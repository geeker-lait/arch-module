package ${package!""};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author ${author!""}
* @date ${.now}
*/
@SpringBootApplication(scanBasePackages = {"${package!""}"})
public class ${(mainClass?cap_first)!""}{
    public static void main(String[] args) {
        SpringApplication.run(${(mainClass?cap_first)!""}.class, args);
    }
}

