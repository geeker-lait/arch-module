package ${pkg!""};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
* @description
*
* @author ${author!""}
* @date ${.now}
*/
@RestController
@ResquestMapping("${(mainClass?uncap_first)!""}")
public interface ${(mainClass?cap_first)!""}{
    <#list apis as method>
    /**
     * ${method.descr}
     * return ${(method.output.javaTyp)!""} ${(method.output.descr)!""}
     **/
    <#if client??>
    @Client()
    </#if>
    @${method.httpMethod}("${(method.name?uncap_first)!""}")
    ${(method.output.javaTyp?cap_first)!""} ${(method.name?uncap_first)!""}(<#list method.input as inp>${(inp.javaTyp?cap_first)!""} ${(inp.javaTyp?uncap_first)!""}</#list>);

    </#list>

}
