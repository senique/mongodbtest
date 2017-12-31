import com.mng.domain.MapReduceResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

public class MapReduceTest {
	
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
//        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
//        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		complexMapreduce();
	}
	
	private static void complexMapreduce() {
		MapReduceOptions options = MapReduceOptions.options();
		options.outputCollection("Result_MapReduce");
		options.outputTypeReduce();
		options.finalizeFunction("classpath:mapreduce/finalize.js");
		MapReduceResults<MapReduceResult> reduceResults = mongoTemplate.mapReduce("ReportRecordNew",  "classpath:mapreduce/map.js",
				"classpath:mapreduce/reduce.js", options, MapReduceResult.class);
		reduceResults.forEach(System.out::println);
	}
}
