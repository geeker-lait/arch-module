package org.arch.project.event.config;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 1:59 PM
 * @description: TODO
 */
@Data
public class WorkProperties {
    private int groupOrder;
    private String groupName;
    private List<String> topics;

}
