package com.mng.domain;

public class ReportRecordResult 
{
    private Long id;
    private Double sumValue;
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Double getSumValue()
    {
        return sumValue;
    }

    public void setSumValue(Double sumValue)
    {
        this.sumValue = sumValue;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+"[id=" + id + ", sumValue=" + sumValue+ "]";
    }
}
