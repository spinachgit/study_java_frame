package com.spinach.utils;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * <p>
 * 数据库批量操作工具。
 * </p>
 * 
 * @version 1.0
 * @author duanzhenxing
 * @date 2016年9月27日
 */
public class BatchTool {

	private static SqlSessionFactory mySqlSessionFactory;

	static {
		mySqlSessionFactory = (SqlSessionFactory) SpringContextUtils.getBean("mySqlSessionFactory");
	}

	/**
	 * 数据库批量新增操作
	 * 
	 * @param mySqlSessionFactory 会话工厂：需在使用的类里面注入
	 * @param statement 新增语句
	 * @param list 所有新增对象
	 * @throws Exception
	 */
	public static void batchInsert(String statement, List<?> list) {
		SqlSession batchSqlSession = mySqlSessionFactory.openSession();
		try {
			for (Object obj : list) {
				batchSqlSession.insert(statement, obj);
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			batchSqlSession.rollback(true);
			throw new RuntimeException(e);
		} finally {
			batchSqlSession.close();
		}
	}

	/**
	 * 数据库批量修改操作
	 * 
	 * @param mySqlSessionFactory 会话工厂：需在使用的类里面注入
	 * @param statement 修改语句
	 * @param list 所有修改对象
	 * @throws Exception
	 */
	public static void batchUpdate(String statement, List<?> list) {
		SqlSession batchSqlSession = mySqlSessionFactory.openSession();
		try {
			for (Object obj : list) {
				batchSqlSession.update(statement, obj);
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			batchSqlSession.rollback(true);
			throw new RuntimeException(e);
		} finally {
			batchSqlSession.close();
		}
	}
}
