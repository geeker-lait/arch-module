package ${package!""};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author ${author!""}
* @date ${.now}
*/
public interface ${(apiClass?cap_first)!""}{
    <#list methods as method>
    <#if supportRpc>
    @${method.httpMethod}
    </#if>
    /**
     * ${method.descr}
     **/
    ${method.output!""} ${method.name!""}(${method.inputs});

    </#list>

}
