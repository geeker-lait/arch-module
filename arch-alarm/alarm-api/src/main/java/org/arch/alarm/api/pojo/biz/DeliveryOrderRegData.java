package org.arch.alarm.api.pojo.biz;

import lombok.*;
import lombok.experimental.Accessors;
import org.arch.alarm.api.annotation.RegProperty;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:28 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DeliveryOrderRegData extends AlarmCommonRegData implements AlarmRegData {

    @RegProperty
    private String dd;


}
