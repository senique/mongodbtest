import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import utils.page.PageResult;
import utils.page.Pager;

public abstract class AbstractBaseRepository
{
    /**
     * @author : balthie@126.com
     * @createtime ： 2014年10月15日 下午3:27:56  
     * @description 主键查询
     * @since version 初始于版本 V0.0.6
     * @param persistentClass
     * @param id
     * @return
     * @throws Exception
     */
    abstract <T> T queryByPrimaryKey(Class<T> persistentClass, Serializable id) throws Exception;
    
    /**
     * @author : balthie@126.com
     * @createtime ： 2014年9月9日 上午11:28:27
     * @description 非主键条件查询
     * @since version 初始于版本 V0.0.5
     * @param condition
     * @return 单个实体
     * @throws Exception
     */
    abstract <T> T uniqueResultByExample(T condition) throws Exception;
    
    /**
     * @author : balthie@126.com
     * @createtime ： 2014年9月9日 上午11:28:27
     * @description 非主键条件列表查询
     * @since version 初始于版本 V0.0.5
     * @param condition
     * @return List 实体列表
     * @throws Exception
     */
    abstract <T> List<T> queryListByExample(T condition) throws Exception;
    
    abstract int countByCriteria(DetachedCriteria criteria);
    
    abstract <T> boolean exists(Class<T> persistentClass, Serializable id);
    
    public <T> T uniqueResultByCriteria(DetachedCriteria criteria)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public <T> List<T> findByCriteria(DetachedCriteria criteria)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    protected <T> T uniqueResultByExample(T condition, Serializable primaryKeyValue) throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    protected <T> T queryMaxPropertyRecord(Class<T> t, String propertyName)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public <T> void saveOrUpdate(T domainEntity)
    {
        // TODO Auto-generated method stub
        
    }
    
    public <T> T save(T domainEntity)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public <T> void update(T domainEntity)
    {
        // TODO Auto-generated method stub
        
    }
    
    protected <T> void delete(T domainEntity)
    {
        // TODO Auto-generated method stub
    }
    
    abstract <T> PageResult<T> criteriaPageSearch(Class<T> clazz, List<Criterion> criterions, String orderStr, Pager p);
}
