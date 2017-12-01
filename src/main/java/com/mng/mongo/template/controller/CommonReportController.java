package com.mng.mongo.template.controller;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.service.CommonReportService;

public class CommonReportController
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
    public void saveTest() throws Exception{
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        ReportRecordNewId rpt = new ReportRecordNewId();
        //      rpt.setName("abc");
        rpt.setTempleteId(11L);
        rpt.setCreatedTime(now);
        rpt.setPeriodDate(now);
        rpt.setFromBusitype(888);
        rpt.setFromObjId(188L);
        rpt.setStatus((byte) 1);
        mtService.save(rpt);
    }    
    
//    @Test
//    public void findByIdTest() throws Exception{
////        ObjectId("5a210a3639a7ed052c4c4898")
////        ObjectId("5a211d2539a7ed2a08cdf0ef")
//        ReportRecordNewId ret = mtService.findById("5a211d2539a7ed2a08cdf0ef");
//        if(null != ret)
//        {
//            System.out.println(ret.toString());
//        }
//    }    
    
//    @Test
//    //TODO return null
//    public void findOneTest() throws Exception{
//        Map<String, String> para = new HashMap<>();
//        para.put("templeteId", "11");
//        ReportRecordNewId ret = mtService.findOne(para);
//        if(null != ret)
//        {
//            System.out.println(ret.toString());
//        }
//    }
    
    
  @Test
  public void updateTest() throws Exception{
//      ObjectId("5a211a6e39a7ed3888350c36")
      ReportRecordNewId ret = mtService.findById("5a211a6e39a7ed3888350c36");
      if(null != ret)
      {
          System.out.println(ret.toString());
          Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
          ret.setPeriodDate(now);
          mtService.update(ret);
      }
  }  
  
  @Test
  public void deleteTest() throws Exception{
//     ObjectId("5a213c9a39a7ed0744ee74c8")
      String tedtId = "5a213c9a39a7ed0744ee74c8";
      ReportRecordNewId ret = mtService.findById(tedtId);
      if(null != ret)
      {
          System.out.println(ret.toString());
          mtService.delete(ret);
          ReportRecordNewId rettmp = mtService.findById(tedtId);
          
          ret.setFromObjId(20L);
          ret.setFromBusitype(20);
          Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
          ret.setPeriodDate(now);
          mtService.save(ret);
          ret = mtService.findById(tedtId);
          System.out.println("after delete ["+rettmp+"]");
          System.out.println("after resave ["+ret+"]");
      }
  }  
    
}
