#!/usr/bin/env bash

# 基础应用
docker pull sonatype/nexus3:latest
docker pull gitlab/gitlab-ce:12.0.3-ce.0
docker pull idoop/zentao:11.5.1
docker pull jenkins/jenkins:2.191-centos
docker pull fauria/vsftpd:latest
docker pull haxqer/jira:8.3.1

docker pull yangqiang/ngrok-server:2.0.2
# 应用
docker pull branchzero/yapi:1.7.0
docker pull centos:7
docker pull centos:latest
docker pull elasticsearch:6.8.1
docker pull containerize/elastichd:latest
docker pull elastichq/elasticsearch-hq:latest
docker pull mongo:3.5
docker pull mysql:5.7.26
docker pull nginx:latest
docker pull postgres:10
docker pull rabbitmq:3-management
docker pull rabbitmq:latest
docker pull redis:latest
docker pull kibana:6.8.2

# nexus3#sonatype/nexus3:latest
mkdir -p /deploy/data/nexus/nexus-data && chown -R 200 /deploy/data/nexus/nexus-data
docker run -d  -e TZ="Asia/Shanghai"  -p 0.0.0.0:8081:8081 --name nexus318 -v /deploy/data/nexus/nexus-data:/nexus-data sonatype/nexus3
# gitlab/gitlab-ce:12.0.3-ce.0
docker run --detach \
  --hostname gitlab.unichain.tech \
  --publish 444:443 --publish 80:80 --publish 23:22 \
  --name gitlab \
  --restart always \
  --volume /deploy/config/gitlab:/etc/gitlab \
  --volume /deploy/logs/gitlab:/var/log/gitlab \
  --volume /deploy/data/gitlab:/var/opt/gitlab \
  gitlab/gitlab-ce:12.0.3-ce.0
# idoop/zentao:11.5.1
docker run -d -p 8071:80 -p 33061:3306 \
        -e TZ="Asia/Shanghai" \
        -e BIND_ADDRESS="false" \
        --restart=always \
        -v /deploy/data/zentao/:/opt/zbox/ \
        --name zentao \
        idoop/zentao:11.5.1
# jenkins/jenkins:2.191-centos
docker run -d  -e TZ="Asia/Shanghai"  -p 9083:8080 -p 40000:50000 --name jenkins2 --restart=always -v /deploy/data/jenkins:/var/jenkins_home jenkins/jenkins:2.191-centos
# fauria/vsftpd:latest
docker run -d -v /deploy/data/vsftpd:/home/vsftpd \
-e TZ="Asia/Shanghai" \
-e FTP_USER=unichain -e FTP_PASS=unichain \
-p 20:20 -p 21:21 -p 21100-21110:21100-21110 \
-e PASV_ADDRESS=127.0.0.1 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 \
--name vsftp --restart=always fauria/vsftpd

#########################################
# branchzero/yapi:1.7.0
cd /deploy/packages/yapi-docker  && docker-compose up -d
# elasticsearch
docker network create network-es-v6
docker run -d  -e TZ="Asia/Shanghai"  --name es6 --net network-es-v6 -p 9200:9200 -p 9300:9300  --restart=always -e "discovery.type=single-node" elasticsearch:6.8.1
# kibana
docker run -d  -e TZ="Asia/Shanghai"  --name kibana6 --net network-es-v6  -v /deploy/conf/kibana/kibana.yml:/usr/share/kibana/config/kibana.yml -p 5601:5601 kibana:6.8.2
# containerize/elastichd
docker run  -d  -e TZ="Asia/Shanghai"  -p 9889:9800 --restart=always --name eshd containerize/elastichd
# elastichq/elasticsearch-hq
docker run -d -p 5000:5000 --name eshq --restart=always  elastichq/elasticsearch-hq
# mysql:5.7.26
docker run -d -p 3306:3306  \
-e MYSQL_USER=unichain  \
-e MYSQL_PASSWORD=unichain*0731      \
-e MYSQL_ROOT_PASSWORD=zfc1cfkyaghhvzrlcg  \
-e TZ="Asia/Shanghai"  \
--name mysql  \
-v /deploy/conf/mysql/lab:/etc/mysql/conf.d  \
-v /deploy/data/mysql_mysql_lab:/var/lib/mysql  \
--restart always  \
--privileged=true  \
mysql:5.7.26

SET PASSWORD FOR 'unichain'@'%' = PASSWORD('unichain*01011');

GRANT ALL PRIVILEGES ON *.* TO 'unichain'@'%' IDENTIFIED BY 'unichain*01011' WITH GRANT OPTION;
FLUSH PRIVILEGES;

# nginx
docker run  -d -e TZ="Asia/Shanghai" -v /deploy/data/nginx:/data -v /deploy/conf/nginx/nginx_lab.conf:/etc/nginx/nginx.conf:ro -p 9080:80  --restart=always --name nginx nginx
# rabbitmq:3-management
docker network create rabbitmq-network
docker run -d -e TZ="Asia/Shanghai" --hostname rabbitmg.unichain.tech  --restart=always  --name rabbitmg -p 5672:5672 -p 25672:25672 -p 5671:5671 -p 4369:4369 -p 15672:15672 --network rabbitmq-network rabbitmq:3-management

# redis
docker network create redis-network
docker run -d -e TZ="Asia/Shanghai" --name redis -p 6379:6379  --restart=always  -v /deploy/data/redis/redis.log:/var/log/redis/redis.log -v /deploy/data/redis/data:/data -v /deploy/conf/redis/redis.conf:/usr/local/etc/redis/redis.conf -d redis redis-server /usr/local/etc/redis/redis.conf --appendonly yes
# jira
docker run -d -e TZ="Asia/Shanghai" -p 9889:9800-p 0.0.0.0:9077:8080 -p 0.0.0.0:9078:9088 --restart=always  -v /deploy/data/jira84-2/:/data --add-host lab.unichain.tech:192.168.1.199 --hostname jira84-2 --name jira unichain_centos:0909

JAVA_OPTS="-javaagent:/data/atlassian-agent-v1.2.2/atlassian-agent.jar" /data/atlassian/jira/bin/start-jira.sh

docker run -d -p 7306:3306  \
-e TZ="Asia/Shanghai" \
-e MYSQL_USER=jira  \
-e MYSQL_PASSWORD=jira      \
-e MYSQL_ROOT_PASSWORD=root  \
--name jiramysql  \
-v /deploy/conf/mysql/jira:/etc/mysql/conf.d  \
-v /deploy/data/jira84-2/mysql:/var/lib/mysql  \
--restart always  \
--privileged=true  \
mysql:5.7.26

CREATE DATABASE jira CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
GRANT ALL PRIVILEGES ON *.* TO 'jira'@'%' IDENTIFIED BY 'jira' WITH GRANT OPTION;
FLUSH PRIVILEGES;

数据库配置: dbconfig.xml
启动命令:   JAVA_OPTS="-javaagent:/data/atlassian-agent-v1.2.2/atlassian-agent.jar" /data/atlassian/jira/bin/start-jira.sh

# confluence
docker rm -f jira84
docker run -d -e TZ="Asia/Shanghai" -p 0.0.0.0:9097:8080 -p 0.0.0.0:9098:9080 --restart=always  -v /deploy/conf/confluence/:/opt/atlassian -v /deploy/data/confluence/:/var/atlassian --add-host lab.unichain.tech:192.168.1.199 --hostname confluence --name conf unichain_centos:0909

docker run -d -p 6306:3306  \
-e MYSQL_USER=jira  \
-e MYSQL_PASSWORD=jira      \
-e MYSQL_ROOT_PASSWORD=root  \
--name confmysql  \
-v /deploy/conf/mysql/jira:/etc/mysql/conf.d  \
-v /deploy/data/jira84/mysql:/var/lib/mysql  \
--restart always  \
--privileged=true  \
mysql:5.7.26

JAVA_OPTS="-javaagent:/var/atlassian/atlassian-agent.jar" /opt/atlassian/confluence/bin/start-confluence.sh
# mongo
docker network create mongo-network
docker run -d -e TZ="Asia/Shanghai" --hostname mongo.unichain.tech --name mongo --network mongo-network  -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mgadmin -e MONGO_INITDB_ROOT_PASSWORD=Mongo123 -v /deploy/data/mongo:/etc/mongo -d mongo:4.0.12

# jenkins node
docker rm -f jenkins_node_prod_10
rm -rf /deploy/data/slave/jenkins_node_prod_10

docker run -d --name jenkins_node_prod_10  \
-e TZ="Asia/Shanghai" \
--hostname jenkins_node_prod_10 \
--add-host lab.unichain.tech:192.168.1.199  \
--add-host gitlab.unichain.tech:192.168.1.199  \
--add-host nexus.unichain.tech:192.168.1.199  \
--add-host s01.tastelifer.com:39.100.152.67  \
--add-host s12.tastelifer.com:106.12.68.246  \
--add-host s22.tastelifer.com:106.12.68.237  \
--add-host s11.tastelifer.com:106.12.71.2  \
--add-host s21.tastelifer.com:106.12.71.3  \
--privileged --restart always -p 2310:22 -v /deploy/data/slave/jenkins_node_prod_10:/data -v /deploy:/deploy -v /etc/maven:/etc/maven -v /var/m2:/root/.m2 unichain_centos:0909

docker rm -f jenkins_node_prod_01
rm -rf /deploy/data/slave/jenkins_node_prod_01

docker run -d --name jenkins_node_prod_01  \
-e TZ="Asia/Shanghai" \
--hostname jenkins_node_prod_01 \
--add-host lab.unichain.tech:192.168.1.199  \
--add-host gitlab.unichain.tech:192.168.1.199  \
--add-host nexus.unichain.tech:192.168.1.199  \
--add-host s01.tastelifer.com:39.100.152.67  \
--add-host s12.tastelifer.com:106.12.68.246  \
--add-host s22.tastelifer.com:106.12.68.237  \
--add-host s11.tastelifer.com:106.12.71.2  \
--add-host s21.tastelifer.com:106.12.71.3  \
--add-host s31.tastelifer.com:106.13.172.44  \
--add-host s32.tastelifer.com:106.13.172.62  \
--add-host s33.tastelifer.com:106.13.170.140  \
--privileged --restart always -p 2301:22 -v /deploy/data/slave/jenkins_node_prod_01:/data -v /deploy:/deploy -v /etc/maven:/etc/maven -v /var/m2old:/root/.m2 jenkins_node:1214

# superset
docker run  -d -e TZ="Asia/Shanghai" -p 9088:8088  --restart=always --name superset_tmp amancevice/superset

