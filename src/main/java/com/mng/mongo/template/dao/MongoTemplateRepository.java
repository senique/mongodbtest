package com.mng.mongo.template.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    
    @Override
    public <T> void save(T entity) {
        mongoTemplate.save(entity);;
    }
    
    @Override
    public <T> T findById(String id) throws Exception {
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
    public <T> void update(Query query, Update update) throws Exception {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
//        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }
    
    @Override
    public <T> void delete(T entity) throws Exception {
//      .remove(query, entityClass);
        mongoTemplate.remove(entity);
    }

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
}
