import com.mng.domain.ReportRecordNew;
import com.mng.domain.ReportRecordResult;
import com.mng.mongo.template.service.CommonReportService;
import com.mng.utils.date.DateUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.util.StopWatch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AggregateTest {

    @Autowired
    private static CommonReportService mtService;

    @Test
    public void aggregateTest() throws Exception{
        StopWatch sw = new StopWatch();
        sw.start();

        Long templateId = 21L;
        List<Long> fromObjIds = new ArrayList<>();
//        fromObjIds.add(101L);
//        fromObjIds.add(108L);
//        for(int i = 0; i < 200; i++) {
//            fromObjIds.add(Long.valueOf(i+1));
//        }
        //DateUtils.parseDate("2017-12-05 23:00:00", "yyyy-MM-dd hh:mm:ss")
        Date startPeriodDate = DateUtils.parseDate("2017-10-01 00:00:00", "yyyy-MM-dd hh:mm:ss");
        Date endPeriodDate = DateUtils.parseDate("2017-11-30 23:00:00", "yyyy-MM-dd hh:mm:ss");

        AggregationResults<ReportRecordResult> aggrRet = mtService.aggregate(templateId, fromObjIds, startPeriodDate, endPeriodDate);
        //        AggregationResults<ReportRecordResult> aggrRet = mtService.aggregate();

        if(null != aggrRet) {
//          System.out.println( aggrRet.toString() );
//          System.out.println( aggrRet.getRawResults() );
//          System.out.println( aggrRet.getMappedResults() );
            System.out.println( ArrayUtils.toString(aggrRet.getMappedResults().stream().map(r->r.toString()).toArray()) );

            List<ReportRecordResult> rrRet = aggrRet.getMappedResults();
//          rrRet.stream().map(r->r.getId()+"|"+r.getSumValue()).map(System.out::println);
            System.out.println( ArrayUtils.toString(rrRet.stream().map(r->r.getId()+"|"+r.getSumValue()).toArray()) );

//          DBObject ret1 = aggrRet.getRawResults();
//          List<Object> ret2 = aggrRet.getMappedResults();
//          System.out.println(ret1.get("result"));
//          BasicDBList ret3 = (BasicDBList) ret1.get("result");
//
//          DBObject tmp = null;
//          for(int i = 0; i < ret3.size(); i++)
//        {
//              tmp = ((DBObject)ret3.get(i));
//              System.out.println( tmp.get("_id")+"|"+tmp.get("turnOver") );
//        }
        }
        sw.stop();
        System.out.println(sw.prettyPrint());
    }

    @Test
    public void prepareDataTest() throws Exception{
//        TimeZone.getTimeZone("Asia/Shanghai");
//        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)));
        Date now = DateUtils.getMiPaasNowChinaTime();
        Date rdDate = DateUtils.getDateBeginTime(now);
        ReportRecordNew rpt = new ReportRecordNew();
//        rpt.setId(BigInteger.valueOf(101));
        int NUM_OF_DOCUMENTS = 1000000;

        StopWatch sw = new StopWatch();
        sw.start("CommonReportControllerTest.prepareDataTest() ");

        for(int i = 0; i < NUM_OF_DOCUMENTS; i++) {
            rdDate = DateUtils.getDateBeginTime(DateUtils.dateAddDays(now,  new Random().nextInt(100)-100));

//            rpt.setTemplateId(21L + new Random().nextInt(3));
            rpt.setTemplateId(21L+ new Random().nextInt(3));
            rpt.setCreatedTime(now);
            rpt.setPeriodDate(rdDate);
            rpt.setFromBusitype(11);
            rpt.setFromObjId(1L + new Random().nextInt(2000));
//            rpt.setFromObjId(Long.valueOf(i+1));
            rpt.setStatus((byte) 1);

            /*
              "turnOver": 0,
              "orderCount": 0,
              "profit": 0,
              "beginTime": 1506528000000,
              "endTime": 1506563009030
             */
            DecimalFormat df = new DecimalFormat("#.00");
            BasicDBObject columnInfo = new BasicDBObject()
                    // 总交易金额
                    .append("turnOver", Double.valueOf(df.format(100 + new Random().nextDouble())))
                    // 总订单数量
                    .append("orderCount", 1 + new Random().nextInt(3))
                    // 总分润金额
                    .append("profit", Double.valueOf(df.format(50 + new Random().nextDouble())))
                    // 营销费用余额
                    .append("salesFee", Double.valueOf(df.format(10 + new Random().nextDouble())))
                    // 提成分享
                    .append("participateProfit", Double.valueOf(df.format(10 + new Random().nextDouble())))
//                    .append("beginTime", rdDate)
//                    .append("endTime", now)
                    ;
            rpt.setColumnInfo(columnInfo);

            /*
              0,
              0,
              0,
              1506528000000,
              1506563009030
             */
            BasicDBList columnList = new BasicDBList();
            columnList.add(columnInfo.get("turnOver"));
            columnList.add(columnInfo.get("orderCount"));
            columnList.add(columnInfo.get("profit"));
            columnList.add(columnInfo.get("salesFee"));
            columnList.add(columnInfo.get("participateProfit"));
//            columnList.add(columnInfo.get("beginTime"));
//            columnList.add(columnInfo.get("endTime"));
            rpt.setColumnList(columnList);
//            rpt.setTurnOver(123.45);

            mtService.saveReportRecord(rpt);
        }

        sw.stop();
        System.out.println(DateUtils.format(now, "yyyy-MM-dd hh:mm:ss")+"|"+DateUtils.format(rdDate, "yyyy-MM-dd hh:mm:ss"));
        System.out.println(sw.prettyPrint());
//        TimeUnit.SECONDS.sleep(150L);
    }
}
