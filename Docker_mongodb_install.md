# Install mongodb on localhost with Docker
**Install Docker:** https://www.docker.com/community-edition#/download

**Install mongodb** `mongodb V3.4, use localhost or local ip and 27017 access`  
Docker for Windows:  
`docker run -it -p 27017:27017 -v F:\docker\mongo\data:/data --name mongodb34 -d mongo:3.4 `   
Docker for Linux:  
`docker run -it -p 27017:27017 -v /root/data/mongodb/data:/data --name mongodb3.4 -d mongo:3.4`


**IDE：Robo 3T** https://robomongo.org/ 

**Config mongodb** `create db and user with password`  
`use testdb`   
`db.createUser(  
   {  
     user: "viomi",  
     pwd: "123456",  
     roles: [ "readWrite", "dbAdmin" ]  
   }  
)`   
`db.auth('viomi','123456')`   

**Mongodb Document**   
https://www.w3cschool.cn/mongodb/mongodb-databases-documents-collections.html   
https://docs.mongodb.com/

**Mongodb Replication**  
https://docs.mongodb.com/manual/replication/  
_Create 1 shard1 with 3 Replica Sets_  
_创建3个副本集_  
`docker run -it -p 27018:27017 -v F:\docker\mongo1\data:/data --name mongodbbak1 -d mongo:3.4 mongod --replSet shard1`  
`docker run -it -p 27019:27017 -v F:\docker\mongo2\data:/data --name mongodbbak2 -d mongo:3.4 mongod --replSet shard1`  
`docker run -it -p 27016:27017 -v F:\docker\mongo3\data:/data --name mongodbbak3 -d mongo:3.4 mongod --replSet shard1`

_'192.168.1.69'替换成docker宿主机的ip或者域名_  
_[1].mongodbbak1 配置mongodbbak1为PRIMARY,mongodbbak2为SECONDARY_  
`cfg={_id:'shard1',members:[ 
	{_id:0,host:'192.168.1.69:27018'}, 
	{_id:1,host:'192.168.1.69:27019'}] 
}`   
`rs.initiate(cfg)`  
`use testdb`   
`db.createUser(  
   {  
     user: "viomi",  
     pwd: "123456",  
     roles: [ "readWrite", "dbAdmin" ]  
   }  
)`   
`db.auth('viomi','123456')`   
_[2].mongodbbak2,添加一个副本集(Replica Set,异步复制数据)_  
`rs.slaveOk()`
_[3].mongodbbak3,添加为仲裁者(ARBITER,不存储数据),用于故障时,切换PRIMARY_  
`rs.addArb('192.168.1.69:27016')`   
`rs.reconfig(rs.conf()) `  
`rs.slaveOk()`  
_[4].连接数据库配置: 配置PRIMARY和SECONDARY_  
`spring.data.mongodb.uri: mongodb://127.0.0.1:27018,127.0.0.1:27019/testdb`  
_[注]._  
`rs.status()  查看状态myState: 1-PRIMARY,2-SECONDARY,7-ARBITER; health: 0-异常,1-正常`  
`db.printReplicationInfo()  查看第一次和最近一次同步事件的时间`  


_备注：_  
**Docker setting：** Shared Drives on both **C and F**, if you use `-v F:\docker\mongo\data:/data`  
**mongoTemplate.aggregate only work for MongoDB 3.4 now, error for:** Changed in version 3.6: MongoDB 3.6 removes the use of aggregate command without the cursor option unless the command includes the explain option.
