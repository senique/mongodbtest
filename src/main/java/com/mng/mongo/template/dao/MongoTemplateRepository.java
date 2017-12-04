package com.mng.mongo.template.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
}
