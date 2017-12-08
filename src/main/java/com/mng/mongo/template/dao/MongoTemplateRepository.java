package com.mng.mongo.template.dao;

import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mng.utils.BeanConvertUtil;
import com.mng.utils.page.PageResult;
import com.mng.utils.page.Pager;

//cant use DetachedCriteria
@SuppressWarnings("unchecked")
public abstract class MongoTemplateRepository extends AbstractBaseRepository {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private MongoTemplate mongoTemplate;
    
    protected abstract <T> Class<T> getEntityClass();
    
    @Autowired
    @Qualifier("mongoTemplate")
//    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    /**
     * 特殊说明：
     * 1. 如果已经存在主键，则save(遍历列表,相当于upsert)会调用update更新记录，而insert(不用遍历，效率高)会忽略操作
     * 2. 如果entity结构改变，则会按照新的结构保存数据（Schema无关性）；
     */
    @Override
    public <T> void save(T entity) {
        mongoTemplate.save(entity);
//        mongoTemplate.insert(entity);
    }
    
    @Override
    public <T, E> T findById(E id) throws Exception {
        return (T) mongoTemplate.findById(id, this.getEntityClass());
    }
    
    @Override
    public <T> T findOne(Query query) throws Exception {
        return (T) mongoTemplate.findOne(query, this.getEntityClass());
    }
    
    @Override
    public long countByCondition(Query query) throws Exception {
        return mongoTemplate.count(query, this.getEntityClass());
    }
    
    @Override
    public <T> T findListByCondition(Query query) throws Exception {
        return (T) mongoTemplate.find(query, this.getEntityClass());
    }
    
    /**
     * TODO can save data
     */
    @Override
    public <T> void update(T entity) throws Exception
    {
        String exclude = "id";
        Query query = Query.query(Criteria.where(exclude).is(BeanUtils.getProperty(entity, exclude)));
        Update update = Update.fromDBObject(BeanConvertUtil.bean2DBObject(entity), exclude);
        
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }
    
    public <T> void update(Query query, Update update) throws Exception
    {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }
    
    @Override
    public <T> void delete(T entity) throws Exception {
//      .remove(query, entityClass);
        mongoTemplate.remove(entity);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月5日 上午9:39:08
     * @description 根据id 和 fieldName累加
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param filedValue
     * @throws Exception
     */
    public void increaseValueToFiled(Object id, String filedName, Long filedValue) throws Exception
    {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), new Update().inc(filedName, filedValue), this.getEntityClass());
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午10:10:42
     * @description 根据ID 添加字段
     *              方法二：直接在实体对象增加字段，调用保存方法
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param filedValue
     * @throws Exception
     */
    public void addFiled(Object id, String filedName, String filedValue) throws Exception
    {
//        super.update(Query.query(Criteria.where("id").is(id)), Update.update(filedName, filedValue));
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), new Update().set(filedName, filedValue), this.getEntityClass());
    }

    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午10:41:04
     * @description 根据ID 删除字段
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @throws Exception
     */
    public void deleteFiled(Object id, String filedName) throws Exception
    {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), new Update().unset(filedName), this.getEntityClass());
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午11:48:16
     * @description 重命名字段名称
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param newFiledName
     * @throws Exception
     */
    public void renameFiled(Object id, String filedName, String newFiledName) throws Exception
    {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), new Update().rename(filedName, newFiledName), this.getEntityClass());
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月5日 下午5:20:51
     * @description 按条件查询多条数据,带分页条件（分页从0开始）
     * @since version 初始于版本 v0.0.1 
     * @param templateId
     * @param fromObjId
     * @param startPeriodDate
     * @param endPeriodDate
     * @param addremark
     * @param pager
     * @return
     * @throws Exception
     */
    public <T> PageResult<T> findListByCondition(Query query, Pager pager) throws Exception {
        if(pager == null){
            pager = new Pager(0, 2000);
        }
            
        long count = countByCondition(query);
        pager.setTotalRow((int) count);
        int pageSize = pager.getRowsPerPage();
        int totalPageNum = pager.getTotalPage();
        int nowPage = pager.getCurrentPage();
        
        query.skip((nowPage - 1) * pageSize); //start index
        query.limit(pageSize); 
        List<T> list = findListByCondition(query);
        return new PageResult<T>(nowPage, pageSize, count, totalPageNum, list);
    }
    
    @SuppressWarnings("rawtypes")
    public  <T> T aggregate(Aggregation aggregation, Class inputType, Class outputType)
    {
        return (T) mongoTemplate.aggregate(aggregation, inputType, outputType);
    }
    
    public void testFnc()
    {
//        mongoTemplate.aggregate(aggregation, outputType)
//        mongoTemplate.group(inputCollectionName, groupBy, entityClass)
//        mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass)
    }
}
