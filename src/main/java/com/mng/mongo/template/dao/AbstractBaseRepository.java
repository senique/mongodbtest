package com.mng.mongo.template.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class AbstractBaseRepository {
    abstract <T> void save(T entity);
    
    abstract <T, E> T findById(E id) throws Exception;
   
    abstract <T> T findOne(Query query) throws Exception;
    
    abstract long countByCondition(Query query) throws Exception;
    
    abstract <T> T findListByCondition(Query Query) throws Exception;
    
    abstract <T> void update(Query query, Update update) throws Exception;
    
    abstract <T> void delete(T entity) throws Exception;

}
