<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <#if parent??><parent>
        <artifactId>${(parent!).artifactId!""}</artifactId>
        <groupId>${(parent!).groupId!""}</groupId>
        <version>${(parent!).version!""}</version>
    </parent>
    </#if>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>${artifactId!""}</artifactId>
    <groupId>${groupId!""}</groupId>
    <version>${version!""}</version>


    <dependencies>


    </dependencies>
</project>
