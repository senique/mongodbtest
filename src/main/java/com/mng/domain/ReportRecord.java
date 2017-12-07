package com.mng.domain;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
import org.springframework.data.annotation.Id;

//@Entity
//@Table(name = "report_record", catalog = "vm_report")
public class ReportRecord implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private Long templateId;
    
    private Date createdTime;
    
    private Date periodDate;
    
    private Integer fromBusitype;
    
    private Long fromObjId;
    
    private Byte status;
    
    public ReportRecord()
    {
        super();
    }
    
    public ReportRecord(Long templateId, Date periodDate, Integer fromBusitype, Long fromObjid, Byte status)
    {
        super();
        this.templateId = templateId;
        this.periodDate = periodDate;
        this.fromBusitype = fromBusitype;
        this.fromObjId = fromObjid;
        this.status = status;
    }
    
//    @GeneratedValue(strategy = IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//  @Id
//  @GeneratedValue(strategy = GenerationType.TABLE, generator = "dog")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ReportRecord")
    @TableGenerator(
          name = "ReportRecord",
          table = "sequences",
          pkColumnName = "key",
          pkColumnValue = "ReportRecord",
          valueColumnName = "seed")
    public Long getId()
    {
        return id;  
    }
    
    public void setId(Long id)
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
        return "ReportRecord [id="
                + id + ", templateId=" + templateId + ", createdTime=" + createdTime + ", periodDate=" + periodDate
                + ", fromBusitype=" + fromBusitype + ", fromObjId=" + fromObjId + ", status=" + status + "]";
    }
    
    
    
}
