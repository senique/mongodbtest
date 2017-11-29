package utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;

public class DateUtils
{
    public static void main(String[] args) throws Exception
    {
        
        // Date d1 = parseDate("2016-01", "yyyy-MM");
        // Date d2 = parseDate("2016-04-14", "yyyy-MM-dd");
        // System.out.println(monthDiff(d1, d2));
        // Date date = getMonthBeginTime(getMiPaasNowChinaTime());
        // System.out.println(date.getTime());
        // System.out.println(Calendar.getInstance(Locale.CHINA).getTime());
        // System.out.println(Calendar.getInstance(Locale.CHINA).getTime());
        // System.out.println(format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        Date date2 = new Date(1465292886120L);
        
        System.out.println(date2);
        System.out.println(new Date());
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(getMiPaasNowChinaTime());
        
    }
    
    /**
     * 在oldDat的日期基础之上再加amount月
     * 
     * @return
     */
    public static Date dateAddMonth(Date oldDate, int offset)
    {
        if(oldDate != null)
        {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTime(oldDate);
            calendar.add(Calendar.MONTH, offset);
            return calendar.getTime();
        }
        return null;
    }
    
    /**
     * 在oldDat的日期基础之上再加offset 周
     * 
     * @return
     */
    public static Date dateAddWeek(Date oldDate, int offset)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        calendar.add(Calendar.WEEK_OF_YEAR, offset);
        return calendar.getTime();
    }
    
    /**
     * 在oldDat的日期基础之上再加amount天
     */
    public static Date dateAddDays(Date oldDate, int amount)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }
    
    /**
     * 在oldDat的日期基础之上再加amount年
     */
    public static Date dateAddYear(Date oldDate, int amount)
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, amount);
        return calendar.getTime();
    }
    
    // 计算两个时间之间相隔的分钟
    public static int minutesDiff(Date preDate, Date laterDate)
    {
        if(preDate != null && laterDate != null && preDate.before(laterDate))
        {
            long n1 = preDate.getTime();
            long n2 = laterDate.getTime();
            long diff = Math.abs(n1 - n2);
            diff /= 60 * 1000;
            return (int) diff;
        }
        return -1;
    }
    
    // 计算两个时间之间相隔的天数，如 1号 和 3号相隔两天
    public static int dayDiff(Date preDate, Date laterDate)
    {
        if(preDate != null && laterDate != null && preDate.before(laterDate))
        {
            long n1 = preDate.getTime();
            long n2 = laterDate.getTime();
            long diff = Math.abs(n1 - n2);
            diff /= 3600 * 1000 * 24;
            if(diff == 0) diff = isSameDay(preDate, laterDate) ? 0L : 1L;
            return (int) diff;
        }
        return -1;
    }
    
    // 计算两个时间之间相隔的周，如 2015年10月1号 和 2015年10月30号相隔 4周
    public static long weekDiff(Date d1, Date d2, Locale locale)
    {
        int weekDiff = -1;
        int dayDiff = dayDiff(d1, d2);
        
        Date preDate, laterDate;
        if(d1.before(d2))
        {
            preDate = d1;
            laterDate = d2;
        }
        else
        {
            preDate = d2;
            laterDate = d1;
        }
        
        if(dayDiff > 0)
        {
            weekDiff = dayDiff / 7;
            // 相差不足7天，也有可能相差1周
            if(dayDiff % 7 != 0)
            {
                Date td = dateAddDays(preDate, weekDiff * 7);
                weekDiff += isSameWeek(td, laterDate, locale) ? 0 : 1;
            }
        }
        return weekDiff;
    }
    
    // 计算两个时间之间相隔的月，如 2015年10月1号 和 2015年10月30号相隔 0月
    public static int monthDiff(Date d1, Date d2)
    {
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        Date preDate, laterDate;
        if(d1.before(d2))
        {
            preDate = d1;
            laterDate = d2;
        }
        else
        {
            preDate = d2;
            laterDate = d1;
        }
        c1.setTime(preDate);
        c2.setTime(laterDate);
        int yearDiff = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return yearDiff * 12 + result;
    }
    
    /**
     * 在oldDat的日期基础之上再加amount小时
     */
    public static Date dateAddHours(Date oldDate, Integer amount)
    {
        if(amount == null) return oldDate;
        
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        
        calendar.add(Calendar.HOUR_OF_DAY, amount);
        
        return calendar.getTime();
    }
    
    public static Date dateAddMinutes(Date oldDate, Integer amount)
    {
        if(amount == null) return oldDate;
        
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        
        calendar.add(Calendar.MINUTE, amount);
        
        return calendar.getTime();
    }
    
    /**
     * 增加秒
     * 
     * @param oldDate
     * @param secondAmout
     * @return
     * @throws Exception
     */
    public static Date dateAddSecond(Date oldDate, Integer secondAmout)
    {
        if(secondAmout == null) return oldDate;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(oldDate);
        
        calendar.add(Calendar.SECOND, secondAmout);
        
        return calendar.getTime();
    }
    
    /**
     * 当前时间的分钟对minute取模，然后当前时间减去取模的值，再把秒数设置为00
     * 
     * @param minute
     * @return
     * @throws Exception
     */
    public static Date findNowDateBeforeMinute(Integer minute) throws Exception
    {
        Date nowDate = getMiPaasNowChinaTime();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(nowDate);
        
        Integer curMinute = calendar.get(Calendar.MINUTE);
        Integer mod = curMinute % minute;
        
        calendar.add(Calendar.MINUTE, -mod);
        calendar.set(Calendar.SECOND, 0);
        
        return calendar.getTime();
    }
    
    /**
     * 查询现在时间到今天凌晨24:00:00的秒数
     */
    public static long findSecondsNowToToday()
    {
        Date now = getMiPaasNowChinaTime();
        
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(now);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        
        long time = (cal.getTime().getTime() - now.getTime()) / 1000;
        
        return time;
    }
    
    /**
     * 查询现在时间到这个月月底凌晨24:00:00的秒数
     */
    public static long findSecondsNowToMonthEnd()
    {
        Date now = getMiPaasNowChinaTime();
        
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(now);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        
        // System.out.println(formatDbDate(cal.getTime()));
        
        long time = (cal.getTime().getTime() - now.getTime()) / 1000;
        
        return time;
    }
    
    /**
     * 去除时分秒
     * 
     * @return
     */
    public static Date excludeSuffix(Date oldDate)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(oldDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * @param date
     *            时间对象
     * @param timePattern
     *            时间模式字符串
     * @return
     */
    public static String format(Date date, String timePattern)
    {
        if(date != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
            return sdf.format(date);
        }
        return null;
    }
    
    /**
     * 比较两个时间对象是否 同年
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameYear(Date d1, Date d2)
    {
        if(d1 == null || d2 == null)
        {
            return false;
        }
        
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(d1);
        
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        c2.setTime(d2);
        
        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 比较两个时间对象是否 同年同月
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameMonth(Date d1, Date d2)
    {
        boolean flag = isSameYear(d1, d2);
        if(flag)
        {
            Calendar c1 = Calendar.getInstance(Locale.CHINA);
            c1.setTime(d1);
            
            Calendar c2 = Calendar.getInstance(Locale.CHINA);
            c2.setTime(d2);
            
            if(c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
            {
                if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * @author：balthie@126.com
     * @createtime ： 2015年11月11日 下午5:13:43
     * @description 判断两个日期是否在同一周
     * @param locale
     *            中国习惯(周一到周日) ， 国外习惯(周日到周六)
     */
    public static boolean isSameWeek(Date d1, Date d2, Locale locale)
    {
        if(d1.equals(d2)) return true;
        Date td1 = getWeekBeginTime(d1, locale);
        Date td2 = getWeekBeginTime(d2, locale);
        return isSameDay(td1, td2);
    }
    
    /**
     * 比较两个时间对象是否 同年同月份同日
     * 
     * @param anlyDate
     * @param summaryDate
     * @return
     */
    public static boolean isSameDay(Date d1, Date d2)
    {
        return org.apache.commons.lang.time.DateUtils.isSameDay(d1, d2);
    }
    
    /**
     * @author : balthie@126.com
     * @createtime ： 2014-1-8 下午3:26:39
     * @description 获取指定日期 0时0分0秒的时间对象
     * @param calDate
     * @return
     */
    public static Date getDateBeginTime(Date date)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * @author : balthie@126.com
     * @createtime ： 2014-1-8 下午3:29:11
     * @description 获取指定日期 23时59分59秒的时间对象
     * @param calDate
     * @return
     */
    public static Date getDateEndTime(Date date)
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**
     * @author：bathie@126.com
     * @createtime ： 2015年11月11日 下午4:15:25
     * @description 取指定日期所在一周的第一天, 0时0分0秒的时间对象
     * @param locale
     *            中国习惯(周一到周日) ， 国外习惯(周日到周六)
     */
    public static Date getWeekBeginTime(Date date, Locale locale)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        if(locale == null || locale == Locale.CHINESE || locale == Locale.SIMPLIFIED_CHINESE)
        {
            cd.add(Calendar.DAY_OF_YEAR, -1); // 将日期映射到上周的周日到周六
            cd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        else
        {
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        
        return cd.getTime();
    }
    
    /**
     * @author：bathie@126.com
     * @createtime ： 2015年11月11日 下午4:15:25
     * @description 取指定日期所在一周的第7天, 23时59分59秒的时间对象
     * @param locale
     *            中国习惯(周一到周日) ， 国外习惯(周日到周六)
     */
    public static Date getWeekEndTime(Date date, Locale locale)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        if(locale == null || locale == Locale.CHINESE || locale == Locale.SIMPLIFIED_CHINESE)
        {
            cd.add(Calendar.DAY_OF_YEAR, 6); // 将日期映射到下周的周日到周六
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        else
        {
            cd.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }
        cd.set(Calendar.HOUR_OF_DAY, 23);
        cd.set(Calendar.MINUTE, 59);
        cd.set(Calendar.SECOND, 59);
        cd.set(Calendar.MILLISECOND, 999);
        return cd.getTime();
    }
    
    public static Date getMonthBeginTime(Date date)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }
    
    public static Date getMonthEndTime(Date date)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        cd.add(Calendar.MONTH, 1);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.add(Calendar.DAY_OF_MONTH, -1);
        cd.set(Calendar.HOUR_OF_DAY, 23);
        cd.set(Calendar.MINUTE, 59);
        cd.set(Calendar.SECOND, 59);
        cd.set(Calendar.MILLISECOND, 999);
        return cd.getTime();
    }
    
    public static Date getYearBeginTime(Date date)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        cd.set(Calendar.MONTH, Calendar.JANUARY);
        cd.set(Calendar.DAY_OF_MONTH, 1);
        cd.set(Calendar.HOUR_OF_DAY, 0);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        return cd.getTime();
    }
    
    public static Date getYearEndTime(Date date)
    {
        Calendar cd = Calendar.getInstance(Locale.CHINA);
        cd.setTime(date);
        cd.set(Calendar.MONTH, Calendar.DECEMBER);
        cd.set(Calendar.DAY_OF_MONTH, 31);
        cd.set(Calendar.HOUR_OF_DAY, 23);
        cd.set(Calendar.MINUTE, 59);
        cd.set(Calendar.SECOND, 59);
        cd.set(Calendar.MILLISECOND, 999);
        return cd.getTime();
    }
    
    /**
     * 
     * @description： 将字符串日期转换成日期类型 @createversion： 1.0
     * 
     * @author：luoyanwen 7738
     * @createtime 2015年5月12日上午11:33:17
     * @param dateStr
     *            字符串日期
     * @param timePattern
     *            日期格式
     * @return
     * @throws Exception
     */
    public static Date parseDate(String dateStr, String timePattern) throws ParseException
    {
        if(StringUtils.isBlank(dateStr) || StringUtils.isBlank(timePattern)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
        return sdf.parse(dateStr);
    }
    
    public static Timestamp parseDateNoException(String dateStr, String timePattern)
    {
        if(StringUtils.isNotBlank(dateStr) && StringUtils.isNotBlank(timePattern))
        {
            try
            {
                Date d = parseDate(dateStr, timePattern);
                
                return new Timestamp(d.getTime());
            }
            catch (ParseException e)
            {
            }
        }
        return null;
    }
    
    /**
     * @description： 判断是否为指定的日期格式 @createversion： 1.0
     * 
     * @author：luoyanwen 7738
     * @createtime 2015年5月12日上午10:34:06
     * @param r
     * @return
     */
    public static boolean isValidDate(String dateStr, String timePattern)
    {
        boolean flag = false;
        
        if(dateStr != null && timePattern != null)
        {
            SimpleDateFormat df = new SimpleDateFormat(timePattern);
            try
            {
                df.setLenient(true);
                df.parse(dateStr);
                return true;
            }
            catch (ParseException e)
            {
            }
        }
        return flag;
    }
    
    // 获取小米pass平台当前服务器北京时间（mipaas默认使用欧洲时区时间，比北京时间晚8小时）
    public static Date getMiPaasNowChinaTime()
    {
        return Calendar.getInstance(Locale.CHINA).getTime();
    }
    
    /**
     * 判断指定时间是否在两个特定时间之间
     * 
     * @author Jason Young
     * @date 2017年4月27日下午7:25:31
     */
    public static boolean checkTimeBetween2Times(Date checkedTime, Date fromTime, Date toTime)
    {
        boolean flag = false;
        if(fromTime == null || toTime == null || checkedTime == null) throw new RuntimeException("判断时间是否是指定时间之间时参数不可为空!");
        long fromMill = fromTime.getTime();
        long toMill = toTime.getTime();
        long checkMill = checkedTime.getTime();
        
        if(fromMill >= toMill)
        {
            if((checkMill - toMill) >= 0 && (fromMill - checkMill) >= 0) return !flag;
        }
        else if(toMill >= fromMill)
        {
            if((checkMill - fromMill) >= 0 && (toMill - checkMill) >= 0) return !flag;
        }
        return flag;
    }
    
    // 计算两个时间之间的秒数
    public static int secondsDiff(Date preDate, Date laterDate)
    {
        if(preDate != null && laterDate != null && preDate.before(laterDate))
        {
            long n1 = preDate.getTime();
            long n2 = laterDate.getTime();
            long diff = Math.abs(n2 - n1);
            diff /= 1000;
            return (int)diff;
        }
        return -1;
    }
}
