import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.OgmSessionFactory;
import org.hibernate.ogm.boot.OgmSessionFactoryBuilder;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.junit.Test;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import hibernate.ogm.entity.ReportRecord;

public class MongoDBLikeQueryTest 
{
//    @TestSessionFactory
    private static SessionFactory sessions;
    
    protected OgmSessionFactory sessionFactory;
    
    private Session session;
    
    private Transaction transaction;
    
    /** refer to org.hibernate.ogm.test.boot.StandAloneOgmTest*/
    private static StandardServiceRegistry getServiceRegistry() {
//        Map<String, Object> settings = new HashMap<>();
//        settings.put( OgmProperties.DATASTORE_PROVIDER, new MongoDBDatastoreProvider( mockClient().build().getClient() ) );
//        if ( associationStorage != null ) {
//            settings.put( DocumentStoreProperties.ASSOCIATIONS_STORE, AssociationStorageType.ASSOCIATION_DOCUMENT );
//        }
        return  new StandardServiceRegistryBuilder()
        .applySetting( OgmProperties.ENABLED, true )
        //assuming you are using JTA in a non container environment
        .applySetting( AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jta" )
        .applySetting(OgmProperties.DATASTORE_PROVIDER, MongoDBDatastoreProvider.class)
        .build();
    }
//    @Override
    /** refer to org.hibernate.ogm.test.boot.StandAloneOgmTest*/
    public static OgmSessionFactory getSessionFactory(Class cls) {
        return new MetadataSources( getServiceRegistry() )
                .addAnnotatedClass( cls )//
            .buildMetadata()
            .getSessionFactoryBuilder()
            .unwrap( OgmSessionFactoryBuilder.class )
            .build();
    }
    
    @Test
    public void reportRecordTest()
    {
        sessionFactory = getSessionFactory(ReportRecord.class);
        OgmSession osn = sessionFactory.openSession();
        Transaction transaction = osn.getTransaction();
        transaction.begin();
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
//        Date now = DateUtils.getMiPaasNowChinaTime();
        ReportRecord rpt = new ReportRecord();
        rpt.setTempleteId(1L);
        rpt.setCreatedTime(now);
        rpt.setPeriodDate(now);
        rpt.setFromBusitype(6);
        rpt.setFromObjId(181L);
        rpt.setStatus((byte) 1);
        osn.persist(rpt);

        transaction.commit();
        osn.clear();
        osn.close();
    }
    
    @Test
    public void reportRecordTestOther()
    {
        sessionFactory = getSessionFactory(BasicDBObject.class);
        OgmSession osn = sessionFactory.openSession();
        Transaction transaction = osn.getTransaction();
        transaction.begin();
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
//        Date now = DateUtils.getMiPaasNowChinaTime();
        
//        ReportRecord rpt = new ReportRecord();
//        rpt.setTempleteId(1L);
//        rpt.setCreatedTime(now);
//        rpt.setPeriodDate(now);
//        rpt.setFromBusitype(6);
//        rpt.setFromObjId(181L);
//        rpt.setStatus((byte) 1);
//        osn.persist(rpt);
        
//        DBCollection collection =  ;
        BasicDBObject bsObj = new BasicDBObject();
        BasicDBObject rpt = new BasicDBObject("id", 1)
                .append("templeteId", 1)
                .append("createdTime", now)
                .append("periodDate", now)
                .append("fromBusitype", 6)
                .append("fromObjId", 181)
                .append("status", 1);
        bsObj.put("ReportRecord", rpt);
        osn.persist(bsObj);
        /** org.hibernate.MappingException: Unknown entity: com.mongodb.BasicDBObject*/

        transaction.commit();
        osn.clear();
        osn.close();
    }
    
    @Test
    public void reportRecordTestMongoDriver()
    {
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        Mongo mongo = new Mongo("10.0.75.1", 27017);
        DB db = mongo.getDB("testdb");
        DBCollection collection = db.getCollection("ReportRecordByMongoDriver");
        
        BasicDBObject bsObj = new BasicDBObject();
        BasicDBObject rpt = new BasicDBObject("id", 1)
                .append("templeteId", 1)
                .append("createdTime", now)
                .append("periodDate", now)
                .append("fromBusitype", 6)
                .append("fromObjId", 181)
                .append("status", 1);
        bsObj.put("ReportRecord", rpt);
        
        collection.insert(rpt);
        
        mongo.close();
        
    }

}
