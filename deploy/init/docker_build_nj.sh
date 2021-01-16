#!/usr/bin/env bash
#-- mysql
#配置文件目录: /deploy/conf/mysql/prod/master

docker run -d -p 3306:3306  \
-e MYSQL_USER=unichain  \
-e MYSQL_PASSWORD=unichain*01011     \
-e MYSQL_ROOT_PASSWORD=zfc1cfkyaghhvzrlcg  \
-e TZ="Asia/Shanghai"  \
--name mysql  \
-v /deploy/conf/mysql/prod/master:/etc/mysql/conf.d  \
-v /deploy/data/prod/master:/var/lib/mysql  \
--restart always  \
--privileged=true  \
mysql:5.7.26

SET PASSWORD FOR 'unichain'@'%' = PASSWORD('unichain*01011');

GRANT ALL PRIVILEGES ON *.* TO 'unichain'@'%' IDENTIFIED BY 'unichain*01011' WITH GRANT OPTION;
FLUSH PRIVILEGES;

#-- nginx
#配置文件: /deploy/conf/nginx/nginx.conf
docker run  -d -e TZ="Asia/Shanghai" -v /deploy/data/nginx:/data -v /deploy/conf/nginx/nginx.conf:/etc/nginx/nginx.conf:ro -p 80:80  --restart=always --name nginx nginx

#-- rabbitmq
docker rm -f rabbitmq
mkdir -p /deploy/data/rabbitmq
docker network create rabbitmq-network
docker run -d -e TZ="Asia/Shanghai" --hostname rabbitmq01.unichain.tech -p 15672:5672 -p 25672:25672 -p 5671:5671 -p 4369:4369 --network rabbitmq-network  --restart=always -v /deploy/data/rabbitmq:/var/lib/rabbitmq --name rabbitmq rabbitmq:3

#--redis
docker rm -f redis
docker network create redis-network
docker run -d -e TZ="Asia/Shanghai" --hostname redis01.unichain.tech --name redis -p 6379:6379 --network redis-network  --restart=always  -v /deploy/data/redis/:/data -d redis redis-server --appendonly yes

#-- mongo
docker rm -f mongo
mkdir -p /deploy/data/mongo
docker network create mongo-network
docker run -d -e TZ="Asia/Shanghai" --hostname mongo01.unichain.tech --name mongo --network mongo-network  -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mgadmin -e MONGO_INITDB_ROOT_PASSWORD=mongo*admin -v /deploy/data/mongo:/etc/mongo -d mongo:4.0.12