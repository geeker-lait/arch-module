#!/usr/bin/env bash

port=$1
dir=$2

cd ${dir}

pid=`ps aux | grep SimpleHTTPServer | grep ${port} | grep -v "grep" | awk -F" " '{print $2}'`
kill -9 $pid
echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""service for ${dir} killed:${pid}"  >> ${dir}/python_server_${port}.log
nohup python -m SimpleHTTPServer ${port} > ${dir}/python_simlehttpserver.log &
pid=`ps aux | grep SimpleHTTPServer | grep ${port} | grep -v "grep" | awk -F" " '{print $2}'`
sleep 3
echo `date +'%Y-%m-%d %H:%M:%S'`" -- INFO -- ""service for ${dir} started:${pid}"  >> ${dir}/python_server_${port}.log
