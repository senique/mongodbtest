package com.mng.mongo.template.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.mng.domain.ReportRecordNewId;
import com.mng.domain.ReportRecordResult;
import com.mng.mongo.template.repository.CommonReportRepository;
import com.mng.utils.page.PageResult;
import com.mng.utils.page.Pager;

@Service
public class CommonReportService extends CommonReportRepository
{
  
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月1日 上午18:03:16
     * @description 保存单条数据
     * @since version 初始于版本 v0.0.1 
     * @param reportRecord
     */
    public void saveReportRecord(ReportRecordNewId reportRecord) {
        super.save(reportRecord);
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
    public ReportRecordNewId findById(Long id) throws Exception {
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
     * @param para
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
     * @description 按条件查询多条数据
     * @since version 初始于版本 v0.0.1 
     * @param templateId
     * @param fromObjIds
     * @param startPeriodDate
     * @param endPeriodDate
     * @param addremark
     * @return
     * @throws Exception
     */
    public List<ReportRecordNewId> findReportRecordListByCondition(Long templateId, List<Long> fromObjIds, Date startPeriodDate, Date endPeriodDate, String addremark) throws Exception {
        Query query = new Query();
        
        dealSingleParameter(templateId, fromObjIds, startPeriodDate, endPeriodDate, addremark, query);
        return super.findListByCondition(query);
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月5日 下午5:20:51
     * @description 按条件查询多条数据,带分页条件（分页从0开始）
     * @since version 初始于版本 v0.0.1 
     * @param templateId
     * @param fromObjIds
     * @param startPeriodDate
     * @param endPeriodDate
     * @param addremark
     * @param pager
     * @return
     * @throws Exception
     */
    public PageResult<ReportRecordNewId> findReportRecordListByCondition(Long templateId, List<Long> fromObjIds, Date startPeriodDate, Date endPeriodDate, String addremark, Pager pager) throws Exception {
        Query query = new Query();
        
        dealSingleParameter(templateId, fromObjIds, startPeriodDate, endPeriodDate, addremark, query);
        return super.findListByCondition(query, pager);
    }

    //处理参数
    private void dealSingleParameter(Long templateId, List<Long> fromObjIds, Date startPeriodDate, Date endPeriodDate,
            String addremark, Query query)
    {
        if(null != templateId && 0!=templateId) {
            query.addCriteria(Criteria.where("templateId").is(templateId));
        }        
        if(!CollectionUtils.isEmpty(fromObjIds)) {
            query.addCriteria(Criteria.where("fromObjId").in(fromObjIds));
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
    }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月5日 上午9:28:48
     * @description 修改除主键外的 其他全部数据(修改字段需要在方法中指定)
     * @since version 初始于版本 v0.0.1 
     * @param updateData
     * @throws Exception
     */
    public void updateReportRecord(ReportRecordNewId updateData) throws Exception {
      super.update(updateData);
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
     * @createtime ： 2017年12月7日 下午5:44:23
     * @description 根据条件统计 报表内嵌字段(columnList)的数据
     * @since version 初始于版本 v0.0.1 
     * @param templateId
     * @param fromObjIds
     * @param startPeriodDate
     * @param endPeriodDate
     * @return
     */
    public AggregationResults<ReportRecordResult> aggregate(Long templateId, List<Long> fromObjIds, Date startPeriodDate, Date endPeriodDate)
    {
        if(null==templateId || 0L==templateId) {
           return null;
        }
        
        Criteria criteria = Criteria.where("templateId").is(templateId);
        if(!CollectionUtils.isEmpty(fromObjIds)) {
            criteria.and("fromObjId").in(fromObjIds);
        } 
        if(null != startPeriodDate && null != endPeriodDate) {
            criteria.and("periodDate").gte(startPeriodDate).andOperator(Criteria.where("periodDate").lte(endPeriodDate));
//            criteria.andOperator(Criteria.where("periodDate").gte(startPeriodDate).andOperator(Criteria.where("periodDate").lte(endPeriodDate)));
        }
        
        String columnInfoLabel = "columnList";
        AggregationOperation unwind = Aggregation.unwind(columnInfoLabel, "arrayIndex");
        AggregationOperation match = Aggregation.match(criteria);//
        AggregationOperation group = Aggregation.group("arrayIndex").sum(columnInfoLabel).as("sumValue"); //ReportRecordResult.sumValue
        
        Aggregation aggregation = Aggregation.newAggregation( unwind, match, group);
        
        return super.aggregate(aggregation, ReportRecordNewId.class, ReportRecordResult.class);
    }
    
    /* #统计嵌套文档元素（命令行OK，Java不OK）
        db.reportRecordNewId.aggregate([
            { $match: { "templateId" : 21, "fromObjId":{"$in" :[101,102,103,104,105,106,107,108]}}}, 
            { $group: { _id: "$templateId",turnOver: {$sum: "$columnInfo.turnOver"}
                                            ,orderCount: {$sum: "$columnInfo.orderCount"}
                                            ,profit: {$sum: "$columnInfo.profit"}
                                            ,count: {$sum: 1}
                                                }}
            ])
    */
    /**
     * @deprecated #统计嵌套文档元素（命令行OK，Java不OK）
     */
    @Deprecated 
    public AggregationResults<ReportRecordResult> aggregate()
    {
//      AggregationOperation unwind = Aggregation.unwind("columnList").newUnwind().path("").arrayIndex("arrayIndex").preserveNullAndEmptyArrays();
      AggregationOperation unwind = Aggregation.unwind("columnInfo", "arrayIndex");
      AggregationOperation match = Aggregation.match(Criteria.where("templateId").is(21).and("fromObjId").in(Arrays.asList(101)));
      
      /**
       * java.lang.NullPointerException
              at org.springframework.data.mapping.context.AbstractMappingContext.getPersistentPropertyPath(AbstractMappingContext.java:258)
//      AggregationOperation group = Aggregation.group("templateId").sum("$columnInfo.turnOver").as("turnOver");
       */
//      AggregationOperation group = Aggregation.group("templateId").sum("columnList").as("sumValue");
    AggregationOperation group = Aggregation.group("templateId").sum("columnInfo.turnOver").as("turnOver");

//      Aggregation aggregation = Aggregation.newAggregation(unwind, match, group);
//      Aggregation aggregation = Aggregation.newAggregation(match, group);
      Aggregation aggregation = Aggregation.newAggregation(group);
//      AggregationResults<Object> result = super.aggregate(aggregation, ReportRecordNewId.class, Object.class);
      AggregationResults<ReportRecordResult> result = super.aggregate(aggregation, ReportRecordNewId.class, ReportRecordResult.class);
      return result;
  }
    
    /**
     * 
     * @author：luocj
     * @createtime ： 2017年12月5日 上午9:39:08
     * @description 根据id 和 fieldName累加
     * @since version 初始于版本 v0.0.1 
     * @param id
     * @param filedName
     * @param filedValue
     * @throws Exception
     */
    public void increaseValueToFiled(Object id, String filedName, Long filedValue) throws Exception
    {
        super.increaseValueToFiled(id, filedName, filedValue);
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
        super.addFiled(id, filedName, filedValue);
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
        super.deleteFiled(id, filedName);
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
        super.renameFiled(id, filedName, newFiledName);
    }
    
}
