package com.mng.mongo.template.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.mng.domain.ReportRecordNewId;

//cant use DetachedCriteria
@SuppressWarnings("unchecked")
@Repository
public class MongoTemplateRepository extends AbstractBaseRepository {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private MongoTemplate mongoTemplate;
    
    public Class<ReportRecordNewId> getEntityClass() {
        return ReportRecordNewId.class;
    }
    
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
    public <T> T findListByCondition(T condition) throws Exception {
        return null;
    }
    
    @Override
    public <T> void update(Query query, Update update) throws Exception {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
//        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }
    
    @Override
    public <T> void delete(T entity) throws Exception {
        mongoTemplate.remove(entity);
    }
    
    /*    
    private MongoDbFactory mongoDbFactory;
    public void setSessionFactory(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }
    
    protected MongoDbFactory getMongoDbFactory() {
        return this.mongoDbFactory;
    }
    protected MongoTemplate getMongoTemplate() {
        if(this.mongoTemplate == null) this.mongoTemplate = new MongoTemplate(getMongoDbFactory());
        return this.mongoTemplate;
    }
    */
}
