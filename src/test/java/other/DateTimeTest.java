package other;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mng.utils.date.DateUtils;

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
}
