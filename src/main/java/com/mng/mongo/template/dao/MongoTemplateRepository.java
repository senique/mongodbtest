package com.mng.mongo.template.dao;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

//cant use DetachedCriteria
//public abstract class MongoTemplateRepository extends AbstractBaseRepository
public abstract class MongoTemplateRepository extends AbstractBaseRepository {
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
    public <T> void save(T entity)
    {
        getMongoTemplate().save(entity);;
    }
    
    @Override
    public <T> T findOne(Query query) throws Exception
    {
        return (T) getMongoTemplate().findOne(query, this.getEntityClass());
    }
    
    @Override
    public <T> T findListByCondition(T condition) throws Exception
    {
        return null;
    }
    
    @Override
    public <T> void update(T entity) throws Exception
    {
    }
    
    @Override
    public <T> void delete(T entity) throws Exception
    {
    }

    abstract public <T> Class<T> getEntityClass();

}
