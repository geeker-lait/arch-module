server:
  servlet:
    context-path:
spring:
    config:
      import: classpath:application-gen.yml
    datasource:
        username: root
        password: root
        url: jdbc:mysql://127.0.0.1:3306/arch?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
        driver-class-name: com.mysql.cj.jdbc.Driver
        platform: mysql
        #启动时需要初始化的建表语句
        #schema: classpath:form-schema.sql
        #初始化的数据
        #data: classpath:init-data.sql
        # Initialize the datasource with available DDL and DML scripts.
        #initialization-mode: always
        #continue-on-error: false
        #data-password:
        #data-username:
        #schema-password:
        #schema-username:
        sql-script-encoding: utf-8
        separator: ;
<#if redis??>
    redis:
      host:
</#if>

<#if rabbitmq??>
    rabbitmq:
      host:
</#if>
