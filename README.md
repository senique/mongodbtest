# mongodbtest
**mongoDB Java连接实现:**

**1.Mongo类(mongodb-driver original)** http://api.mongodb.com/java/current/com/mongodb/Mongo.html#Mongo  
@Deprecated
Mongo mongo = new Mongo("IP", 27017);

**2.MongoClient类（mongodb-driver since 2.10.0）** http://api.mongodb.com/java/current/com/mongodb/MongoClient.html  
MongoClient mongoClient = new MongoClient("IP", 27017);  

**3.MongoTemplate类（Spring Data的 MongoDB项目）** https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html  
MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("IP", 27017), "testdb"));

**4.Hibernate OGM（Hibernate的一个项目）** http://hibernate.org/ogm/  
For JPA: use EntityManager
Without JPA: use OgmSession

**5.Morphia (Apache许可/Google Code项目,提供一系列围绕 Mongo Java驱动程序的注解和包装程序)** http://mongodb.github.io/morphia/   
@Entity+@Id  
@Embedded, @Reference, (@Property), ...  
Morphia morphia = new Morphia();  
morphia.mapPackage("mongodbDemo");  
final Datastore datastore = morphia.createDatastore(new MongoClient("localhost"), "morphia_example");  
datastore.save(entity);  

_备注：_  
`Spring Data MongoDB 对比 Hibernate OGM `  
https://stackoverflow.com/questions/23163853/whats-the-difference-between-spring-data-mongodb-and-hibernate-ogm-for-mongodb   
`Spring Data MongoDB 对比  morphia `  
https://stackoverflow.com/questions/37584915/morphia-vs-spring-data-mongo  
`Spring Data MongoDB 对比 morphia、mongo4j `  
https://stackoverflow.com/questions/5837135/how-do-morphia-mongo4j-and-spring-data-for-mongodb-compare  
`Spring Data MongoDB 对比 MongoDB Java Driver, Jongo, Morphia `  
http://note.youdao.com/noteshare?id=9dfe8683537c926dc3cd6f6cfc825710&sub=240B84F1DC2A413ABED1E36A9D191C38  