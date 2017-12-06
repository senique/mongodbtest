package com.mng.domain;

public class ReportRecordResult implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    
    private Long templeteId;
    private Long turnOver;
    private Integer orderCount;
    private Long profit;
    
    public Long getTempleteId()
    {
        return templeteId;
    }
    public void setTempleteId(Long templeteId)
    {
        this.templeteId = templeteId;
    }
    public Long getTurnOver()
    {
        return turnOver;
    }
    public void setTurnOver(Long turnOver)
    {
        this.turnOver = turnOver;
    }
    public Integer getOrderCount()
    {
        return orderCount;
    }
    public void setOrderCount(Integer orderCount)
    {
        this.orderCount = orderCount;
    }
    public Long getProfit()
    {
        return profit;
    }
    public void setProfit(Long profit)
    {
        this.profit = profit;
    }
    
    @Override
    public String toString()
    {
        return "ReportRecord [templeteId=" + templeteId + ", turnOver=" + turnOver + ", orderCount=" + orderCount
                + ", profit=" + profit + "]";
    }
}
