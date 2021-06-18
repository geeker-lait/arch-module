package ${package!""};

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
@RequestMapping("${(name?uncap_first)!""}")
public interface ${(name?cap_first)!""}${suffix!""} extends CrudRest<${(name?cap_first)!""}Request, Long, ${(name?cap_first)!""}SearchDto>{


}
