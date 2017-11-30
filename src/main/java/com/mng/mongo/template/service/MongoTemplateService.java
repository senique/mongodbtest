package com.mng.mongo.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.dao.MongoTemplateRepository;

public class MongoTemplateService extends MongoTemplateRepository
{
    @Autowired  
    private MongoTemplateRepository mtRepository;
    
    public void save(ReportRecordNewId entity){
        mtRepository.save(entity);
    }
    
    
    @Override
    public Class<ReportRecordNewId> getEntityClass()
    {
        return ReportRecordNewId.class;
    }
    
    
    
}
