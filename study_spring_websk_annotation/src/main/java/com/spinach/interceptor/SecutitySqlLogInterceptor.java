package com.spinach.interceptor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.spinach.domain.BaseEntity;
import com.spinach.utils.DateUtil;
import com.spinach.utils.FileUtils;
import com.spinach.utils.JsonUtil;
import com.spinach.utils.ResourceUtils;

import net.sf.json.JSONObject;

/**
 * <p>
 * mybatis日志记录拦截器。 自定义拦截：增、删、改相关的SQL
 * 参考：深入浅出Mybatis-sql自动生成  https://www.cnblogs.com/lijun1990/p/6811176.html 
 *  mybatis定义拦截器 https://www.cnblogs.com/woms/p/6038251.html
 * </p>
 * @author spinach
 * 2017年6月9日下午4:25:58
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SecutitySqlLogInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(SecutitySqlLogInterceptor.class);
	private static String filePath;
	private static String fileName;
	static {
		try {
			filePath = ResourceUtils.getPropertyInSystem("log.filePath");
			fileName = ResourceUtils.getPropertyInSystem("log.fileName");
		} catch (Exception e) {
			logger.error(DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + "获得log.path路径失败，请在system.properties配置。：" + e.getMessage());
			e.printStackTrace();
		}
	}

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		// 分离代理对象链  
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}
		// 分离最后一个代理对象的目标类  
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = SystemMetaObject.forObject(object);
		}
		 
		String sql = null;
		try {
			//获得SQL语句
			sql = getSql(metaStatementHandler);
		} catch (Exception e) {
			logger.error("获得sql语句失败！",e);
		}
		try {
			writeSqlToLog(sql);
		} catch (Exception e) {
			logger.error("sql语句写入日志文件失败！sql记录："+sql,e);
		}
		return invocation.proceed();
	}
	
	/**
	 * <p>
	 *  sql语句写入日志文件
	 * </p>
	 * @author wanghuihui
	 * @date 2017-6-9下午3:18:19
	 * @param sql
	 * @throws Exception 
	 */
	private void writeSqlToLog(String sql) throws Exception {
		//写入日志信息。
		if (!StringUtils.isEmpty(sql)) {
			logger.info("sql = " + sql);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sql", sql);
			map.put("userInfo", getUserInfo());
			map.put("date", DateUtil.datesToString(new Date()));

			String _fileName = fileName + "." + DateUtil.datesToString(new Date(), "yyyy-MM-dd") + ".log";
			FileUtils.writeAppendFile(filePath, _fileName, JsonUtil.getJSONString(map));
		}
	}

	/**
	 * <p>
	 *  获得SQL语句
	 * </p>
	 * @author wanghuihui
	 * @date 2017-6-9下午12:16:51
	 * @param metaStatementHandler
	 * @return 
	 */
	private String getSql(MetaObject metaStatementHandler) {
		// TODO Auto-generated method stub
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		if(isSelectSql(mappedStatement)){
			return null;
		}
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		originalSql = originalSql.trim();
		
		List<ParameterMapping> paramList = boundSql.getParameterMappings();	
		Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
		
		List<Object> params;
		params = new ArrayList<Object>(paramList.size());
		//获得对应的参数集合
		for (ParameterMapping param : paramList) {
			params.add(param.getProperty());
		}
		
		JSONObject json = JSONObject.fromObject(parameterObject);
		
		if (parameterObject instanceof BaseEntity) {
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					//SQL语句中"?"的替换
					originalSql = originalSql.replaceFirst("\\?", "'" + json.get(params.get(i)) + "'");
				}
			}
		}else if(parameterObject instanceof Map){
			Object obj = json.get("array");
			if(obj != null && obj instanceof List){
				List paramsList = (List)obj;
				for(int i=0;i<paramsList.size();i++){
					originalSql = originalSql.replaceFirst("\\?", "'" + paramsList.get(i) + "'");
				}
			}
		}
		logger.info("Auto generated sql:" + originalSql);
		//去除空格SQL语句
		originalSql = originalSql.replaceAll("\\t", "").replaceAll("\\n", "").replaceAll("\\s{2,}", " ");
		return originalSql;
	}
	
	/**
	 * <p>
	 *  方法类型过滤。排除 select 和 unknown未知类型。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-6-9下午3:11:55
	 * @param mappedStatement
	 * @return
	 */
	private boolean isSelectSql(MappedStatement mappedStatement) {
		String type = mappedStatement.getSqlCommandType().toString();
		return type.equals("SELECT") || type.equals("UNKNOWN");
	}
	

	/**
	 * <p>
	 * 获得用户信息: 注意:用户获得使用的:shiro构架。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-6-7下午1:34:03
	 * @return
	 */
	private Object getUserInfo() {
		Map<String,Object> map = new HashMap<>();
		map.put("userAccount", "0014706");
		map.put("userName", "spinach");
		return map;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

}
