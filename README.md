# mongodbtest
**mongoDB Java连接实现:**
> [**其他语言实现（Drivers/Api等）**](https://api.mongodb.com)   
  [**MongoDB单节点及副本集安装**](https://github.com/senique/mongodbtest/blob/master/Docker_mongodb_install.md)  

[**1.Mongo类(mongodb-java-driver original)**](https://github.com/mongodb/mongo-java-driver)  
@Deprecated  
Mongo mongo = new Mongo("IP", 27017);  

[**2.MongoClient类（mongodb-java-driver since 2.10.0）**](https://github.com/mongodb/mongo-java-driver)  
MongoClient mongoClient = new MongoClient("IP", 27017);  

[**3.MongoTemplate类（Spring Data的 MongoDB项目）**](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/MongoTemplate.html)  
MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient("IP", 27017), "testdb"));

[**4.Hibernate OGM（Hibernate的一个项目）**](http://hibernate.org/ogm/)  
For JPA: use EntityManager  
Without JPA: use OgmSession  

[**5.Morphia (Apache许可/Google Code项目，提供一系列围绕 Mongo Java驱动程序的注解和包装程序)**](https://github.com/MorphiaOrg/morphia)    
@Entity + @Id  
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
[`Spring Data MongoDB 对比 MongoDB Java Driver, Jongo, Morphia `](https://github.com/senique/mongodbtest/blob/master/A_Comparison_of_MongoDB_Drivers.md)  
