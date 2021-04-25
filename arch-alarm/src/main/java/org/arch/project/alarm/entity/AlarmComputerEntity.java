package org.arch.project.alarm.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date
 */
@Data
@Builder
public class AlarmComputerEntity {

    private Long id;
    private String regKey;
    private String regName;

}
