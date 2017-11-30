/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;
import org.apache.commons.lang.time.StopWatch;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;
import org.junit.Test;
import com.mng.domain.Breed;
import com.mng.domain.Dog;
import com.mng.utils.date.DateUtils;
import com.mongodb.BasicDBObject;

public class DogBreedRunner
{

//    private static final Logger logger = LoggerFactory.getLogger(DogBreedRunner.class);
    private final Log logger = LoggerFactory.make();

    @Test
   public void timeTest(){
       logger.debug(LocalDateTime.now());
       logger.debug(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now()));
       logger.debug(Instant.now());
       logger.debug(LocalDate.now());
       logger.debug(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDate.now()));
       
       LocalDateTime now = LocalDateTime.now();
//       String ist = DateTimeFormatter.BASIC_ISO_DATE.format(now);
       String ist = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(now);
       BasicDBObject bsObj = new BasicDBObject();
       ZoneOffset se =ZoneOffset.ofHours(8);
       bsObj.put("create_time", Date.from(now.toInstant(se)));
       bsObj.put("period_date", ist);
       logger.debug(Date.from(now.toInstant(se)));
       logger.debug(bsObj.toJson());
       logger.infof(DateUtils.getMiPaasNowChinaTime().toString());
   }
    
   @Test
   public void simpleTest() throws NamingException
    {
        StopWatch sw = new StopWatch();
        sw.start();
        
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

        
        Map<String, Object> properties = new HashMap<String, Object>();
        // pass the type
        properties.put( OgmProperties.DATASTORE_PROVIDER, MongoDBDatastoreProvider.class );
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial", properties );
        
		//build the EntityManagerFactory as you would build in in Hibernate Core
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "ogm-jpa-tutorial" );
//        EntityManager entityManager = emf.createEntityManager();
//        EntityTransaction entityTransaction = entityManager.createNamedStoredProcedureQuery(name);
		
		
		
		LocalDateTime now = LocalDateTime.now();
//        String ist = DateTimeFormatter.BASIC_ISO_DATE.format(now);
        String ist = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(now);
        BasicDBObject bsObj = new BasicDBObject();
        ZoneOffset se =ZoneOffset.ofHours(8);
        bsObj.put("create_time", Date.from(now.toInstant(se)));
        bsObj.put("period_date", ist);
		
		//Persist entities the way you are used to in plain JPA
		try {
            tm.begin();
            logger.infof("About to store dog and breed");
			EntityManager em = emf.createEntityManager();
			Breed collie = new Breed();
			collie.setName( "Collie" );
			em.persist( collie );
			Dog dina = new Dog();
			dina.setName( "Dina" );
			dina.setBreed( collie );
			dina.setRemark(bsObj.toJson());
			em.persist( dina );
			Long dinaId = dina.getId();
            em.flush();
            em.close();
            tm.commit();

			//Retrieve your entities the way you are used to in plain JPA
            logger.infof("About to retrieve dog and breed");
            tm.begin();
			em = emf.createEntityManager();
			dina = em.find( Dog.class, dinaId );
            if(null != dina)
            {
                logger.infof("Found dog %s of breed %s", dina.getName(), dina.getBreed().getName());
            }
            em.flush();
            em.close();
            tm.commit();
            emf.close();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
        
        sw.stop();
        logger.infof("org.apache.commons.lang.time.StopWatch: " + sw.getTime());

	}

}
