

import com.mng.mongo.template.service.CommonReportService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConfigTest
{
    private static ApplicationContext app;
    @BeforeClass
    public static void initSpring() {
        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
    }

    @Test
    public void randomTest() throws NamingException, ParseException, InterruptedException
    {
//        # javax.naming.NoInitialContextException: Need to specify class name in environment or system property, 
//        #or as an applet parameter, or in an application resource file:  java.naming.factory.initial
        InitialContext ctx = new InitialContext();
        System.out.println(ctx);
        
////        Map<Integer, Integer> rdMap = new HashMap<Integer, Integer>();
//        Map<Integer, Integer> rdMap = new HashMap<Integer, Integer>();
//        for(int i = 0; i < 1000; i++) {
//            int rd = new Random().nextInt(3);
//            if(null != rdMap.get(rd)) {
//                rdMap.put(rd, (Integer)rdMap.get(rd)+1);
//            }
//            else {
//                rdMap.put(rd, 1);
//            }
////            System.out.println(rd);
//        }
//        System.out.println(rdMap.toString());
        

    }
    
}
