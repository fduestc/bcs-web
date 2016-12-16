package com.cfets.fx.dao;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

import com.cfets.bcs.util.EntityClassUtil;

/***
 * dao的虚基类，实现了基本的CRUD和分页查询操作
 * 
 * @author XWQ
 * 
 * @param <T>
 */
public abstract class BaseDaoImpl<T> implements IBaseDao<T> {
  protected EntityManager baseEntityManager;

  @SuppressWarnings("unchecked")
  private Class<T> entityClass = (Class<T>) EntityClassUtil.getEntityClass(this.getClass());

  private String entityName = getEntityClassName();
  // private String entityObjectName = null;

  public BaseDaoImpl() {
  }

  /***
   * store：保存实体
   */
  @Override
  public void persist(T entity) {
    baseEntityManager.persist(entity);
  }

  /***
   * find：查询实体
   */
  @Override
  public T find(Object entityId) {
    return baseEntityManager.find(entityClass, entityId);
  }

  /***
   * udpate：更新实体到数据库
   */
  @Override
  public void merge(T entity) {
    baseEntityManager.merge(entity);
  }

  /***
   * delete：删除实体，支持一次性删除多个实体
   */
  @Override
  public int remove(Object... entityIds) {
    for (Object entityId : entityIds) {
      baseEntityManager.remove(this.find(entityId));
    }

    return entityIds.length;
  }

  /***
   * 批量执行：批量更新和批量删除
   */
  @Override
  public int execute(String jpql) {
    return baseEntityManager.createQuery(jpql).executeUpdate();
  }

  @Override
  public int execute(String jpql, Object[] params) {
    Query query = baseEntityManager.createQuery(jpql);

    setParameters(query, params);

    return query.executeUpdate();
  }

  @Override
  public int execute(String jpql, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createQuery(jpql);

    setParameters(query, paramMap);

    return query.executeUpdate();
  }

  @Override
  public int executeNative(String sql) {
    return baseEntityManager.createNativeQuery(sql).executeUpdate();
  }

  @Override
  public int executeNative(String sql, Object[] params) {
    Query query = baseEntityManager.createNativeQuery(sql);

    setParameters(query, params);

    return query.executeUpdate();
  }

  @Override
  public int executeNative(String sql, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createNativeQuery(sql);

    setParameters(query, paramMap);

    return query.executeUpdate();
  }

  /***
   * 获取实体的数量，也就是数据库表的行数
   */
  @Override
  public long getTotalCount() {
    Query query = baseEntityManager.createQuery("select count(1) from " + entityName);
    Long result = (Long) query.getSingleResult();
    return result.intValue();
  }

  @Override
  public long getCount(String whereJpql, Object[] params) {
    String jpql = "select count(1) from " + entityName + " where " + whereJpql;

    Query query = baseEntityManager.createQuery(jpql);
    setParameters(query, params);
    Long count = (Long) query.getSingleResult();

    return count.longValue();
  }

  @Override
  public long getCount(String whereJpql, Map<String, Object> paramMap) {
    String jpql = "select count(1) from " + entityName + " where " + whereJpql;

    Query query = baseEntityManager.createQuery(jpql);
    setParameters(query, paramMap);
    Long count = (Long) query.getSingleResult();

    return count.longValue();
  }

  @Override
  public long getNativeCount(String sql) {
    Query query = baseEntityManager.createNativeQuery(sql);

    Integer count = (Integer) query.getSingleResult();

    return count.intValue();
  }

  @Override
  public long getNativeCount(String sql, Object[] params) {
    Query query = baseEntityManager.createNativeQuery(sql);

    setParameters(query, params);

    Integer count = (Integer) query.getSingleResult();

    return count.intValue();
  }

  @Override
  public long getNativeCount(String sql, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createNativeQuery(sql);

    setParameters(query, paramMap);

    Integer count = (Integer) query.getSingleResult();

    return count.intValue();
  }

  /***
   * 获取所有的实体
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> getAll() {
    Query query = baseEntityManager.createQuery("from " + entityName);

    return query.getResultList();
  }

  /***
   * 获取所有的实体,并排序
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> getAll(String orderBy) {
    String jpqlString = "from " + entityName + " " + orderBy;

    Query query = baseEntityManager.createQuery(jpqlString);

    return query.getResultList();
  }

  /***
   * 注意，目前这个函数只支持使用逻辑主键的数据库表格，对使用复合主键的表，请自行构造这个函数
   */
  @Override
  public Object queryForProperty(String property, Object entityId) {
    String jpql = "select o." + property + " from " + entityName + " o where o." + getEntityId() + "=?";
    System.out.println(jpql);
    Query query = baseEntityManager.createQuery(jpql);
    query.setParameter(1, entityId);
    Object result = null;
    try {
      result = query.getSingleResult();
    } catch (RuntimeException e) {
    }
    return result;
  }

  /***
   * 分页获取
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * 
   */
  @Override
  public List<T> query(int firstResult, int maxResult) {
    return query(firstResult, maxResult, null, null, null, 0);
  }

  /***
   * 分页获取并排序
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @orderBy 指定查询结果的排序依据，如 ：<"firstName","asc">,<"firstName","desc">
   * 
   */
  @Override
  public List<T> query(int firstResult, int maxResult, String orderBy) {
    return query(firstResult, maxResult, null, null, orderBy, 0);
  }

  /***
   * 使用位置参数，根据条件分页获取
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @whereJpql 指定where语句，如 ：user.name.firstName=?1 and user.name.lastName=?2
   * @params 指定where中的参数 ，如：params=["Wanqiang","Xiong"]
   * 
   */
  @Override
  public List<T> query(int firstResult, int maxResult, String whereJpql, Object[] params) {
    return query(firstResult, maxResult, whereJpql, params, null);
  }

  /***
   * 根据条件【使用命名参数】进行分页获取，并排序
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @whereJpql 指定where语句，如 ：user.name.firstName=:firstName and
   *            user.name.lastName=:lastName
   * @paramMap 指定where中的参数
   *           ，如：paramMap=[("firstName":"Wanqiang"),"lastName":"Xiong"]
   * 
   */
  @Override
  public List<T> query(int firstResult, int maxResult, String whereJpql, Map<String, Object> paramMap) {
    return query(firstResult, maxResult, whereJpql, paramMap, null);
  }

  /***
   * 使用位置参数，根据条件获取，并排序
   * 
   * @whereJpql 指定where语句，如 ：user.name.firstName=?1 and user.name.lastName=?2
   * @params 指定where中的参数 ，如：params=["Wanqiang","Xiong"]
   * @orderBy 指定查询结果的排序依据，如 ：<"firstName","asc">,<"firstName","desc">
   * 
   */
  @Override
  public List<T> query(String whereJpql, Object[] params, String orderBy) {
    return query(-1, -1, whereJpql, params, orderBy, 0);
  }

  /***
   * 根据条件【使用命名参数】进行分页获取，并排序
   * 
   * @whereJpql 指定where语句，如 ：user.name.firstName=:firstName and
   *            user.name.lastName=:lastName
   * @paramMap 指定where中的参数
   *           ，如：paramMap=[("firstName":"Wanqiang"),"lastName":"Xiong"]
   * @orderBy 指定查询结果的排序依据，如 ：orderBy=[("firstName","asc"),("firstName","desc")]
   * 
   */
  @Override
  public List<T> query(String whereJpql, Map<String, Object> paramMap, String orderBy) {
    return query(-1, -1, whereJpql, paramMap, orderBy);
  }

  /***
   * 使用位置参数，根据条件获取
   * 
   * @whereJpql 指定where语句，如 ：user.name.firstName=?1 and user.name.lastName=?2
   * @params 指定where中的参数 ，如：params=["Wanqiang","Xiong"]
   * 
   */
  @Override
  public List<T> query(String whereJpql, Object[] params) {
    return query(-1, -1, whereJpql, params, null, 0);
  }

  /***
   * 根据条件【使用位置参数（请从1开始指定）】进行分页获取，并排序
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @whereJpql 指定where语句，如 ：user.name.firstName=?1 and user.name.lastName=?2
   * @params 指定where中的参数 ，如：params=["Wanqiang","Xiong"]
   * @orderBy 指定查询结果的排序依据，如 ：<"firstName","asc">,<"firstName","desc">
   * 
   */
  @Override
  public List<T> query(int firstResult, int maxResult, String whereJpql, Object[] params, String orderBy) {
    // 这里进行了中转，一面与使用命名参数的query产生冲突
    return query(firstResult, maxResult, whereJpql, params, orderBy, 0);
  }

  /***
   * 使用命名参数，根据条件获取
   * 
   * @whereJpql 指定where语句，如 ：user.name.firstName=:firstName and
   *            user.name.lastName=:lastName
   * @paramMap 指定where中的参数
   *           ，如：paramMap=[("firstName":"Wanqiang"),"lastName":"Xiong"]
   * 
   */
  @Override
  public List<T> query(String whereJpql, Map<String, Object> paramMap) {
    return query(-1, -1, whereJpql, paramMap, null);
  }

  /***
   * 根据条件【使用命名参数】进行分页获取，并排序
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @whereJpql 指定where语句，如 ：user.name.firstName=:firstName and
   *            user.name.lastName=:lastName
   * @paramMap 指定where中的参数
   *           ，如：paramMap=[("firstName":"Wanqiang"),"lastName":"Xiong"]
   * @orderBy 指定查询结果的排序依据，如 ：orderBy=[("firstName","asc"),("firstName","desc")]
   * 
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> query(int firstResult, int maxResult, String whereJpql, Map<String, Object> paramMap, String orderBy) {
    StringBuffer jpqlSb = new StringBuffer();
    jpqlSb.append("from ");
    jpqlSb.append(entityName);

    if (whereJpql != null) {
      jpqlSb.append(" where " + whereJpql);
    }

    if (orderBy != null) {
      jpqlSb.append(" order by " + orderBy);
    }

    Query query = baseEntityManager.createQuery(jpqlSb.toString(), entityClass);
    // 设置参数
    setParameters(query, paramMap);
    if (firstResult > -1 && maxResult > -1)
      query.setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  /***
   * 根据条件【使用位置参数（请从1开始指定）】进行分页获取，并排序
   * 
   * @firstResult 指定起始索引，从0开始
   * @maxResult 指定查询的结果集的大小
   * @whereJpql 指定where语句，如 ：user.name.firstName=?1 and user.name.lastName=?2
   * @params 指定where中的参数 ，如：params=["Wanqiang","Xiong"]
   * @orderBy 指定查询结果的排序依据，如 ：<"firstName","asc">,<"firstName","desc">
   * 
   */
  @SuppressWarnings("unchecked")
  private List<T> query(int firstResult, int maxResult, String whereJpql, Object[] params, String orderBy,
      int location) {
    StringBuffer jpqlSb = new StringBuffer();
    jpqlSb.append("from ");
    jpqlSb.append(entityName);

    if (whereJpql != null) {
      jpqlSb.append(" where " + whereJpql);
    }

    if (orderBy != null) {
      jpqlSb.append(" order by " + orderBy);
    }

    Query query = baseEntityManager.createQuery(jpqlSb.toString(), entityClass);
    // 设置参数
    setParameters(query, params);
    if (firstResult > -1 && maxResult > -1)
      query.setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  // jpql查询，自己制定select语句
  @Override
  public List<T> querySelect(String selectJpql) {
    Object[] params = null;
    return this.querySelect(selectJpql, -1, -1, params);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> querySelect(String selectJpql, int firstResult, int maxResult, Object[] params) {
    Query query = baseEntityManager.createQuery(selectJpql);

    // 设置参数
    setParameters(query, params);
    if (firstResult > -1 && maxResult > -1)
      query.setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  @Override
  public List<T> querySelect(String selectJpql, Object[] params) {
    return this.querySelect(selectJpql, -1, -1, params);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> querySelect(String selectJpql, int firstResult, int maxResult, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createQuery(selectJpql);

    // 设置参数
    setParameters(query, paramMap);
    if (firstResult > -1 && maxResult > -1)
      query.setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  @Override
  public List<T> querySelect(String selectJpql, Map<String, Object> paramMap) {
    return this.querySelect(selectJpql, -1, -1, paramMap);
  }

  /***
   * 根据原生的sql查询，并分页， 返回的list的每一行是Object[]类型数据
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql) {
    Query query = baseEntityManager.createNativeQuery(sql).setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String nativeSqlString, Object[] params) {
    Query query;

    if (firstResult < 0 || maxResult < 0) {
      query = baseEntityManager.createNativeQuery(nativeSqlString);
    } else {
      query = baseEntityManager.createNativeQuery(nativeSqlString).setFirstResult(firstResult).setMaxResults(maxResult);
    }

    setParameters(query, params);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createNativeQuery(sql);
    setParameters(query, paramMap);

    query.setFirstResult(firstResult).setMaxResults(maxResult);

    return query.getResultList();
  }

  // public List<T> nativeQuery(int firstResult, int maxResult, String
  // sql, Map<String, Object> paramMap)

  /***
   * 根据原生的sql查询， 返回的list的每一行是Object[]类型数据
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql) {
    Query query = baseEntityManager.createNativeQuery(sql);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, Object[] params) {
    Query query = baseEntityManager.createNativeQuery(sql);
    setParameters(query, params);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, Map<String, Object> paramMap) {
    Query query = baseEntityManager.createNativeQuery(sql);
    setParameters(query, paramMap);

    return query.getResultList();
  }

  /***
   * 根据原生的sql分页查询，并映射到对应的resultClass上 返回的list的每一行是resultClass类型数据
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql, Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass).setFirstResult(firstResult)
        .setMaxResults(maxResult);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql, Object[] params, Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass).setFirstResult(firstResult)
        .setMaxResults(maxResult);
    setParameters(query, params);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql, Map<String, Object> paramMap,
      Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass).setFirstResult(firstResult)
        .setMaxResults(maxResult);
    setParameters(query, paramMap);

    return query.getResultList();
  }

  /***
   * 根据原生的sql查询，并映射到对应的resultClass上 返回的list的每一行是resultClass类型数据
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, Object[] params, Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass);
    setParameters(query, params);

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, Map<String, Object> paramMap, Class<T> resultClass) {
    Query query = baseEntityManager.createNativeQuery(sql, resultClass);
    setParameters(query, paramMap);

    return query.getResultList();
  }

  /***
   * 根据原生的sql分页查询，并根据resultSetMapping对结果集进行自定义映射
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(int firstResult, int maxResult, String sql, String resultSetMapping) {
    Query query = baseEntityManager.createNativeQuery(sql, resultSetMapping).setFirstResult(firstResult)
        .setMaxResults(maxResult);

    return query.getResultList();
  }

  /***
   * 根据原生的sql查询，并根据resultSetMapping对结果集进行自定义映射
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> nativeQuery(String sql, String resultSetMapping) {
    Query query = baseEntityManager.createNativeQuery(sql, resultSetMapping);

    return query.getResultList();
  }

  // 私有方法
  private void setParameters(Query query, Object[] params) {
    if (null != query && null != params && params.length > 0) {
      for (int i = 1; i <= params.length; i++) {
        query.setParameter(i, params[i - 1]);
      }
    }
  }

  private void setParameters(Query query, Map<String, Object> paramMap) {
    if (null != paramMap && !paramMap.isEmpty()) {
      for (String key : paramMap.keySet()) {
        query.setParameter(key, paramMap.get(key));
      }
    }
  }

  private String getEntityId() {
    Field[] fields = entityClass.getDeclaredFields();
    Id id = null;
    String entityId = null;
    // 先看看字段有没有注解id
    for (Field field : fields) {
      id = field.getAnnotation(Id.class);
      if (null != id) {
        entityId = field.getName();
        break;
      }
    }
    // 如果字段上没有注解，则在getter方法上面找id
    if (null == id) {
      try {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
        Method readMethod = null;
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
          readMethod = propertyDescriptor.getReadMethod();
          id = readMethod.getAnnotation(Id.class);
          if (null != id) {
            entityId = propertyDescriptor.getName();
            break;
          }
        }
      } catch (IntrospectionException e) {
        throw new RuntimeException(e);
      }
    }

    if (entityId == null)
      throw new RuntimeException("逻辑主键没有设置，目前只支持单一主键");

    return entityId;
  }

  private String getEntityClassName() {
    String entityName = entityClass.getSimpleName();
    Entity entity = entityClass.getAnnotation(Entity.class);
    String name = null;
    if (null != entity)
      name = entity.name();
    return null == name || "".equals(name.trim()) ? entityName : name.trim();
  }
}