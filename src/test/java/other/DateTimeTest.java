package other;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mng.utils.date.DateUtils;
import org.springframework.util.StopWatch;

public class DateTimeTest
{
    private static final Logger logger = LoggerFactory.getLogger(DateTimeTest.class);
    
    public static void main(String[] args)
    {
        
        Date now1 = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        Date now2 = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)));
        Date now3 = DateUtils.getMiPaasNowChinaTime();
        Date now4 = DateUtils.getDateBeginTime(now3);
        Date now5 = DateUtils.getDateBeginTime(now2);
        String now6 = DateUtils.format(now3, "yyyy-MM-dd hh:mm:ss");
//        Date rdDate = DateUtils.getDateBeginTime(now);
        
        logger.debug("test datetime for now: \n[{}]\n[{}]\n[{}]\n[{}]\n[{}]\n[{}]\n[{}]", new Object[]{now1,now2,now3,now4,now5,now6});
    }

    public void loopDateTest(){
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = DateUtils.parseDate("2016-10-01", "yyyy-MM-dd");
            endDate = DateUtils.parseDate("2017-12-31", "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
    }
}
