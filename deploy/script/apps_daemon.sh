#!/usr/bin/env bash

# 启动指定的应用/服务,并定期检查状态如果退出则重新启动

SHELL_FOLDER=$(dirname $(readlink -f "$0"))
source ${SHELL_FOLDER}/../conf/global.sh

curdate=`date +%Y%m%d`
curtimestamp=`date +'%Y%m%d%H%M%S'`

model=$1  # 持续重启:rr, 更新重启:r
tagapp=$2
validapps=$apps

if [[ x${tagapp} != x ]];then
  validapps=${tagapp}
fi

OLD_IFS="$IFS"
IFS=","
applist=($validapps)
IFS="$OLD_IFS"

# 日志移除
cd ${logsdir}
logs=`ls -l | grep  'apps_daemon_' | awk -F" " '{print $NF}' | grep -v "apps_daemon_${curdate}"`
for log in ${logs[*]}
do
    mv ${logsdir}/${log} ${logsbak}
done

cd ${apppkgdir}
echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},运行模式:${model}(默认r),指定应用:${tagapp},有效应用:${validapps}"  >> ${logsdir}/apps_daemon_${curdate}.log
for app in ${applist[*]}
do
    newpid=null
    # 如果传入了服务名称则匹配名称并启动最新的服务jar包
    if [[ x${app} != x ]];then
        lastupdatedjar=`ls -l | grep -E '*.jar$' | awk -F" " '{print $NF}' | grep -E "[0-9]{1,}[_]${app}-[0-9]*" | sort -u | tail -1`  # 可能有多个版本的应用jar包,并匹配jar包名称获取最新的jar包(不包含全路径)
        oldjars=`ls -l | grep -E '*.jar$' | awk -F" " '{print $NF}' | grep -E "[0-9]{1,}[_]${app}-[0-9]*" | sort -u |  head -n -1`
        pidlist=`ps aux | grep -v "grep"|grep -E "[0-9][_]${app}-[0-9]"`  # 仅仅匹配jar包开头匹配的进程，支持的jar名称规范为: 201901010101-app-demo-0.1.1-*.jar 或 app-demo-0.1.1-*.jar
        pids=`ps aux | grep -v "grep"|grep -E "[0-9][_]${app}-[0-9]" |awk -F" " '{print $2}'`
        pidcount=${#pids[*]}
        if [[ ${pidcount} -gt 1 ]];then  # 匹配超过1个进程说明应用名重复/冲突
           echo `date +'%Y-%m-%d %H:%M:%S'`" -- ERROR -- ""${curtimestamp},运行模式:${model},应用服务:${app}异常->匹配多个jar包"  >> ${logsdir}/apps_daemon_${curdate}.log
        else # 匹配0个或1个,需要比对版本/更新时间,如果运行的是旧版本则杀死并重启,如果没有服务运行则启动
           runjar=`ps aux | grep -v "grep"|grep -E "[0-9][_]${app}-[0-9]" | awk -F"/" '{print $NF}'`  # 获取运行中的jar包名称
           if [[ x${runjar} != x${lastupdatedjar} ]] || [[ x${model} == xrr ]];then
               kill -9 ${pids} # 如果未运行则空执行
               mv -f ${applogs}/*_${app}.log ${logsbak}
               mv -f ${logsbak}/${curdate}_${app}.log ${applogs}
               nohup java -jar -Xmx2048m -Xms1024m -Dspring.profiles.active=${env} -Dspring.cloud.inetutils.preferred-networks=${localip} ${apppkgdir}/${lastupdatedjar}  > ${applogs}/${curdate}_${app}.log &
               newpid=`ps aux | grep -v "grep"|grep  "${lastupdatedjar}" |awk -F" " '{print $2}'`
           fi
           # 移动过期jar包到备份目录
           for oldjar in ${oldjars[*]}
           do
               mv ${apppkgdir}/${oldjar} ${packagesbak}
           done
           echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},运行模式:${model},应用服务启动:${app}#${runjar}#${pids},最新状态:${lastupdatedjar}#${newpid}"  >> ${logsdir}/apps_daemon_${curdate}.log
        fi
    else
        echo `date +'%Y-%m-%d %H:%M:%S'`" -- ERROR -- ""${curtimestamp},运行模式:${model},应用服务:${app}无可用jar包"  >> ${logsdir}/apps_daemon_${curdate}.log
    fi
done

echo "------------------------------------------------java process info"  > ${logsdir}/pidinfo.log
ps aux | awk -F" " '{print $1"  "$2"  "$9"  "$10"  "$11"  "$12"  "$13"  "$14" "$15" "$16" "$17" "$19}'| grep "/deploy/packages/applications"  | grep -v "grep" >> ${logsdir}/pidinfo.log
echo "------------------------------------------------java info for port and process"  >> ${logsdir}/pidinfo.log
netstat -alnp | grep tcp | grep java |grep -E ":::[500,180,160,190][0-9][0-9]" | sort -u >> ${logsdir}/pidinfo.log

# 清理无用jar包(删除或移动)
OLD_IFS="$IFS"
IFS=","
allapplist=($allapps)
IFS="$OLD_IFS"

cd ${apppkgdir}

for app in ${allapplist[*]}
do
    # 如果传入了服务名称则匹配名称并启动最新的服务jar包
    if [[ x${app} != x ]];then
        oldjars=`ls -l | grep -E '*.jar$' | awk -F" " '{print $NF}' | grep -E "[0-9]{1,}[_]${app}-[0-9]*" | sort -u |  head -n -1`
        # 移动过期jar包到备份目录
        for oldjar in ${oldjars[*]}
        do
            #rm -rf ${apppkgdir}/${oldjar}  ## 最好避免删除操作
            mv ${apppkgdir}/${oldjar} ${packagesbak}  ## 移动到备份目录但是需要手工定期清理
        done
    fi
done
