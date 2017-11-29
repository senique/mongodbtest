package mongo.template.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import utils.page.PageResult;
import utils.page.Pager;

public abstract class MongoTemplateRepository extends AbstractBaseRepository
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private MongoTemplate mongoTemplate;
    
    private MongoDbFactory mongoDbFactory;
    
    public void setDataSource(DataSource dataSource)
    {
    
    }
    
    public void setSessionFactory(MongoDbFactory mongoDbFactory)
    {
        this.mongoDbFactory = mongoDbFactory;
    }
    
    protected MongoTemplate getMongoTemplate()
    {
        if(this.mongoTemplate == null) this.mongoTemplate = new MongoTemplate(getMongoDbFactory());
        return this.mongoTemplate;
    }
    
    protected MongoDbFactory getMongoDbFactory()
    {
        return this.mongoDbFactory;
    }
    
    @Override
    protected int countByCriteria(DetachedCriteria criteria)
    {
        criteria.setProjection(Projections.rowCount());
        @SuppressWarnings("rawtypes")
        List list = getMongoTemplate().findByCriteria(criteria);
        criteria.setProjection(null);
        if(list != null && list.size() > 0)
            return Integer.valueOf(list.get(0).toString());
        else
            return 0;
    }
    
    @Override
    protected <T> boolean exists(Class<T> persistentClass, Serializable id)
    {
        T entity = (T) mongoTemplate.get(persistentClass, id);
        return entity != null;
    }
    
    /** 主键查询 */
    @Override
    public <T> T queryByPrimaryKey(Class<T> persistentClass, Serializable id) throws Exception
    {
        if(id == null)
        {
            throw new Exception("primary key must not null");
        }
        T entity = (T) getMongoTemplate().get(persistentClass, id);
        if(entity != null)
            this.getMongoTemplate().evict(entity);
        return entity;
    }
    
    /** 非主键条件查询 */
    @Override
    public <T> T uniqueResultByExample(T condition) throws Exception
    {
        List<T> list = queryListByExample(condition);
        T t = (list == null || list.isEmpty()) ? null : list.get(0);
        return t;
    }
    
    /** 指定主键字段的单条记录通用查询 */
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T uniqueResultByExample(T condition, Serializable primaryKeyValue)
            throws Exception
    {
        if(condition == null && primaryKeyValue == null)
        {
            throw new Exception("查询参数不能为空");
        }
        if(primaryKeyValue != null)
        {
            return (T) queryByPrimaryKey(condition.getClass(), primaryKeyValue);
        }
        else
        {
            return uniqueResultByExample(condition);
        }
    }
    
    /** 非主键条件列表查询 */
    @Override
    public <T> List<T> queryListByExample(T condition) throws Exception
    {
        if(condition == null)
        {
            throw new Exception("查询参数不能为空");
        }
        // String className = condition.getClass().getName();
        // LOG.debug(MessageFormat.format(" queryList invoke , clazz is 【{0}】",
        // className));
        
        /**
         * Hiberante在使用QBE查询时，如果是对主键查询，是不起作用的，即使主键设置了值。
         * 在这种情况下，需要对主键查询时，可以使用criteria.add(Restrictions.idEq(id))来实现。
         */
        List<T> list = getMongoTemplate().findByExample(condition);
        // LOG.debug(MessageFormat.format(" queryList end , clazz is 【{0}】 size
        // 【{1}】 ", className,
        // list.size()));
        
        return list;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria criteria)
    {
        List<T> result = (List<T>) getMongoTemplate().findByCriteria(criteria);
        return result;
    }
    
    @Override
    public <T> T uniqueResultByCriteria(DetachedCriteria criteria)
    {
        List<T> list = findByCriteria(criteria);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected <T> T queryMaxPropertyRecord(Class<T> t, String propertyName)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(t);
        dc.addOrder(Order.desc(propertyName));
        Criteria c = dc.getExecutableCriteria(mongoDbFactory.getCurrentSession());
        c.setMaxResults(1);
        return (T) c.uniqueResult();
    }
    
    @Override
    public <T> void saveOrUpdate(T domainEntity)
    {
        getMongoTemplate().saveOrUpdate(domainEntity);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T save(T domainEntity)
    {
        Serializable id = getMongoTemplate().save(domainEntity);
        return (T) getMongoTemplate().get(domainEntity.getClass(), id);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> void update(T domainEntity)
    {
        this.getMongoTemplate().update(domainEntity);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    protected <T> void delete(T domainEntity)
    {
        this.getMongoTemplate().delete(domainEntity);
    }
    
    protected <T> PageResult<T> criteriaPageSearch(Class<T> clazz, List<Criterion> criterions, String orderStr,
            Pager pager)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        for(Criterion criterion : criterions)
        {
            dc.add(criterion);
        }
        return criteriaPageSearch(dc, orderStr, pager);
    }
    
    protected <T> PageResult<T> criteriaPageSearch(DetachedCriteria dc, String orderStr, Pager pager)
    {
        if(pager == null)
            pager = new Pager(0, 2000);
            
        int count = countByCriteria(dc);
        pager.setTotalRow(count);
        int pageSize = pager.getRowsPerPage();
        int totalPageNum = pager.getTotalPage();
        int nowPage = pager.getCurrentPage();
        
        if(StringUtils.isNotEmpty(orderStr))
        {
            dc.addOrder(Order.desc(orderStr));
        }
        
        List<T> list = this.getMongoTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException
            {
                Criteria c = dc.getExecutableCriteria(session);
                c.setFirstResult((nowPage - 1) * pageSize);
                c.setMaxResults(pageSize);
                return c.list();
            }
        });
        
        if(!CollectionUtils.isEmpty(list)) list.stream().filter(o -> o == null).forEach(o -> this.getMongoTemplate().evict(o));
        
        return new PageResult<T>(nowPage, pageSize, count, totalPageNum, list);
    }
    
    /** 可以指定排序方式,利用DetachedCriteria.addOrder实现 */
    protected <T> PageResult<T> criteriaPageSearch(DetachedCriteria dc, Pager pager)
    {
        int count = countByCriteria(dc);
        pager.setTotalRow(count);
        int pageSize = pager.getRowsPerPage();
        
        int totalPageNum = pager.getTotalPage();
        
        int nowPage = pager.getCurrentPage();
        
        List<T> list = this.getMongoTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
            
            @SuppressWarnings("unchecked")
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException
            {
                Criteria c = dc.getExecutableCriteria(session);
                c.setFirstResult((nowPage - 1) * pageSize);
                
                c.setMaxResults(pageSize);
                
                return c.list();
            }
        });
        return new PageResult<T>(nowPage, pageSize, count, totalPageNum, list);
    }
    
    /** 批量删除 */
    public <T> void delete(List<T> entityList)
    {
        if(entityList != null && entityList.size() > 0)
        {
            this.getMongoTemplate().deleteAll(entityList);
        }
    }
    
    /** 批量保存 */
    public <T> void save(List<T> entityList)
    {
        for(T entity : entityList)
        {
            this.saveOrUpdate(entity);
        }
    }
    
    //查询列表
    public <T> PageResult<T> searchByQuery(Query dataQuery,Query countQuery,Pager pager,Object... values) {
        int nowPage = pager.getCurrentPage();
        int pageSize = pager.getRowsPerPage();
        int totalCount = countByQuery(countQuery,values);
        pager.setTotalRow(totalCount);
        int totalPageNum = pager.getTotalPage();
        
        List<T> list = null;
        if(totalCount > 0){
            int rowStartIdx = (nowPage - 1) * pageSize;
            int rowCount = pageSize;
            list = listByQuery(dataQuery, rowStartIdx, rowCount, values);
        }
        
        return new PageResult<T>(nowPage,pageSize,totalCount,totalPageNum, list);
    }
    
    //查询数据
    @SuppressWarnings("unchecked")
    public <T> List<T> listByQuery(Query query,int rowStartIdx,int rowCount,Object... values) {
        setParameters(query, values);
        if (rowStartIdx > 0) query.setFirstResult(rowStartIdx);
        if (rowCount > 0) query.setMaxResults(rowCount);
        List<T> list = query.list();
        return list;
    }
    
    //获取总数
    public int countByQuery(Query query,Object... values ) {
        setParameters(query, values);
        int result =  ((BigInteger) query.uniqueResult()).intValue();
        return result;
    }
    
    //设置参数
    @SuppressWarnings("rawtypes")
    protected void setParameters(Query query, Object... values) {
        if (values != null) {
            int cur = 0;
            for (int i = 0; i < values.length; i++) {
                if (values[i]!=null && values[i] instanceof List){
                    List list = (List)values[i];
                    for (int j=0;list!=null && j<list.size();j++){
                        query.setParameter(cur++, list.get(j));
                    }
                }else {
                    query.setParameter(cur++, values[i]);
                }
            }
        }
    }
    
    public void executeUpdateWithSql(Query query, Object... values)
    {
        setParameters(query, values);
        query.executeUpdate();
    }
}