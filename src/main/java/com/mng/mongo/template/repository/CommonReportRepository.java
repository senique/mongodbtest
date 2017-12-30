package com.mng.mongo.template.repository;

import org.springframework.stereotype.Repository;
import com.mng.domain.ReportRecordNew;
import com.mng.mongo.template.dao.MongoTemplateRepository;

@Repository
@SuppressWarnings("unchecked")
public class CommonReportRepository extends MongoTemplateRepository
{
    
    @Override
    protected Class<ReportRecordNew> getEntityClass() {
        return ReportRecordNew.class;
    }
    
}
