package org.arch.alarm.application.test;

import jdk.nashorn.internal.runtime.linker.Bootstrap;
import org.arch.alarm.api.AlarmRegService;
import org.arch.alarm.api.dto.AlarmRegDto;
import org.arch.alarm.api.dto.ComputExpressDto;
import org.arch.alarm.api.dto.RegDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/4/2021 11:04 AM
 */
//@SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class FAlarmRegServiceTest {
    @Autowired
    AlarmRegService alarmRegService;

    /**
     * 新增规则
     */
    @Test
    public void add() {
        RegDto regDto = new RegDto();
        regDto.setCatid(1L);
        regDto.setRegKey("pickTimeout");
        regDto.setRegName("拣货超时");

        alarmRegService.saveReg(regDto);
    }


    /**
     * 配置计算器或规则表达式
     */
    @Test
    public void config() {
        ComputExpressDto computExpressDto = new ComputExpressDto();
        computExpressDto.setId(1389429675518783489L);
        computExpressDto.setComputerId(1L);
        computExpressDto.setPlaceholderJson("{key1:val1,key2:val2}");
        computExpressDto.setExpression("user1.age-user2.age > 0");
        alarmRegService.configComputOrExpression(computExpressDto);
    }


    /**
     * 整体编辑
     */
    @Test
    public void edit() {
        AlarmRegDto computExpressDto = new AlarmRegDto();
        computExpressDto.setId(1389429675518783489L);
        computExpressDto.setComputerId(1L);
        computExpressDto.setPlaceholderJson("{key1:val1,key2:val2}");
        computExpressDto.setExpression("user1.age-user2.age > 0");

//        computExpressDto.setRegName("");
        computExpressDto.setDescr("计算舱内拣货超时");
        alarmRegService.editAlarmReg(computExpressDto);
    }

}
