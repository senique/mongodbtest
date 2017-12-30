# Install mongodb on localhost with Docker
**Install Docker:** https://www.docker.com/community-edition#/download

**Install mongodb** `mongodb V3.4, use localhost or local ip and 27017 access`  
Docker for Windows:  
`docker run -it -p 27017:27017 -v F:\docker\mongo\data:/data --name mongodb34 -d mongo:3.4 `   
Docker for Linux:  
`docker run -it -p 27017:27017 -v /root/data/mongodb/data:/data --name mongodb3.4 -d mongo:3.4`


**IDE：Robo 3T** https://robomongo.org/ 

**Config mongodb** `create db and user with password`  
use testdb;  
db.createUser(  
   {  
     user: "viomi",  
     pwd: "123456",  
     roles: [ "readWrite", "dbAdmin" ]  
   }  
);  
db.auth('viomi','123456');  

**Mongodb Document**   
https://www.w3cschool.cn/mongodb/mongodb-databases-documents-collections.html   
https://docs.mongodb.com/


_备注：_  
**Docker setting：** Shared Drives on both **C and F**, if you use `-v F:\docker\mongo\data:/data`  
**mongoTemplate.aggregate only work for MongoDB 3.4 now, error for:** Changed in version 3.6: MongoDB 3.6 removes the use of aggregate command without the cursor option unless the command includes the explain option.