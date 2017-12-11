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

_备注：_  
`Spring Data MongoDB 对比 Hibernate OGM `  
https://stackoverflow.com/questions/23163853/whats-the-difference-between-spring-data-mongodb-and-hibernate-ogm-for-mongodb  
  