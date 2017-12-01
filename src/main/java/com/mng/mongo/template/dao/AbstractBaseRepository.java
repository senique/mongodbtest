package com.mng.mongo.template.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class AbstractBaseRepository {
    abstract <T> void save(T entity);
    
    abstract <T> T findById(String id) throws Exception;
   
    abstract <T> T findOne(Query query) throws Exception;
    
    abstract <T> T findListByCondition(T condition) throws Exception;
    
    abstract <T> void update(Query query, Update update) throws Exception;
    
    abstract <T> void delete(T entity) throws Exception;
    
}
