package com.mng.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import com.mng.utils.sequence.GeneratedValue;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class ReportRecordNewId
{
    @Id
    @GeneratedValue
    private long id = 0L;

    private Long templateId;
    
    private Date createdTime;
    
//    @TemporalValue
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date periodDate;
    
    private Integer fromBusitype;
    
    private Long fromObjId;
    
    private Byte status;
    
    // 自动添加字段到数据库
//    private Double turnOver;
//    public Double getTurnOver()
//    {
//        return turnOver;
//    }
//
//    public void setTurnOver(Double turnOver)
//    {
//        this.turnOver = turnOver;
//    }
    
    /*
    "turnOver": 0,
    "orderCount": 0,
    "profit": 0,
    "beginTime": 1506528000000,
    "endTime": 1506563009030
     */
    private BasicDBObject columnInfo;
    /*
    0,
    0,
    0,
    1506528000000,
    1506563009030
     */
    private BasicDBList columnList;
    
    public ReportRecordNewId()
    {
        super();
    }
    
    public ReportRecordNewId(Long templateId, Date periodDate, Integer fromBusitype, Long fromObjid, Byte status)
    {
        super();
        this.templateId = templateId;
        this.periodDate = periodDate;
        this.fromBusitype = fromBusitype;
        this.fromObjId = fromObjid;
        this.status = status;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
//    @Column(name = "template_id")
    public Long getTemplateId()
    {
        return templateId;
    }
    
    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }
    
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_time", updatable = false, insertable = false)
    public Date getCreatedTime()
    {
        return createdTime;
    }
    
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }
    
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "period_date")
    public Date getPeriodDate()
    {
        return periodDate;
    }
    
    public void setPeriodDate(Date periodDate)
    {
        this.periodDate = periodDate;
    }
    
//    @Column(name = "from_busitype")
    public Integer getFromBusitype()
    {
        return fromBusitype;
    }
    
    public void setFromBusitype(Integer fromBusitype)
    {
        this.fromBusitype = fromBusitype;
    }
    
//    @Column(name = "from_objid")
    public Long getFromObjId()
    {
        return fromObjId;
    }
    
    public void setFromObjId(Long fromObjid)
    {
        this.fromObjId = fromObjid;
    }
    
//    @Column(name = "status")
    public Byte getStatus()
    {
        return status;
    }
    
    public void setStatus(Byte status)
    {
        this.status = status;
    }
    
    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+" [id="
                + id + ", templateId=" + templateId + ", createdTime=" + createdTime + ", periodDate=" + periodDate
                + ", fromBusitype=" + fromBusitype + ", fromObjId=" + fromObjId + ", status=" + status + "]";
    }

    public BasicDBObject getColumnInfo()
    {
        return columnInfo;
    }

    public void setColumnInfo(BasicDBObject columnInfo)
    {
        this.columnInfo = columnInfo;
    }

    public BasicDBList getColumnList()
    {
        return columnList;
    }

    public void setColumnList(BasicDBList columnList)
    {
        this.columnList = columnList;
    }

}
