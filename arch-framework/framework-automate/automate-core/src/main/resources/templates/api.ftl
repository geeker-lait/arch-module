package ${pkg!""};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author ${author!""}
* @date ${.now}
*/
public interface ${(topicName?cap_first)!""}{
    <#list apis as method>
    /**
     * ${method.descr}
     * return ${(method.output.javaTyp)!""} ${(method.output.descr)!""}
     **/
    <#if client??>
    @Client()
    </#if>
    @${method.httpMethod}
    ${(method.output.javaTyp)!""} ${method.name!""}();

    </#list>

}
