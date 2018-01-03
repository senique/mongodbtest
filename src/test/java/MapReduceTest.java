import com.mng.domain.MapReduceResult;
import com.mng.mongo.template.service.CommonReportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

public class MapReduceTest {

    private static MongoTemplate mongoTemplate;

    private static CommonReportService mtService;

    static {
        //加载spring
//        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
//        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ApplicationContext app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        mongoTemplate = (MongoTemplate) app.getBean("mongoTemplate");
        mtService = (CommonReportService) app.getBean("commonReportService");
    }

    @Test
    public void complexMapReduce() {
        MapReduceOptions options = MapReduceOptions.options();
        options.outputCollection("Result_MapReduce");//文档结果输出到集合
        options.outputTypeReduce();
        options.finalizeFunction("classpath:mapreduce/finalize.js");

        MapReduceResults<MapReduceResult> reduceResults = mongoTemplate.mapReduce("reportRecordNew",  "classpath:mapreduce/map.js",
                "classpath:mapreduce/reduce.js", options, MapReduceResult.class);
        reduceResults.forEach(System.out::println);
    }

    @Test
    public void mapReduceApiTest(){
        MapReduceResults<MapReduceResult> mapReduceResults = mtService.mapReduce(21L, null, null,null);
        if (null != mapReduceResults){
            mapReduceResults.forEach(System.out::println);
        }
    }
}
