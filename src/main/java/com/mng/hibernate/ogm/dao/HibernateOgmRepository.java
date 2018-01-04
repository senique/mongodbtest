package com.mng.hibernate.ogm.dao;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.OgmSessionFactory;
import org.hibernate.ogm.boot.OgmSessionFactoryBuilder;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateOgmRepository extends AbstractBaseRepository
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private OgmSession ogmSession;
    
    protected OgmSessionFactory ogmSessionFactory;
    
    private SessionFactory sessionFactory;
    
    public void setDataSource(DataSource dataSource)
    {
    
    }
    
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    
    
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
    
    protected OgmSession getOgmSession()
    {
        if(this.ogmSession == null) {
            this.ogmSession = getOgmSessionFactory(null).openSession();//null to class TODO
        }
        return this.ogmSession;
    }
    
    protected SessionFactory getSessionFactory()
    {
        return this.sessionFactory;
    }
    
 
}
