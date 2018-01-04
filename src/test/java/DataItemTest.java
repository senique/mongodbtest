import com.mng.domain.DataItem;
import com.mng.mongo.template.dao.MongoTemplateRepository;
import com.mng.mongo.template.repository.DataItemRepository;
import com.mng.utils.date.DateUtils;
import com.mng.utils.sequence.SaveMongoEventListener;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class DataItemTest
{
    private static ApplicationContext app;
    @Autowired
    private static MongoTemplateRepository mongoTemplate;

    @Autowired
    private static DataItemRepository dataItemRepository;

//    @Autowired
//    private AbstractApplicationContext context;
//    @Autowired
//    private AutowireCapableBeanFactory beanFactory;
//    @Autowired
//    /**
//     * 注册Listener 通过注解@GeneratedValue指定主键ID值 （大数据量情况性能相对会下降）
//     * [ApplicationListenerDetector] Inner bean 'com.viomi.common.utils.sequence.SaveMongoEventListener'
//     * implements ApplicationListener interface but is not reachable for event multicasting by its containing
//     * ApplicationContext because it does not have singleton scope. Only top-level listener beans are allowed to be of non-singleton scope.
//     */
//    public void registerListeners() {
//        SaveMongoEventListener firstListener = beanFactory.createBean(SaveMongoEventListener.class);
//        context.addApplicationListener(firstListener);
//    }

    @BeforeClass
    public static void initSpring() {
        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        dataItemRepository = (DataItemRepository) app.getBean("dataItemRepository");
    }



    @Test
    public void aggregateTest() throws Exception{
        StopWatch sw = new StopWatch();
        sw.start();
        

        sw.stop();
        System.out.println(sw.prettyPrint());
    }
    
    @Test
    public void prepareDataTest() throws Exception {
//        TimeZone.getTimeZone("Asia/Shanghai");
//        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)));
        Date now = DateUtils.getMiPaasNowChinaTime();
        Date rdDate = DateUtils.getDateBeginTime(now);
        DataItem rpt = new DataItem();
//        rpt.setId(BigInteger.valueOf(101));

        StopWatch sw = new StopWatch();
        sw.start("CommonReportControllerTest.prepareDataTest() ");

        for (int i = 0; i < 1; i++) {
            rdDate = DateUtils.getDateBeginTime(DateUtils.dateAddDays(now, new Random().nextInt(100) - 100));

            rpt.setCount(21L + new Random().nextInt(3));
            rpt.setCreatedTime(now);

            //test for id\
            rpt.setName("testid"+ new Random().nextInt(100));
            rpt.setbId(BigInteger.valueOf(100+new Random().nextInt(50)));
            rpt.setObjId(ObjectId.get());

            dataItemRepository.save(rpt);
        }
    }
    
}
