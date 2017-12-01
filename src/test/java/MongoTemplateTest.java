import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.service.CommonReportService;

public class MongoTemplateTest
{
    private static ApplicationContext app;
    @Autowired
    private static CommonReportService mtService;
    
//    @Test
    public void mtTest() throws Exception{
//        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
//        ReportRecordNewId rpt = new ReportRecordNewId();
//        //      rpt.setName("abc");
//        rpt.setTempleteId(11L);
//        rpt.setCreatedTime(now);
//        rpt.setPeriodDate(now);
//        rpt.setFromBusitype(888);
//        rpt.setFromObjId(188L);
//        rpt.setStatus((byte) 1);
//        mtService.save(rpt);
        
        Map<String, String> para = new HashMap<>();
        para.put("templeteId", "11");
//        ReportRecordNewId ret = mtService.findOne(para);
//        ObjectId("5a210a3639a7ed052c4c4898") fromBusitype=888
//        ObjectId("5a21098939a7ed1b7ce8d2d8") fromBusitype=666
        ReportRecordNewId ret = mtService.findById("5a21098939a7ed1b7ce8d2d8");
        if(null != ret)
        {
            System.out.println(ret.toString());
        }
        
    }
    
    @BeforeClass
    public static void initSpring() {
        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        mtService = (CommonReportService) app.getBean("commonReportService");
    }
    
}
