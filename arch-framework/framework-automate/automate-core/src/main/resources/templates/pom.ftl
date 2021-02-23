<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <#if parent??><parent>
        <artifactId>${(parent!).artifactId!""}</artifactId>
        <groupId>${(parent!).groupId!""}</groupId>
        <version>${(parent!).version!""}</version>
    </parent>
    </#if><modelVersion>4.0.0</modelVersion>
    <artifactId>${artifactId!""}</artifactId>
    <groupId>${groupId!""}</groupId>
    <version>${version!""}</version>
    <#if packaging??>
    <packaging>${packaging!""}</packaging>
    </#if>

    <#if modules ?? && (modules?size >0)>
    <modules>
        <#list modules as module>
        <module>${(module.artifactId)!""}</module>
        </#list>
    </modules>
    </#if>

    <#if dependencies ?? && (dependencies?size >0)>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <#list dependencies as dependencie>
        <dependency>
            <artifactId>${(dependencie.artifactId)!""}</artifactId>
            <groupId>${(dependencie.groupId)!""}</groupId>
            <#if dependencie.versoin??>
            <version>${(dependencie.version)!""}</version>
            </#if>
        </dependency>
        </#list>
    </dependencies>
    </#if>
</project>
