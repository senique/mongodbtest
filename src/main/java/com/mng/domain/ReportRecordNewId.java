package com.mng.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;
import com.mng.utils.sequence.GeneratedValue;
import com.mng.utils.sequence.TemporalValue;

public class ReportRecordNewId implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private long id = 0L;

    private Long templeteId;
    
    private Date createdTime;
    
    @TemporalValue
    private Date periodDate;
    
    private Integer fromBusitype;
    
    private Long fromObjId;
    
    private Byte status;
    
    public ReportRecordNewId()
    {
        super();
    }
    
    public ReportRecordNewId(Long templeteId, Date periodDate, Integer fromBusitype, Long fromObjid, Byte status)
    {
        super();
        this.templeteId = templeteId;
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
    
//    @Column(name = "templete_id")
    public Long getTempleteId()
    {
        return templeteId;
    }
    
    public void setTempleteId(Long templeteId)
    {
        this.templeteId = templeteId;
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
        return "ReportRecord [id="
                + id + ", templeteId=" + templeteId + ", createdTime=" + createdTime + ", periodDate=" + periodDate
                + ", fromBusitype=" + fromBusitype + ", fromObjId=" + fromObjId + ", status=" + status + "]";
    }
    
    
    
}
