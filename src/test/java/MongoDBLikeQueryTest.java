import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.OgmSessionFactory;
import org.hibernate.ogm.boot.OgmSessionFactoryBuilder;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.junit.Test;
import hibernate.ogm.entity.Breed;
import hibernate.ogm.entity.Dog;

public class MongoDBLikeQueryTest 
{
//    @TestSessionFactory
    private static SessionFactory sessions;
    
    protected OgmSessionFactory sessionFactory;
    
    private Session session;
    
    private Transaction transaction;
    
    private static StandardServiceRegistry getServiceRegistry() {
//        Map<String, Object> settings = new HashMap<>();
//        settings.put( OgmProperties.DATASTORE_PROVIDER, new MongoDBDatastoreProvider( mockClient().build().getClient() ) );
//        if ( associationStorage != null ) {
//            settings.put( DocumentStoreProperties.ASSOCIATIONS_STORE, AssociationStorageType.ASSOCIATION_DOCUMENT );
//        }
        return new StandardServiceRegistryBuilder()
//                .applySetting( OgmProperties.ENABLED, true )
//              //assuming you are using JTA in a non container environment
//              .applySetting( AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jta" )
//              //assuming JBoss TransactionManager in standalone mode
//              .applySetting( AvailableSettings.JTA_PLATFORM, "JBossTS" )
              //assuming Infinispan as the backend, using the default settings
              .applySetting(OgmProperties.DATASTORE_PROVIDER, new MongoDBDatastoreProvider( MockMongoClientBuilder.mockClient().build().getClient() ))
              .build();
    }
    
    
//    @Override
    public static OgmSessionFactory getSessionFactory() {
        return new MetadataSources( getServiceRegistry() )
//                .addAnnotatedClass( Order.class )
//              .addAnnotatedClass( Item.class )
                .addAnnotatedClass( Dog.class )//
              .buildMetadata()
              .getSessionFactoryBuilder()
              .unwrap( OgmSessionFactoryBuilder.class )
              .build();
    }
    
    
    @Test
    public void test()
    {
        
        sessionFactory = getSessionFactory();
        OgmSession osn = sessionFactory.openSession();
        Transaction transaction = osn.getTransaction();
        transaction.begin();
        
        Breed collie = new Breed();
        collie.setName("Collie osn");
        osn.persist(collie);
        Dog dina = new Dog();
        dina.setName("Dina osn");
        dina.setBreed(collie);
        osn.persist(dina);
        osn.clear();
        osn.close();
        
    }
    
    public void addTestEntities()
    {
        Session session = sessions.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        
        Hypothesis hypothesis = new Hypothesis();
        hypothesis.setId("1");
        hypothesis.setPosition(1);
        hypothesis.setDescription("Alea iacta est.");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("2");
        hypothesis.setPosition(2);
        hypothesis.setDescription("Ne vadis...");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("3");
        hypothesis.setPosition(3);
        hypothesis.setDescription("Omne initium difficile est.");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("4");
        hypothesis.setPosition(4);
        hypothesis.setDescription("Nomen est omen.");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("5");
        hypothesis.setPosition(5);
        hypothesis.setDescription("Quo vadis?");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("6");
        hypothesis.setPosition(6);
        hypothesis.setDescription("Ne vadis.");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("7");
        hypothesis.setPosition(7);
        hypothesis.setDescription("100% scientia");
        session.persist(hypothesis);
        
        hypothesis = new Hypothesis();
        hypothesis.setId("8");
        hypothesis.setPosition(8);
        hypothesis.setDescription("100\nscientiae");
        session.persist(hypothesis);
        
        transaction.commit();
        session.clear();
        session.close();
    }
    
    public void deleteTestEntities() throws Exception
    {
        Session session = sessions.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        
        session.delete(new Hypothesis("1"));
        session.delete(new Hypothesis("2"));
        session.delete(new Hypothesis("3"));
        session.delete(new Hypothesis("4"));
        session.delete(new Hypothesis("5"));
        session.delete(new Hypothesis("6"));
        session.delete(new Hypothesis("7"));
        session.delete(new Hypothesis("8"));
        
        transaction.commit();
        session.clear();
        session.close();
    }

}
