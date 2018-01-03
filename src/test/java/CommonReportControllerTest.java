
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import com.mng.domain.ReportRecordNew;
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
    public void saveTest() throws Exception{
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        ReportRecordNew rpt = new ReportRecordNew();
//            rpt.setName("abc");
//        rpt.setId(BigInteger.valueOf(101));
        rpt.setTemplateId(21L);
        rpt.setCreatedTime(now);
        rpt.setPeriodDate(now);
        rpt.setFromBusitype(222);
        rpt.setFromObjId(now.getTime());
        rpt.setStatus((byte) 1);
        mtService.saveReportRecord(rpt);

        Map<String, Object> para = new HashMap<>();
        para.put("fromObjId", now.getTime());
        ReportRecordNew ret = mtService.findOneReportRecordByMap(para);
        if(null != ret)
        {
            System.out.println(ret.toString());
        }
    }

    @Test
    public void findByIdTest() throws Exception{
        //ObjectId("5a210a3639a7ed052c4c4898")
        //ObjectId("5a211d2539a7ed2a08cdf0ef")
        ReportRecordNew ret = mtService.findById(1L);
        if(null != ret)
        {
            System.out.println(ret.toString());
        }
    }

    @Test
    public void findByMapTest() throws Exception{
        Map<String, Object> para = new HashMap<>();
        para.put("templeteId", 11);
        ReportRecordNew ret = mtService.findOneReportRecordByMap(para);
        if(null != ret)
        {
            System.out.println(ret.toString());
        }

        List<ReportRecordNew> retList = mtService.findReportRecordListByMap(para);
        if(null != retList)
        {
            System.out.println( ArrayUtils.toString(retList.stream().map(r->r.toString()).toArray()) );
        }
    }

    @Test
    public void findListByConditionTest() throws Exception{
        Long templateId = 100L;
        ArrayList<Long> fromObjId = new ArrayList<Long>();
        //DateUtils.parseDate("2017-12-02 10:21:08", "yyyy-MM-dd hh:mm:ss")
        Date startPeriodDate = null;
        Date endPeriodDate = null;
        String addremark = null;//支持"模糊查询"
        List<ReportRecordNew> retList = mtService.findReportRecordListByCondition(templateId, fromObjId, startPeriodDate, endPeriodDate, addremark );
        if(null != retList) {
            System.out.println( ArrayUtils.toString(retList.stream().map(r->r.toString()).toArray()) );
        }
    }
    @Test
    public void findListByConditionWithPage() throws Exception{
        Long templateId = 22L;
        ArrayList<Long> fromObjId = new ArrayList<Long>();
        //DateUtils.parseDate("2017-12-02 10:21:08", "yyyy-MM-dd hh:mm:ss")
        Date startPeriodDate = null;
        Date endPeriodDate = null;
        String addremark = null;//支持"模糊查询"
        Pager pager = new Pager(6, 0);
        PageResult<ReportRecordNew> pageRet = mtService.findReportRecordListByCondition(templateId, fromObjId, startPeriodDate, endPeriodDate, addremark, pager );
        if(null != pageRet) {
            System.out.println( pageRet.toString() );
            System.out.println( ArrayUtils.toString(pageRet.getList().stream().map(r->r.toString()).toArray()) );
        }
    }

    @Test
    public void updateTest() throws Exception{
        //ObjectId("5a211a6e39a7ed3888350c36")
        ReportRecordNew ret = mtService.findById(1L);
        if(null != ret)
        {
            System.out.println(ret.toString());
            Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
            ret.setPeriodDate(now);
            mtService.updateReportRecord(ret);

            //根据id 和 fieldName累加
            mtService.increaseValueToFiled("5a211a6e39a7ed3888350c36", "status", 2L);
        }
    }

    @Test
    public void deleteTest() throws Exception{
        //ObjectId("5a213c9a39a7ed0744ee74c8")
        Long tedtId = 1L;
        ReportRecordNew ret = mtService.findById(tedtId);
        if(null != ret)
        {
            System.out.println(ret.toString());
            mtService.deleteReportRecord(ret);
            ReportRecordNew rettmp = mtService.findById(tedtId);

            ret.setFromObjId(20L);
            ret.setFromBusitype(20);
            Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
            ret.setPeriodDate(now);
            mtService.saveReportRecord(ret);
            ret = mtService.findById(tedtId);
            System.out.println("after delete ["+rettmp+"]");
            System.out.println("after resave ["+ret+"]");
        }
    }

    @Test
    public void addOrDeleteOrRenameFiledTest() throws Exception{
        //ObjectId("5a211a6e39a7ed3888350c36")
        ReportRecordNew ret = mtService.findById(1L);
        if(null != ret)
        {
            System.out.println(ret.toString());
            mtService.addFiled(ret.getId(), "addremark", "这是测试模糊查询的remarktest");
//        mtService.renameFiled(ret.getId(), "newlabel05", "newlabel05");
//        mtService.deleteFiled(ret.getId(), "newlabel05");
        }
    }
    
}
