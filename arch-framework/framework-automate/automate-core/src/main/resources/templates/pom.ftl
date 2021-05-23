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

    <#if dependencyManagement ?? && (dependencyManagement?size >0)>
    <dependencyManagement>
        <dependencies>
        <#list dependencyManagement as dm>
            <dependency>
                <groupId>${dm.groupId!""}</groupId>
                <artifactId>${dm.artifactId!""}</artifactId>
                <#if dm.isModule>
                <version>${r'${project.version}'}</version>
                <#else>
                <version>${dm.version!""}</version>
                </#if>
                <#if dm.type??>
                <type>${dm.type!""}</type>
                </#if>
                <#if dm.scope??>
                <scope>${dm.scope!""}</scope>
                </#if>
            </dependency>
        </#list>
        </dependencies>
    </dependencyManagement>
    </#if>

    <dependencies>
        <#if modules ?? && (modules?size >0)>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        </#if>
        <#if dependencies ?? && (dependencies?size >0)>
        <#list dependencies as dependencie>
        <dependency>
            <artifactId>${(dependencie.artifactId)!""}</artifactId>
            <groupId>${(dependencie.groupId)!""}</groupId>
            <#if dependencie.version??>
            <version>${(dependencie.version)!""}</version>
            </#if>
        </dependency>
        </#list>
        </#if>
    </dependencies>

    <build>
        <#if application??>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.yml</include>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.sql</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.yml</include>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.sql</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
        </#if>
        <plugins>
            <#if application??>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <delimiters>@</delimiters>
                    <useDefaultDelimiters>false</useDefaultDelimiters>
                </configuration>
            </plugin>
            </#if>
        </plugins>
    </build>

</project>
