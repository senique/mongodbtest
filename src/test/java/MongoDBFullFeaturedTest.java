import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import org.bson.Document;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.mng.domain.ReportRecord;
import com.mng.domain.ReportRecordNewId;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBFullFeaturedTest 
{
//    @TestSessionFactory
    private SessionFactory sessions;
    
    protected OgmSessionFactory ogmSessionFactory;
    
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
    public static OgmSessionFactory getOgmSessionFactory(Class cls) {
        return new MetadataSources( getServiceRegistry() )
                .addAnnotatedClass( cls )//
            .buildMetadata()
            .getSessionFactoryBuilder()
            .unwrap( OgmSessionFactoryBuilder.class )
            .build();
    }
    
//    @Test
    public void mongoByHibernateJpaTest()
    {
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put( OgmProperties.DATASTORE_PROVIDER, MongoDBDatastoreProvider.class );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial", properties );
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        /** java.lang.IllegalArgumentException: Unknown entity: com.mongodb.BasicDBObject*/
//        BasicDBObject bsObj = new BasicDBObject();
//        BasicDBObject rpt = new BasicDBObject("id", 1).append("templeteId", 1).append("createdTime", now).append("periodDate", now)
//                .append("fromBusitype", 6).append("fromObjId", 181).append("status", 1);
//        bsObj.put("ReportRecord", rpt);
        
        ReportRecord rpt = new ReportRecord();
        rpt.setTempleteId(11L);
        rpt.setCreatedTime(now);
        rpt.setPeriodDate(now);
        rpt.setFromBusitype(6);
        rpt.setFromObjId(181L);
        rpt.setStatus((byte) 1);
        
        //Persist entities the way you are used to in plain JPA
        try {
          /**note that you must start the transaction before creating the EntityManager
             or else call entityManager.joinTransaction()*/
            tm.begin();
            EntityManager em = emf.createEntityManager();
            em.persist(rpt);
            em.flush();
            em.close();
            tm.commit();

            emf.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }    
    
//    @Test
    public void mongoByHibernateOgmTest()
    {
        ogmSessionFactory = getOgmSessionFactory(ReportRecord.class);
        OgmSession osn = ogmSessionFactory.openSession();
        Transaction transaction = osn.getTransaction();
        transaction.begin();
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
//        Date now = DateUtils.getMiPaasNowChinaTime();
        
        /**TODO org.hibernate.MappingException: Unknown entity: com.mongodb.BasicDBObject*/
//      BasicDBObject bsObj = new BasicDBObject();
//      BasicDBObject rpt = new BasicDBObject("id", 1).append("templeteId", 1).append("createdTime", now).append("periodDate", now)
//              .append("fromBusitype", 6).append("fromObjId", 181).append("status", 1);
//      bsObj.put("ReportRecord", rpt);
        
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
    
//    @Test
    public void mongoByMongoTemplateTest()
    {
//        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("10.0.75.1", 27017), "testdb"));
        MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("10.0.75.1", 27017), "testdb"));

        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        
//        /** org.springframework.data.mapping.MappingException: 
//         * Couldn't find PersistentEntity for type class com.mongodb.BasicDBObject!*/
//        BasicDBObject rpt = new BasicDBObject("id", 1)
//                .append("templeteId", 1)
//                .append("createdTime", now)
//                .append("periodDate", now)
//                .append("fromBusitype", 6)
//                .append("fromObjId", 181)
//                .append("status", 1);
       
      //TODO can't auto generate id? and store
      ReportRecordNewId rpt = new ReportRecordNewId();
//      rpt.setName("abc");
      rpt.setTempleteId(1L);
      rpt.setCreatedTime(now);
      rpt.setPeriodDate(now);
      rpt.setFromBusitype(6);
      rpt.setFromObjId(181L);
      rpt.setStatus((byte) 1);
      
//      ReportRecord3 rpt = new ReportRecord3();
////    rpt.setName("abc");
//    rpt.setTempleteId(1L);
//    rpt.setCreatedTime(now);
//    rpt.setPeriodDate(now);
//    rpt.setFromBusitype(6);
//    rpt.setFromObjId(181L);
//    rpt.setStatus((byte) 1);
//        
//      mongoTemplate.insert(rpt);
      mongoTemplate.save(rpt);
    }
    
//    @Test
    public void mongoByMongoClientTest()
    {
        MongoClient mongoClient = new MongoClient("10.0.75.1", 27017);  
        MongoDatabase db = mongoClient.getDatabase("testdb");//获取DB  
        MongoCollection<Document> collection = db.getCollection("ReportRecordByMongoClient");//获取collection（表） 
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        Document rpt = new Document("id", 1)
                .append("templeteId", 1)
                .append("createdTime", now)
                .append("periodDate", now)
                .append("fromBusitype", 6)
                .append("fromObjId", 181)
                .append("status", 1);
        
//        Document document = new Document();
//        collection.insertOne(document );
        
        collection.insertOne(rpt);
        
        mongoClient.close();
    }
    
//    @Test
    @Deprecated
    public void mongoByMongoApiTest()
    {
        Mongo mongo = new Mongo("10.0.75.1", 27017);
        DB db = mongo.getDB("testdb");
        DBCollection collection = db.getCollection("ReportRecordByMongoApi");
        
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)));
        BasicDBObject rpt = new BasicDBObject("id", 1)
                .append("templeteId", 1)
                .append("createdTime", now)
                .append("periodDate", now)
                .append("fromBusitype", 6)
                .append("fromObjId", 181)
                .append("status", 1);
        
        collection.insert(rpt);
        
        mongo.close();
    }
}
