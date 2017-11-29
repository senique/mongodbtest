package utils.page;

import java.util.List;

public class PageResult<T>
{
    
    private long nowPage;
    
    private long pageSize;
    
    private long totalCount;
    
    private long totalPageNum;
    
    private List<T> list;
    
    public PageResult(PageResult<?> tr)
    {
        super();
        this.nowPage = tr.nowPage;
        this.pageSize = tr.pageSize;
        this.totalCount = tr.totalCount;
        this.totalPageNum = tr.totalPageNum;
    }
    
    public PageResult(long nowPage, long pageSize, long totalCount,
            long totalPageNum, List<T> list)
    {
        super();
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPageNum = totalPageNum;
        this.list = list;
    }
    
    public PageResult()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public long getNowPage()
    {
        return nowPage;
    }
    
    public void setNowPage(long nowPage)
    {
        this.nowPage = nowPage;
    }
    
    public long getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(long pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public long getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public long getTotalPageNum()
    {
        return totalPageNum;
    }
    
    public void setTotalPageNum(long totalPageNum)
    {
        this.totalPageNum = totalPageNum;
    }
    
    public List<T> getList()
    {
        return list;
    }
    
    public void setList(List<T> list)
    {
        this.list = list;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PageResult [nowPage=").append(nowPage).append(", pageSize=").append(pageSize).append(", totalCount=")
                .append(totalCount).append(", totalPageNum=").append(totalPageNum).append("]");
        return builder.toString();
    }
}
