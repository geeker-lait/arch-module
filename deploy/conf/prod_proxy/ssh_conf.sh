#!/usr/bin/env bash


39.100.152.67   s01.tastelifer.com
106.12.68.246   s12.tastelifer.com  # jump server && gateway
106.12.68.237   s22.tastelifer.com
106.12.71.2     s11.tastelifer.com
106.12.71.3     s21.tastelifer.com

# vim ~/.ssh/config
Host s01
  HostName s01.tastelifer.com
  User root
  ProxyCommand nc -x 127.0.0.1:3000 %h %p

Host s22
  HostName s22.tastelifer.com
  User root
  ProxyCommand nc -x 127.0.0.1:3000 %h %p

Host s11
  HostName s11.tastelifer.com
  User root
  ProxyCommand nc -x 127.0.0.1:3000 %h %p

Host s21
  HostName s21.tastelifer.com
  User root
  ProxyCommand nc -x 127.0.0.1:3000 %h %p


IdentityFile ~/.ssh/id_rsa