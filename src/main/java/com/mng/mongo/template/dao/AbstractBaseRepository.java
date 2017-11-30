package com.mng.mongo.template.dao;

import org.springframework.data.mongodb.core.query.Query;

public abstract class AbstractBaseRepository
{
    abstract <T> void save(T entity);
   
    abstract <T> T findOne(Query condition) throws Exception;
    
    abstract <T> T findListByCondition(T condition) throws Exception;
    
    abstract <T> void update(T entity) throws Exception;
    
    abstract <T> void delete(T entity) throws Exception;
    
}
