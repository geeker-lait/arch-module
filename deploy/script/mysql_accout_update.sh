#!/usr/bin/env bash

# 全局参数
updateall="update account set source='self' WHERE SUBSTRING(ctime,18,20) < 33  AND SUBSTRING(ctime,1,13) >= '2019-11-21 12';"

mysql -uunichain -punichain*0731 -h 106.12.71.3 -P3306 -D unichain_account -e "${updateall}"

echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""BACKUP DELETE:${delfile}"  >> ${logdir}/mysql_backup.log