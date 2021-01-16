# frps
nohup /deploy/packages/frp/frps -c /deploy/packages/frp/frps.ini  >> /deploy/logs/frps.log &

# nginx
docker run  -d -v /deploy/data/frp/nginx:/data -v /deploy/conf/nginx/nginx_frp.conf:/etc/nginx/nginx.conf:ro -p 7080:80  --restart=always --name nginx_frp nginx

# frpc
nohup /deploy/packages/frp/frpc -c /deploy/packages/frp/frpc.ini  >> /deploy/logs/frps.log &