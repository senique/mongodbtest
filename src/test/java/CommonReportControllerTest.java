
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.util.StopWatch;
import com.mng.domain.ReportRecordNewId;
import com.mng.domain.ReportRecordResult;
import com.mng.mongo.template.service.CommonReportService;
import com.mng.utils.BeanConvertUtil;
import com.mng.utils.date.DateUtils;
import com.mng.utils.page.PageResult;
import com.mng.utils.page.Pager;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class CommonReportControllerTest
{
    private static ApplicationContext app;
    @Autowired
    private static CommonReportService mtService;
    
    @BeforeClass
    public static void initSpring() {
        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        mtService = (CommonReportService) app.getBean("commonReportService");
    }

    @Test
    public void aggregateTest() throws Exception{
        StopWatch sw = new StopWatch();
        sw.start();
        
        Long templateId = 21L;
        List<Long> fromObjIds = new ArrayList<>();
//        fromObjIds.add(101L);
//        fromObjIds.add(108L);
//        for(int i = 0; i < 200; i++) {
//            fromObjIds.add(Long.valueOf(i+1));
//            
//            Date startPeriodDate = DateUtils.parseDate("2017-10-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
//            Date endPeriodDate = DateUtils.parseDate("2017-11-30 23:00:00", "yyyy-MM-dd hh:mm:ss");
//            
//            AggregationResults<ReportRecordResult> aggrRet = mtService.aggregate(templateId, Arrays.asList(Long.valueOf(i+1)), startPeriodDate, endPeriodDate);
//        }
        //DateUtils.parseDate("2017-12-05 23:00:00", "yyyy-MM-dd hh:mm:ss")
        Date startPeriodDate = DateUtils.parseDate("2017-10-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
        Date endPeriodDate = DateUtils.parseDate("2017-11-30 23:00:00", "yyyy-MM-dd hh:mm:ss");
        
        AggregationResults<ReportRecordResult> aggrRet = mtService.aggregate(templateId, fromObjIds, startPeriodDate, endPeriodDate);
        if(null != aggrRet) {
//          System.out.println( aggrRet.toString() );
//          System.out.println( aggrRet.getRawResults() );
//          System.out.println( aggrRet.getMappedResults() );
            System.out.println( ArrayUtils.toString(aggrRet.getMappedResults().stream().map(r->r.toString()).toArray()) );
          
          List<ReportRecordResult> rrRet = aggrRet.getMappedResults();
//          rrRet.stream().map(r->r.getId()+"|"+r.getSumValue()).map(System.out::println);
          System.out.println( ArrayUtils.toString(rrRet.stream().map(r->r.getId()+"|"+r.getSumValue()).toArray()) );
          
//          DBObject ret1 = aggrRet.getRawResults();
//          List<Object> ret2 = aggrRet.getMappedResults();
//          System.out.println(ret1.get("result"));
//          BasicDBList ret3 = (BasicDBList) ret1.get("result");
//
//          DBObject tmp = null;
//          for(int i = 0; i < ret3.size(); i++)
//        {
//              tmp = ((DBObject)ret3.get(i));
//              System.out.println( tmp.get("_id")+"|"+tmp.get("turnOver") );
//        }
        }
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
    
    @Test
    public void prepareDataTest() throws Exception{
//        TimeZone.getTimeZone("Asia/Shanghai");
//        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)));
        Date now = DateUtils.getMiPaasNowChinaTime();
        Date rdDate = DateUtils.getDateBeginTime(now);
        ReportRecordNewId rpt = new ReportRecordNewId();
//        rpt.setId(BigInteger.valueOf(101));
        
        StopWatch sw = new StopWatch();
        sw.start("CommonReportControllerTest.prepareDataTest() ");
        
        for(int i = 0; i < 100000; i++) {
            rdDate = DateUtils.getDateBeginTime(DateUtils.dateAddDays(now,  new Random().nextInt(100)-100));
            
//            rpt.setTemplateId(21L + new Random().nextInt(3));
            rpt.setTemplateId(21L);
            rpt.setCreatedTime(now);
            rpt.setPeriodDate(rdDate);
            rpt.setFromBusitype(11);
            rpt.setFromObjId(1L + new Random().nextInt(2000));
//            rpt.setFromObjId(Long.valueOf(i+1));
            rpt.setStatus((byte) 1);
            
            DecimalFormat df = new DecimalFormat("#.00");
            BasicDBObject columnInfo = new BasicDBObject()
                    // 总交易金额
                    .append("turnOver", Double.valueOf(df.format(100 + new Random().nextDouble())))
                    // 总订单数量
                    .append("orderCount", 1 + new Random().nextInt(3))
                    // 总分润金额
                    .append("profit", Double.valueOf(df.format(50 + new Random().nextDouble())))
                    // 营销费用余额
                    .append("salesFee", Double.valueOf(df.format(10 + new Random().nextDouble())))
                    // 提成分享
                    .append("participateProfit", Double.valueOf(df.format(10 + new Random().nextDouble())))
//                    .append("beginTime", rdDate)
//                    .append("endTime", now)
                    ;
            rpt.setColumnInfo(columnInfo);
            
            BasicDBList columnList = new BasicDBList();
            columnList.add(columnInfo.get("turnOver"));
            columnList.add(columnInfo.get("orderCount"));
            columnList.add(columnInfo.get("profit"));
            columnList.add(columnInfo.get("salesFee"));
            columnList.add(columnInfo.get("participateProfit"));
//            columnList.add(columnInfo.get("beginTime"));
//            columnList.add(columnInfo.get("endTime"));
            rpt.setColumnList(columnList);
//            rpt.setTurnOver(123.45);

            mtService.saveReportRecord(rpt);
        }
        
        
        
        /*
        "turnOver": 0,
        "orderCount": 0,
        "profit": 0,
        "beginTime": 1506528000000,
        "endTime": 1506563009030
         */
        
//        Map<String, Object> para = new HashMap<>();
//        para.put("fromObjId", now.getTime());
//        ReportRecordNewId ret = mtService.findOneReportRecordByMap(para);
//        if(null != ret)
//        {
//          System.out.println(ret.toString());
//        }
        
        sw.stop();
        System.out.println(DateUtils.format(now, "yyyy-MM-dd hh:mm:ss")+"|"+DateUtils.format(rdDate, "yyyy-MM-dd hh:mm:ss"));
        System.out.println(sw.prettyPrint());
//        TimeUnit.SECONDS.sleep(150L);
    }    
    
    @Test
    public void findByIdTest() throws Exception{
        //ObjectId("5a210a3639a7ed052c4c4898")
        //ObjectId("5a211d2539a7ed2a08cdf0ef")
//        ReportRecordNewId ret = mtService.findById(6190);
        ReportRecordNewId ret = mtService.findById(6190L);
        if(null != ret)
        {
          System.out.println(ret.toString());
        }
    }    
    
    @Test
    public void findByMapTest() throws Exception{
        Map<String, Object> para = new HashMap<>();
//        para.put("templateId", 21);
        para.put("id", 4069);
        ReportRecordNewId ret = mtService.findOneReportRecordByMap(para);
        if(null != ret)
        {
          System.out.println(ret.toString());
        }
        
        List<ReportRecordNewId> retList = mtService.findReportRecordListByMap(para);
        if(null != retList)
        {
          System.out.println( ArrayUtils.toString(retList.stream().map(r->r.toString()).toArray()) );
        }
    }
    
    @Test
  public void findListByConditionTest() throws Exception{
        Long templateId = 21L;
        
//        List<Long> fromObjIds = Arrays.asList(110L);
        List<Long> fromObjIds = new ArrayList<>();
        fromObjIds.add(101L);
        fromObjIds.add(108L);
        //DateUtils.parseDate("2017-12-05 23:00:00", "yyyy-MM-dd hh:mm:ss")
        Date startPeriodDate = DateUtils.parseDate("2017-10-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
        Date endPeriodDate = DateUtils.parseDate("2017-11-30 23:00:00", "yyyy-MM-dd hh:mm:ss");
        String addremark = null;//支持"模糊查询"
        List<ReportRecordNewId> retList = mtService.findReportRecordListByCondition(templateId, fromObjIds, startPeriodDate, endPeriodDate, addremark );
        if(null != retList) {
            System.out.println( ArrayUtils.toString(retList.stream().map(r->r.toString()).toArray()) );
        }
  }
    @Test
    public void findListByConditionWithPagerTest() throws Exception{
          Long templateId = 22L;
          List<Long> fromObjIds = new ArrayList<>();
          fromObjIds.add(101L);
          fromObjIds.add(108L);
          //DateUtils.parseDate("2017-12-02 10:21:08", "yyyy-MM-dd hh:mm:ss")
          Date startPeriodDate = null;
          Date endPeriodDate = null;
          String addremark = null;//支持"模糊查询"
          Pager pager = new Pager(6, 0);
          PageResult<ReportRecordNewId> pageRet = mtService.findReportRecordListByCondition(templateId, fromObjIds, startPeriodDate, endPeriodDate, addremark, pager );
          if(null != pageRet) {
              System.out.println( pageRet.toString() );
              System.out.println( ArrayUtils.toString(pageRet.getList().stream().map(r->r.toString()).toArray()) );
          }
    }
    
  @Test
  public void updateTest() throws Exception{
  //ObjectId("5a211a6e39a7ed3888350c36")
  Long id = 8L;
  ReportRecordNewId ret = mtService.findById(id);
  if(null != ret)
  {
    System.out.println(ret.toString());
    BeanConvertUtil.bean2DBObject(ret);
    
//    Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
    Date now = DateUtils.getMiPaasNowChinaTime();
    ret.setCreatedTime(now);
    mtService.updateReportRecord(ret);
    
    ReportRecordNewId ret2 = mtService.findById(id);
    System.out.println(ret2.getCreatedTime());
    
//    
//    //根据id 和 fieldName累加
//    mtService.increaseValueToFiled("5a211a6e39a7ed3888350c36", "status", 2L);
  }
}   
//  
//  @Test
//  public void deleteTest() throws Exception{
//     //ObjectId("5a213c9a39a7ed0744ee74c8")
//      String tedtId = "5a213c9a39a7ed0744ee74c8";
//      ReportRecordNewId ret = mtService.findById(tedtId);
//      if(null != ret)
//      { 
//        System.out.println(ret.toString());
//        mtService.deleteReportRecord(ret);
//        ReportRecordNewId rettmp = mtService.findById(tedtId);
//        
//        ret.setFromObjId(20L);
//        ret.setFromBusitype(20);
//        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
//        ret.setPeriodDate(now);
//        mtService.saveReportRecord(ret);
//        ret = mtService.findById(tedtId);
//        System.out.println("after delete ["+rettmp+"]");
//        System.out.println("after resave ["+ret+"]");
//      }
//  }  
//  
//  @Test
//  public void addOrDeleteOrRenameFiledTest() throws Exception{
//      //ObjectId("5a211a6e39a7ed3888350c36")
//      ReportRecordNewId ret = mtService.findById("5a211a6e39a7ed3888350c36");
//      if(null != ret)
//      {
//        System.out.println(ret.toString());
//        mtService.addFiled(ret.getId(), "addremark", "这是测试模糊查询的remarktest");
////        mtService.renameFiled(ret.getId(), "newlabel05", "newlabel05");
////        mtService.deleteFiled(ret.getId(), "newlabel05");
//      }
//  } 
    
}
