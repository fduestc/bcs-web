package com.cfets.fx.dao;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
	// store
	public void persist(T entity);

	// read
	public T find(Object entityId);

	// update
	public void merge(T entity);

	// delete：根据主键删除
	public int remove(Object... entityIds);

	// 批量执行：批量更新和批量删除
	public int execute(String jpql);

	public int execute(String jpql, Object[] params);

	public int execute(String jpql, Map<String, Object> paramMap);

	public int executeNative(String sql);

	public int executeNative(String sql, Object[] params);

	public int executeNative(String sql, Map<String, Object> paramMap);

	// 获取所有的实体
	public List<T> getAll();

	// 获取所有的实体,并排序
	public List<T> getAll(String orderBy);

	// 获取实体的数量，也就是数据库表的行数
	public long getTotalCount();

	// 根据条件获取实体的数量，也就是数据库表的行数
	public long getCount(String whereJpql, Object[] params);

	public long getCount(String whereJpql, Map<String, Object> paramMap);

	// 根据条件获取实体的数量，也就是数据库表的行数
	public long getNativeCount(String sql);

	public long getNativeCount(String sql, Object[] params);

	public long getNativeCount(String sql, Map<String, Object> paramMap);

	// jsql查询，自己指定select语句
	public List<T> querySelect(String selectJpql);

	public List<T> querySelect(String selectJpql, int firstResult, int maxResult, Object[] params);

	public List<T> querySelect(String selectJpql, Object[] params);

	public List<T> querySelect(String selectJpql, int firstResult, int maxResult, Map<String, Object> paramMap);

	public List<T> querySelect(String selectJpql, Map<String, Object> paramMap);

	// 分页查询，通过自己定义对应的
	public List<T> query(int firstResult, int maxResult, String whereJpql, Object[] params, String orderBy);

	List<T> query(int firstResult, int maxResult, String whereJpql, Map<String, Object> paramMap, String orderBy);

	public List<T> query(int firstResult, int maxResult, String orderBy);

	public List<T> query(int firstResult, int maxResult, String whereJpql, Object[] params);

	public List<T> query(int firstResult, int maxResult, String whereJpql, Map<String, Object> paramMap);

	public List<T> query(int firstResult, int maxResult);

	public List<T> query(String whereJpql, Object[] params, String orderBy);

	public List<T> query(String whereJpql, Map<String, Object> paramMap, String orderBy);

	public List<T> query(String whereJpql, Object[] params);

	public List<T> query(String whereJpql, Map<String, Object> paramMap);

	/***
	 * 注意，目前这个函数只支持使用逻辑主键的数据库表格，对使用复合主键的表，请自行构造这个函数
	 */
	public Object queryForProperty(String property, Object entityId);

	/***
	 * 根据原生的sql查询，并分页， 返回的list的每一行是Object[]类型数据
	 */
	public List<T> nativeQuery(int firstResult, int maxResult, String sql);

	public List<T> nativeQuery(int firstResult, int maxResult, String sql, Object[] params);

	public List<T> nativeQuery(int firstResult, int maxResult, String sql, Map<String, Object> paramMap);

	/***
	 * 根据原生的sql查询， 返回的list的每一行是Object[]类型数据
	 */
	public List<T> nativeQuery(String sql);

	public List<T> nativeQuery(String sql, Object[] params);

	public List<T> nativeQuery(String sql, Map<String, Object> paramMap);

	/***
	 * 根据原生的sql分页查询，并映射到对应的resultClass上 返回的list的每一行是resultClass类型数据
	 */
	public List<T> nativeQuery(int firstResult, int maxResult, String sql, Class<T> resultClass);

	public List<T> nativeQuery(int firstResult, int maxResult, String sql, Object[] params, Class<T> resultClass);

	public List<T> nativeQuery(int firstResult, int maxResult, String sql, Map<String, Object> paramMap,
			Class<T> resultClass);

	/***
	 * 根据原生的sql查询，并映射到对应的resultClass上 返回的list的每一行是resultClass类型数据
	 */
	public List<T> nativeQuery(String sql, Class<T> resultClass);

	public List<T> nativeQuery(String sql, Object[] params, Class<T> resultClass);

	public List<T> nativeQuery(String sql, Map<String, Object> paramMap, Class<T> resultClass);

	/***
	 * 根据原生的sql分页查询，并根据resultSetMapping对结果集进行自定义映射
	 */
	public List<T> nativeQuery(int firstResult, int maxResult, String sql, String resultSetMapping);

	/***
	 * 根据原生的sql查询，并根据resultSetMapping对结果集进行自定义映射
	 */
	public List<T> nativeQuery(String sql, String resultSetMapping);

}
