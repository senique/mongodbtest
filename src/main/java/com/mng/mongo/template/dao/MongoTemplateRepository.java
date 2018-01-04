package com.mng.mongo.template.dao;

import java.util.List;

import com.mng.domain.ReportRecord;
import com.mng.utils.sequence.SaveMongoEventListener;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
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
    private AbstractApplicationContext context;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    /**
     * 注册Listener 通过注解@GeneratedValue指定主键ID值 （大数据量情况性能相对会下降）
     * [ApplicationListenerDetector] Inner bean 'com.viomi.common.utils.sequence.SaveMongoEventListener'
     * implements ApplicationListener interface but is not reachable for event multicasting by its containing
     * ApplicationContext because it does not have singleton scope. Only top-level listener beans are allowed to be of non-singleton scope.
     */
    public void registerListeners() {
        SaveMongoEventListener firstListener = beanFactory.createBean(SaveMongoEventListener.class);
        context.addApplicationListener(firstListener);
    }


    @Autowired
    @Qualifier("mongoTemplate")
//    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
//        mongoTemplate = new MongoTemplate(new Mongo("localhost"),"testdb");
//        mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "testdb"));
//        mongoTemplate.setApplicationContext(context);
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
    
    @Override
    public <T> void update(T entity) throws Exception {
        String idLabel = "id";
        String idValue = BeanUtils.getProperty(entity, idLabel);
        if(StringUtils.isNotBlank(idValue)) {
            //id value must be long for Query
            Query query = Query.query(Criteria.where(idLabel).is(Long.valueOf(idValue)));
            Update update = Update.fromDBObject(BeanConvertUtil.bean2DBObject(entity), idLabel);
            mongoTemplate.updateFirst(query, update, this.getEntityClass());
        }
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
     * @param query
     * @param pager
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
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月29日 上午11:39:48
     * @description 聚合函数 限制mongoV3.4
     *              Changed in version 3.6: MongoDB 3.6 removes the use of aggregate command without the cursor option 
     *              unless the command includes the explain option. 
     * @since version 初始于版本 v0.0.1 
     * @param aggregation
     * @param inputType
     * @param outputType
     * @return
     */
    public  <T> T aggregate(Aggregation aggregation, Class inputType, Class outputType)
    {
        return (T) mongoTemplate.aggregate(aggregation, inputType, outputType);
    }

    /**
     *
     * @author：luocj
     * @createtime ：2018年1月2日 下午18:00:55
     * @description mapReduce
     * @param query
     * @param mapFunction
     * @param reduceFunction
     * @param outputEntityClass
     * @return
     */
    public <T> MapReduceResults<T> mapReduce(Query query, String mapFunction, String reduceFunction, Class<T> outputEntityClass) {
        return mongoTemplate.mapReduce(query, getCollectionName(this.getEntityClass().getSimpleName()), mapFunction, reduceFunction, outputEntityClass);
    }

    private String getCollectionName(String simpleName) {
        if (null == simpleName || simpleName.length() == 0){
            return "";
        }
        return simpleName.toLowerCase().substring(0, 1)+simpleName.substring(1);
    }


//    public void testFnc()
//    {
//        mongoTemplate.aggregate(aggregation, outputType)
//        mongoTemplate.group(inputCollectionName, groupBy, entityClass)
//        mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass)
}
