package com.mng.mongo.template.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.repository.CommonReportRepository;

@SuppressWarnings("unchecked")
@Service
public class CommonReportService extends CommonReportRepository
{
  
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月1日 上午18:03:16
     * @description 保存单条数据
     * @since version 初始于版本 v0.0.1 
     * @param entity
     */
    public void saveReportRecord(ReportRecordNewId entity) {
        super.save(entity);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月1日 上午18:03:16
     * @description 根据主键查询记录
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @throws Exception
     */
    public ReportRecordNewId findById(String id) throws Exception {
        return super.findById(id);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 下午6:10:37
     * @description 按条件查询单条数据 （多条数据时取第一条）
     * @since version 初始于版本 v0.0.1 
     * @param para
     * @return
     * @throws Exception
     */
    public ReportRecordNewId findOneReportRecordByMap(Map<String, Object> para) throws Exception {
        Query query = new Query();
        if(null == para || para.size()==0) {
            return null;
        }
        for(String key: para.keySet()){
            query.addCriteria(Criteria.where(key).is(para.get(key)));
        }
        return super.findOne(query);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 下午6:10:45
     * @description 按条件查询多条数据（条件是map，不能包含时间参数，map的value值必须和数据库类型相对应）
     * @since version 初始于版本 v0.0.1 
     * @param templeteId
     * @param fromObjId
     * @param startPeriodDate
     * @param endPeriodDate
     * @param addremark
     * @return
     * @throws Exception
     */
    public List<ReportRecordNewId> findReportRecordListByMap(Map<String, Object> para) throws Exception {
        Query query = new Query();
        if(null == para || para.size()==0) {
            return null;
        }
        for(String key: para.keySet()){
            query.addCriteria(Criteria.where(key).is(para.get(key)));
        }
        return super.findListByCondition(query);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 下午6:10:45
     * @description 按条件查询多条数据（条件是单个参数，且包含时间参数）
     * @since version 初始于版本 v0.0.1 
     * @param templeteId
     * @param fromObjId
     * @param startPeriodDate
     * @param endPeriodDate
     * @param addremark
     * @return
     * @throws Exception
     */
    public List<ReportRecordNewId> findReportRecordListByCondition(Long templeteId, Long fromObjId, Date startPeriodDate, Date endPeriodDate, String addremark) throws Exception {
        Query query = new Query();
        
        if(null != templeteId && 0!=templeteId) {
            query.addCriteria(Criteria.where("templeteId").is(templeteId));
        }        
        if(null != fromObjId && 0!=fromObjId) {
            query.addCriteria(Criteria.where("fromObjId").is(fromObjId));
        }         
        if(null != startPeriodDate && null != endPeriodDate) {
            query.addCriteria(Criteria.where("periodDate").gte(startPeriodDate).andOperator(Criteria.where("periodDate").lte(endPeriodDate)));
        } else if(null != startPeriodDate) {
            query.addCriteria(Criteria.where("periodDate").gte(startPeriodDate));
        } else if(null != endPeriodDate) {
            query.addCriteria(Criteria.where("periodDate").lte(endPeriodDate));
        } 
        if(StringUtils.isNotBlank(addremark))
        {
            query.addCriteria(Criteria.where("addremark").regex(Pattern.compile("^.*"+addremark+".*$")));
        }
        /* 排序*/
        query.with(new Sort("createdTime"));//默认是 ASC
//        query.with(new Sort(new Order(Direction.DESC, "createdTime")));
//        query.with(new Sort("createdTime").and(new Sort("fromObjId")));//多个
        return super.findListByCondition(query);
    }
    
    //TODO
    public void updateReportRecord(ReportRecordNewId updateData) throws Exception {
//        super.update(Query.query(Criteria.where("id").is(updateData.getId())), Update.update("periodDate", updateData.getPeriodDate()));
        super.update(Query.query(Criteria.where("id").is(updateData.getId())), 
                Update.update("periodDate", updateData.getPeriodDate())
//                .addToSet("newlabeladdToSet", "newlabeladdToSet2")
//                .push("newlabelpush", "newlabelpush")
//                .pull("newlabel01", "newlabel01")
//                .inc("status", 1)
//                .rename("newlabel01", "newlabel02")
//                .set("newlabel02", "newlabel03")
//                .unset("newlabel02")
//                .set("newlabel04", "newlabel04")
                );
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午10:42:11
     * @description 根据对象实例删除一条记录
     * @since version 初始于版本 v0.0.1 
     * @param data
     * @throws Exception
     */
    public <T> void deleteReportRecord(ReportRecordNewId data) throws Exception {
        super.delete(data);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午10:10:42
     * @description 根据ID 添加字段
     *              方法二：直接在实体对象增加字段，调用保存方法
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param filedValue
     * @throws Exception
     */
    public void addFiled(Object id, String filedName, String filedValue) throws Exception
    {
        super.update(Query.query(Criteria.where("id").is(id)), Update.update(filedName, filedValue));
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午10:41:04
     * @description 根据ID 删除字段
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @throws Exception
     */
    public void deleteFiled(Object id, String filedName) throws Exception
    {
        super.update(Query.query(Criteria.where("id").is(id)), new Update().unset(filedName));
    }
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月4日 上午11:48:16
     * @description 重命名字段名称
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param newFiledName
     * @throws Exception
     */
    public void renameFiled(Object id, String filedName, String newFiledName) throws Exception
    {
        super.update(Query.query(Criteria.where("id").is(id)), new Update().rename(filedName, newFiledName));
    }

    
}