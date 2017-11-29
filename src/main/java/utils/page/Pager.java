package utils.page;

public class Pager
{
    private int rowsPerPage;
    
    private int currentPage;
    
    private int totalPage;
    
    private int totalRow;
    
    private int nextPage;
    
    private int previousPage;
    
    private int startRow;
    
    private int endRow;
    
    private int remainder;
    
    private boolean existPrevious;
    
    private boolean existNext;
    
    public Pager()
    {
        rowsPerPage = 10;
        totalRow = 100;
        currentPage = 1;
    }
    
    public Pager(int currentPage, int rowsPerPage)
    {
        super();
        this.rowsPerPage = rowsPerPage;
        this.currentPage = currentPage;
        this.totalRow = 10000;
    }
    
    public Pager(int rowsPerPage, int currentPage, int totalRow)
    {
        super();
        this.rowsPerPage = rowsPerPage;
        this.currentPage = currentPage;
        this.totalRow = totalRow;
    }
    
    public int getRowsPerPage()
    {
        return rowsPerPage == 0 ? 5 : rowsPerPage;
    }
    
    public void setRowsPerPage(Integer rowsPerPage)
    {
        if(rowsPerPage != null) this.rowsPerPage = rowsPerPage;
    }
    
    public int getCurrentPage()
    {
        if(currentPage > getTotalPage())
            return getTotalPage();
        else if(currentPage < 1) return 1;
        return currentPage;
    }
    
    public int getEndRow()
    {
        endRow = getCurrentPage() >= getTotalPage() ? getTotalRow() : getCurrentPage() * rowsPerPage;
        return endRow;
    }
    
    public int getNextPage()
    {
        nextPage = getCurrentPage() >= getTotalPage() ? getTotalPage() : getCurrentPage() + 1;
        return nextPage;
    }
    
    public int getPreviousPage()
    {
        previousPage = getCurrentPage() <= 1 ? 1 : getCurrentPage() - 1;
        return previousPage;
    }
    
    public int getStartRow()
    {
        startRow = (getCurrentPage() - 1) * getRowsPerPage() + 1;
        return startRow;
    }
    
    public int getTotalPage()
    {
        if(getRemainder() == 0)
            totalPage = getTotalRow() / getRowsPerPage();
        else totalPage = getTotalRow() / getRowsPerPage() + 1;
        return totalPage;
    }
    
    public int getTotalRow()
    {
        return totalRow;
    }
    
    public int getRemainder()
    {
        remainder = getTotalRow() % getRowsPerPage();
        return remainder;
    }
    
    public boolean isExistNext()
    {
        existNext = getCurrentPage() < getTotalPage();
        return existNext;
    }
    
    public boolean isExistPrevious()
    {
        existPrevious = getCurrentPage() > 1;
        return existPrevious;
    }
    
    public void setCurrentPage(Integer currentPage)
    {
        if(currentPage != null) this.currentPage = currentPage;
    }
    
    public void setTotalRow(Integer totalRow)
    {
        if(totalRow != null) this.totalRow = totalRow;
    }
}
