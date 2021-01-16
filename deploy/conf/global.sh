#!/usr/bin/env bash

# 全局路径
deployroot=/deploy
logsdir=${deployroot}/logs
packagesdir=${deployroot}/packages
confdir=${deployroot}/conf
scriptdir=${deployroot}/script
sourcedir=${deployroot}/source
datadir=${deployroot}/data
initdir=${deployroot}/init
logsbak=${logsdir}/logs_bak
packagesbak=${packagesdir}/applications_bak

releaskeypath=${confdir}/releasekey.txt

# 全局参数
lab=lab.unichain.tech
s01=s01.tastelifer.com
s12=s12.tastelifer.com
s11=s11.tastelifer.com
s21=s21.tastelifer.com
s22=s22.tastelifer.com
s31=s31.tastelifer.com
s32=s32.tastelifer.com
s33=s33.tastelifer.com

env=dev
hostname=`hostname`

localip=`grep $hostname /etc/hosts | grep -v "#" |awk -F" " '{print $1}'|head -1`
if [[ ${hostname} == *jenkins_node_prod* ]] ;then
    hostname=jenkins_node_prod
fi

if [[ ${hostname} == *tastelifer* ]] ;then
    env=prd
fi

labuser=admin
releaseuser=opsadmin
# 应用参数
applines=`cat ${confdir}/apps/${hostname} |grep -v "#"|sort -u | grep -v '^$'`
for i in ${applines[@]};do apps=$apps","$i; done
apps=`echo ${apps:1}`

allapplines=`cat ${confdir}/apps/* |grep -v "#"|sort -u | grep -v '^$'`
for i in ${allapplines[@]};do allapps=$allapps","$i; done
allapps=`echo ${allapps:1}`

apppkgdir=${packagesdir}/applications
applogs=${logsdir}/applications
appservers=${s11}","${s31}","${s12}
produser=devops
