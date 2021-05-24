package org.arch.alarm.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/17/2021 4:43 PM
 */
//@EnableFeignClients
@SpringBootApplication
@ImportResource({"classpath:application-dubbo.xml"})
public class AlarmApplication {
}
