package com.spinach.interceptor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ch.fm.common.base.dao.BaseDao;
import com.ch.fm.common.base.dao.FmLogOperateDao;
import com.ch.fm.common.base.entity.FmLogOperate;
import com.ch.fm.common.base.entity.UserEntity;
import com.ch.fm.common.base.service.BaseService;
import com.ch.fm.common.util.DateUtil;
import com.ch.fm.common.util.FileUtils;
import com.ch.fm.common.util.JsonUtil;
import com.ch.fm.common.util.ResourceUtils;
import com.ch.fm.common.util.SpringContextUtils;
import com.ch.framework.core.template.entity.BasicDb;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SecutitySqlLogInterceptor2 implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(SecutitySqlLogInterceptor2.class);
	private static FmLogOperateDao fmLogOperateDao ;
	
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
		//是否是定时器，定时器不拦截。
		boolean isTask = false;
		try {
			SecurityUtils.getSubject().getPrincipal();
			SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		} catch (Exception e1) {
			isTask = true;
			//e1.printStackTrace();
		}finally{
			if(isTask){
				return invocation.proceed();
			}
		}
		
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
			writeSqlToDB(sql,metaStatementHandler);
		} catch (Exception e) {
			logger.error("sql语句写入日志文件失败！sql记录："+sql,e);
		}
		return invocation.proceed();
	}
	
	/**
	 * <p>
	 *  sql语句写入数据库
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-4下午3:04:04
	 * @param sql
	 * @param metaStatementHandler 
	 */
	private void writeSqlToDB(String sql, MetaObject metaStatementHandler) {
		if (!StringUtils.isEmpty(sql)) {
			logger.info("sql = " + sql);
			FmLogOperate log = new FmLogOperate();
			log.setCreateTime(new Date());
			log.setDaoMethod(getDaoMethod(metaStatementHandler));
			log.setSqlContent(sql);
			log.setUserAccount((String)SecurityUtils.getSubject().getPrincipal());
			if(null == fmLogOperateDao){
				fmLogOperateDao = (FmLogOperateDao) SpringContextUtils.getBean("fmLogOperateDao");
			}
			fmLogOperateDao.insert(log);
		}
	}
	
	/**
	 * <p>
	 *  获得DAO层方法
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-4下午3:31:31
	 * @param metaStatementHandler
	 * @return
	 */
	private String getDaoMethod(MetaObject metaStatementHandler) {
		String id = "";
		try {
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			id = mappedStatement.getId();
			if(id.indexOf("dao")>0){
				id= id.substring(id.indexOf("dao")+4);
			}
		} catch (Exception e) {
			logger.error("DAO层方法获得失败~",e);
		}
		return id;
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
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		if(isFilterSql(mappedStatement)){
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
		
		if(parameterObject instanceof FmLogOperate){
			return null; 
		}else if(null == parameterObject){
			
		}else if(parameterObject instanceof BasicDb || parameterObject.getClass().toString().contains("entity")) {
			JSONObject json = JSONObject.fromObject(parameterObject);
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					//SQL语句中"?"的替换
					originalSql = originalSql.replaceFirst("\\?", "'" + json.get(params.get(i)) + "'");
				}
			}
		}else if(parameterObject instanceof Map){
			JSONObject json = JSONObject.fromObject(parameterObject);
			Object obj = json.get("array");
			if(obj != null && obj instanceof List){
				List paramsList = (List)obj;
				for(int i=0;i<paramsList.size();i++){
					originalSql = originalSql.replaceFirst("\\?", "'" + paramsList.get(i) + "'");
				}
			}else{
				for(Object param: params){
					originalSql = originalSql.replaceFirst("\\?", "'" + json.get(param) + "'");
				}
			}
		}else if(parameterObject instanceof String){
				originalSql = originalSql.replaceFirst("\\?", "'" + parameterObject + "'");
		}else{
			logger.error("暂时不支持的类型，请增加~"+parameterObject.getClass() + " id:"+mappedStatement.getId());
		}
		logger.debug("Auto generated sql:" + originalSql);
		//去除空格SQL语句
		originalSql = originalSql.replaceAll("\\t", " ").replaceAll("\\n", " ").replaceAll("\\s{2,}", " ");
		return originalSql;
	}
	
	/**
	 * <p>
	 *  方法过虑：framework.system包下的SQL不写入数据库
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-4下午1:48:28
	 * @param mappedStatement
	 * @return
	 */
	private boolean isFilterSql(MappedStatement mappedStatement) {
		String id = mappedStatement.getId();
		if(id.contains("framework.system")){
			return true;
		}
		return false;
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
		String userId = (String) SecurityUtils.getSubject().getPrincipal();
		String user = (String) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		Map<String, Object> map = new HashMap<String, Object>();
		UserEntity userInfo;
		if (StringUtils.isEmpty(user)) {
			userInfo = new UserEntity();
			userInfo.setAccount(userId);
			//用户信息放在缓存，下次取数据，直接从内存中获得。
		} else {
			userInfo = (UserEntity) JsonUtil.getEntity(user, UserEntity.class);
		}
		map.put("userAccount", userInfo.getAccount());
		map.put("userName", userInfo.getUserName());
		return map;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

}
