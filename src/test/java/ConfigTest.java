

import java.text.ParseException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConfigTest
{
    
    public static void main(String[] args) throws NamingException, ParseException, InterruptedException
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
        
        
       /* 
        Date beginDate = DateUtils.parseDate("2016-10-01", "yyyy-MM-dd");
        Date endDate = DateUtils.parseDate("2017-12-31", "yyyy-MM-dd");
        
//        long count = DateUtils.monthDiff(beginDate, endDate);
        long count = DateUtils.dayDiff(beginDate, endDate);
        System.out.println(count);

        StopWatch sw = new StopWatch();
        
        for(int i = 0; i <= count; i++)
        {
          sw.start("test "+i);
//          System.out.println("["+i+"]"+DateUtils.dateAddMonth(beginDate, i));
//          System.out.println("["+i+"]"+DateUtils.format(DateUtils.dateAddMonth(beginDate, i), "yyyy-MM-dd HH:mm:ss"));
//          TimeUnit.SECONDS.sleep(1);
          sw.stop();
//          System.out.println(sw.getTotalTimeMillis()+"["+i+"]"+DateUtils.format(DateUtils.dateAddMonth(beginDate, i), "yyyy-MM-dd HH:mm:ss")+"-"+DateUtils.format(DateUtils.dateAddMonth(beginDate, i+1), "yyyy-MM-dd HH:mm:ss"));
//          System.out.println(sw.getTotalTimeMillis()+"["+i+"]"+DateUtils.format(DateUtils.dateAddMonth(beginDate, i), "yyyy-MM-dd HH:mm:ss")+"-"+DateUtils.format( DateUtils.getMonthEndTime(DateUtils.dateAddMonth(beginDate, i)), "yyyy-MM-dd HH:mm:ss"));
          System.out.println(DateUtils.format(DateUtils.dateAddDays(beginDate, i), "yyyy-MM-dd"));
          
        }
        */
        
        


    }
    
}
