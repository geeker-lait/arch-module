package ${pkg!""};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
* @description ${descr!""}
*
* @author ${author!""}
* @date ${.now}
*/
@RestController
@ResquestMapping("${(name?uncap_first)!""}")
public interface ${(name?cap_first)!""}${suffix!""}{
    <#list curls as curl>
    /**
     * ${curl.descr}
     *<#list curl.inputs as inp>
     * @param ${(inp.typ?cap_first)!""} ${inp.descr!""}</#list>
     * @return ${(curl.output.typ)!""} ${(curl.output.descr)!""}
     */
    <#if client??>
    @Client()
    </#if>
    ${curl.httpMethod}("${(curl.name?uncap_first)!""}")
    ${(curl.output.typ?cap_first)!"void"} ${(curl.name?uncap_first)!""}(<#list curl.inputs as inp>${(inp.typ?cap_first)!""} ${(inp.name?uncap_first)!""}<#if inp_has_next>, </#if></#list>);
    </#list>

}
