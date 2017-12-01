package com.mng.mongo.template.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.dao.MongoTemplateRepository;

@SuppressWarnings("unchecked")
@Service
public class CommonReportService extends MongoTemplateRepository
{
    @Autowired  
    private MongoTemplateRepository mtRepository;
    
    public void save(ReportRecordNewId entity) {
        mtRepository.save(entity);
    }
    
    public ReportRecordNewId findById(String id) throws Exception {
        return mtRepository.findById(id);
    }
    
    public ReportRecordNewId findOne(Map<String, String> para) throws Exception {
        Query query = new Query();
        Criteria criteria = new Criteria();
        
        if(null == para || para.size()==0) {
            return null;
        }
//        for(String key: para.keySet()){
//            criteria.where(key).is(para.get(key));
//        }
        criteria = Criteria.where("templeteId").is("100");
        query.addCriteria(criteria);
        return mtRepository.findOne(query);
    }
    
    @Override
    public <T> T findListByCondition(T condition) throws Exception {
        return null;
    }
    
    public <T> void update(ReportRecordNewId updateData) throws Exception {
//        mtRepository.update(Query.query(Criteria.where("id").is(updateData.getId())), Update.update("periodDate", updateData.getPeriodDate()));
        mtRepository.update(Query.query(Criteria.where("id").is(updateData.getId())), 
                Update.update("periodDate", updateData.getPeriodDate()).addToSet("newlabel01", "newlabel01"));
    }
    
    public <T> void delete(ReportRecordNewId data) throws Exception {
        mtRepository.delete(data);
    }
}
