#!/usr/bin/env bash

# 全局参数
number=31  #保存备份个数，备份31天数据
logdir=/deploy/logs
curtime=`date +%Y%m%d%H%M%S`  #日期
# 数据库参数
mysqlid=$1
username=$2
password=$3
host=$4
port=$5

#如果文件夹不存在则创建
backup_dir=/deploy/data/backups/mysql/${mysqlid}  # 路径
if [ ! -d $backup_dir ];
then
    mkdir -p $backup_dir;
fi
bakfile=$backup_dir/all-${curtime}.sql

mysqldump -u $username -p$password -h $host -P${port} --all-databases > ${bakfile}
counts=`wc -l ${bakfile}`
tar -czvf ${bakfile}.tar.gz  ${bakfile}
echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""MYSQL BACKUP:${bakfile} with lines:${counts}"  >> ${logdir}/mysql_backup.log

#找出需要删除的备份如果过期则删除
delfile=`ls -l -crt  $backup_dir/*.sql | awk '{print $9 }' | head -1`
count=`ls -l -crt  $backup_dir/*.sql | awk '{print $9 }' | wc -l`

if [ $count -gt $number ]
then
  rm $delfile
  echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""BACKUP DELETE:${delfile}"  >> ${logdir}/mysql_backup.log
fi