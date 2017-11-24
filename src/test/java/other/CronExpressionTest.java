/**
 * @author：luocj
 * @createtime ： 2017年10月18日 下午3:44:18
 * @description TODO 一句话描述
 */
package test.java.other;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import org.junit.Test;

/**
 * 秒分时 天月周 年
 * N Field Name Mandatory Allowed Values Allowed Special Characters
 * 1 Seconds YES 0-59 , - * /
 * 2 Minutes YES 0-59 , - * /
 * 3 Hours YES 0-23 , - * /
 * 4 Day of month YES 1-31 , - * ? / L W
 * 5 Month YES 1-12 or JAN-DEC , - * /
 * 6 Day of week YES 1-7 or SUN-SAT , - * ? / L #
 * _ Year NO empty, 1970-2099 , - * /
 *
 * * 字符代表所有可能的值
 * / 字符用来指定数值的增量
 * ？ 表示不指定值
 * L 单词“last”的缩写
 * W 表示为最近工作日
 * # 是用来指定“的”每月第n个工作日
 * 
 * 【Java 8时间和日期API】
 * Instant：瞬时实例。
 * LocalDate：本地日期，不包含具体时间 例如：2014-01-14 可以用来记录生日、纪念日、加盟日等。
 * LocalTime：本地时间，不包含日期。
 * LocalDateTime：组合了日期和时间，但不包含时差和时区信息。
 * ZonedDateTime：最完整的日期时间，包含时区和相对UTC或格林威治的时差。
 */
public class CronExpressionTest
{
    @Test
    public void maintest() throws ParseException
    {
//        String token = "T63hTyVs1yrr5rkz";
//        ILoginData iData = AuthCache.loadLoginData(token);
//        CrmlLoginData crmData = AuthCache.loadLoginData(token, CrmlLoginData.class);
//        Long channelId = crmData.getChannelId();
        
        // 秒分时 天月周 年
        String time = "0 0 0 7 * ?";// 每月7日
//        String time = "59 59 23 ? * 2#1";// 每月第二个周一
//        String time = "* 0 8,13,18 * * ? ";// 
               
        
//        CronExpression ctime = new CronExpression(time);
        CronExpressionNew ctime = new CronExpressionNew(time);
//        CronCalendar cctime = new CronCalendar(time);
        
//        Date now = DateUtils.getMiPaasNowChinaTime();
        Date now = Date.from(Instant.now());
        System.out.println(now);
        System.out.println(ctime.getNextValidTimeAfter(now));
        System.out.println(ctime.getTimeAfter(now));
//      System.out.println(ctime.getNextInvalidTimeAfter(now));
//      System.out.println(ctime.getTimeBefore(now));
        
    }
}
