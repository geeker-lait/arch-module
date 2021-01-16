#!/usr/bin/env bash

#参考:  https://hub.docker.com/_/mysql
#输入参数:
#  mysqlid: 数据库ID用于命名container并在dbid为空时作为默认数据库名,如果为空使用时间戳
#  myport:  mysql宿主机端口,默认3306
#  dbid:    默认数据库名称,如果为空则使用mysqlid
# mode:     主服务器或从服务器(master or slave)

curtimestamp=`date +'%Y%m%d%H%M%S'`
myportprefix="330"


if [ x$1 = x ];then
    mode=master
else
    mode=$1
fi

if [ x$2 = x ];then
    mysqlid=${curtimestamp}
else
    mysqlid=$2
fi

if [ x$3 = x ];then
    myport=`echo "${myportprefix}${curtimestamp:12:2}"`
else
    myport=$3
fi

if [ x$4 = x ];then
    dbid=${mysqlid}
else
    dbid=$4
fi


MYSQL_USER="unichain"
MYSQL_PASSWORD="unichain*${mysqlid}"
MYSQL_ROOT_PASSWORD="zfc1cfkyaghhvzrlcg"

myconfpath=/deploy/conf/mysql_${mode}_${mysqlid}
mydatapath=/deploy/data/mysql_${mode}_${mysqlid}
mkdir -p ${myconfpath}
chmod -R 777 ${myconfpath}

mkdir -p ${mydatapath}
chmod -R 777 ${mydatapath}

cp /deploy/conf/mysql/my_${mode}_default.cnf ${myconfpath}/my.cnf

chmod 644 ${myconfpath}/my.cnf
# echo "unichain" | base64 |  tr '[A-Z]' '[a-z]'
# echo "unichain" | base64 | base64 |  tr '[A-Z]' '[a-z]'

mycontainername=mysql_${mode}_${mysqlid}

docker stop ${mycontainername}
docker rm ${mycontainername}

docker run -d -p ${myport}:3306  \
-e MYSQL_USER=${MYSQL_USER}  \
-e MYSQL_PASSWORD=${MYSQL_PASSWORD}      \
-e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}  \
--name ${mycontainername}  \
-v ${myconfpath}:/etc/mysql/conf.d  \
-v ${mydatapath}:/var/lib/mysql  \
--restart always  \
--privileged=true  \
mysql:5.7.26

containerid=`docker ps | grep ${mycontainername} | awk -F" " '{print $1}'`
if [ x${containerid} = x ];then
    docker rm ${mycontainername}
    rm -rf ${mydatapath}
    rm -rf ${myconfpath}
    echo `date +'%Y-%m-%d %H:%M:%S'`"-- ${curtimestamp}"" -- INFO -- ""-- Docker 容器启动失败:${mycontainername},端口:${myport},数据库名:${dbid}"  >> /deploy/logs/docker_mysql.log
else
    echo `date +'%Y-%m-%d %H:%M:%S'`"-- ${curtimestamp}"" -- INFO -- ""-- Docker 容器已启动:${mycontainername}:${containerid},端口:${myport},数据库名:${dbid},访问权限:${MYSQL_USER}   ${MYSQL_PASSWORD},配置路径:${myconfpath},数据目录:${mydatapath}"  >> /deploy/logs/docker_mysql.log
fi