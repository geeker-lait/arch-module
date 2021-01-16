#!/usr/bin/env bash
set -x
# 根据<dev>分支提交注释中的关键字确定发布流程
# 1. 如果含有<#发布#>则:dev->release->tag && master,完成版本管理流程
# 2. 发布到测试则打包并启动应用,发布到生产则上传jar包到生产服务器

SHELL_FOLDER=$(dirname $(readlink -f "$0"))
source ${SHELL_FOLDER}/../conf/global.sh

projectpath=$1
cd ${projectpath}

curtime=`date +%Y%m%d`
curtimestamp=`date +'%Y%m%d%H%M%S'`

jardir=${packagesdir}/applications
mkdir -p ${jardir}
releaseprodkey="#发布生产#"
releaselabkey="#发布测试#"
relbranch=dev

git config --global user.name "opsadmin"
git config --global user.email "shazh@langqy.com"

useradd admin
# 日志移除
cd ${logsdir}
logs=`ls -l | grep  'jenkins_auto_deploy_' | awk -F" " '{print $NF}' | grep -v "jenkins_auto_deploy_${curtime}"`
for log in ${logs[*]}
do
    mv ${logsdir}/${log} ${logsbak}
done

cd ${projectpath}

git reset --hard
git checkout dev --
git pull --rebase origin dev
git pull
git checkout dev --
git pull --rebase origin dev
git pull

lastlog=`git log --pretty=format:"%h #%cn# %s"  -1| grep -v "#${releaseuser}#" `  # 过滤opsadmin的提交

echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},发布关键字:${lastlog}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
# 1.0 发布测试
if  [[ $lastlog  ==  *$releaselabkey* ]];then
    echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},切换到dev分支"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
fi

# 2.0 打包:如果目录包含pom.xml 文件则使用maven进行打包操作
ispomfile=`ls -l |awk -F" " '{print $NF}'|grep -E "^pom.xml"`
if [[ x${ispomfile} != x ]];then
    git checkout ${relbranch} -- && git pull origin ${relbranch}
    mvn clean package -Dmaven.test.skip=true
    jarfiles=`find ./ -type f  -name "*.jar" | grep target | grep -v "m2/repository"`
    OLD_IFS="$IFS"
    IFS=$'\n'
    jarlist=($jarfiles)
    IFS="$OLD_IFS"
    for jarpath in ${jarlist[*]}
    do
        #echo `date +'%Y-%m-%d %H:%M:%S'`" -- DEBUG -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},jarpath:${jarpath}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
        jarname=`echo ${jarpath} | awk -F"/" '{print $NF}'`
        OLD_IFS="$IFS"
        IFS=","
        appjarlist=($apps)
        IFS="$OLD_IFS"
        for appjar in ${appjarlist[*]}
        do
            #echo `date +'%Y-%m-%d %H:%M:%S'`" -- DEBUG -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},appjar:${appjar},jarname:${jarname}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
            # 如果是发布到测试则拷贝jar包 并 启动服务检测,如果是生产则拷贝jar到远程
            if [[ $jarname  =~  ${appjar}-[0-9][.]* ]];then
                # 如果jar包名称在配置中则为有效包并拷贝到目标目录并启动
                tagjarpath=${apppkgdir}/${curtimestamp}_${jarname}
                cp ${jarpath}  ${tagjarpath}
                echo "Taskid:${curtimestamp},JarName:${appjar},JenkinsBuildNum:${BUILD_DISPLAY_NAME},GitLog:${lastlog}"  >> ${logsdir}/jenkins_build_history.log
                chmod -R 777 ${tagjarpath}
                chown admin:admin ${tagjarpath}
                if [[ $lastlog  ==  *$releaselabkey* ]];then
                    ssh ${labuser}@${lab} "sh ${scriptdir}/apps_daemon.sh r $appjar"
                    echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME}到测试,拷贝jar包:${jarpath} 到:${tagjarpath},并部署:${appjar}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
                fi
                if [[ $lastlog  ==  *$releaseprodkey* ]];then
                    OLD_IFS="$IFS"
                    IFS=","
                    appserverslist=($appservers)
                    IFS="$OLD_IFS"
                    for appserver in ${appserverslist[*]}
                    do
                        sshpass -f "${confdir}/secrets/devops" scp -r ${tagjarpath} ${produser}@${appserver}:${apppkgdir}
                        echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME}到生产:${appserverslist},拷贝jar包:${jarpath} 到:${tagjarpath},并部署:${appjar}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
                    done
                fi
            fi
        done
    done
else
    echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},无pom.xml文件不做处理"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
fi

#3.0 如果是发布到生产环境则同步部署脚本到远程机器
if [[ $lastlog  ==  *$releaseprodkey* ]];then
    OLD_IFS="$IFS"
    IFS=","
    appserverslist=($appservers)
    IFS="$OLD_IFS"
    for appserver in ${appserverslist[*]}
    do
        sshpass -f "${confdir}/secrets/devops" scp -r /data/workspace/unichain_deploy/conf/global.sh ${produser}@${appserver}:${confdir}
        sshpass -f "${confdir}/secrets/devops" scp -r /data/workspace/unichain_deploy/conf/apps ${produser}@${appserver}:${confdir}
        sshpass -f "${confdir}/secrets/devops" scp -r /data/workspace/unichain_deploy/script ${produser}@${appserver}:${deployroot}
        echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},上传文件到远程:${produser}@${appserver}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
    done
fi

# 4.0 分支管理
if [[ $lastlog  ==  *$releaseprodkey* ]];then
    # 代码版本管理
    # dev -> release
    git checkout release -- && git pull && git push origin release:release && git pull origin release  --allow-unrelated-histories &&  git checkout release && git merge  -m "auto merge to release by jenkins" dev && git push -u origin release
    # release -> tag
    tagname=tag_${curtime}
    git checkout release -- && git pull && git tag -d ${tagname} && git push origin :refs/tags/${tagname}
    git tag -f -a ${tagname} -m "auto tag by jenkins with build id:${BUILD_DISPLAY_NAME}-${curtimestamp}" && git push origin --tags
    # release -> master
    git checkout release -- && git pull && git push origin master:master && git pull origin master  --allow-unrelated-histories && git checkout master && git merge -m "auto merge to master by jenkins" release && git push -u origin master
    echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""${curtimestamp},Jenkins 自动部署:${projectpath}#${BUILD_DISPLAY_NAME},合并到release/master并生成tag:${tagname}"  >> ${logsdir}/jenkins_auto_deploy_${curtime}.log
fi

tail -1000 ${logsdir}/jenkins_build_history.log | sort -u -r > ${logsdir}/jenkins_build.log