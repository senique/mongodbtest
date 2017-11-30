import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.mng.domain.ReportRecordNewId;
import com.mng.mongo.template.service.MongoTemplateService;

public class MongoTemplateTest
{
    @Autowired
    private MongoTemplateService mtService;
    
    @Test
    public void mtTest(){
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        ReportRecordNewId rpt = new ReportRecordNewId();
        //      rpt.setName("abc");
        rpt.setTempleteId(100L);
        rpt.setCreatedTime(now);
        rpt.setPeriodDate(now);
        rpt.setFromBusitype(666);
        rpt.setFromObjId(188L);
        rpt.setStatus((byte) 1);
        
        mtService.save(rpt);
    }
    
}
