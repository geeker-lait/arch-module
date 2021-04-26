package org.arch.project.alarm;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Component
public class ExpressionHandler {

    private Map<String, String> operatorsMap = new HashMap<>();

    private void init() {
        operatorsMap.put("\\s+and\\s+", "&&");
        operatorsMap.put("\\s+or\\s+", "||");
        operatorsMap.put("\\s+mod\\s+", "%");
        operatorsMap.put("(?|<|\\!|=)=(?!=|>|<|\\!)", "==");
        operatorsMap.put("\\)and\\s+", ")&&");
        operatorsMap.put("\\s+and\\(", "&&(");
        operatorsMap.put("\\)and\\(", ")&&(");
        operatorsMap.put("\\)or\\s+", ")||");
        operatorsMap.put("\\s+or\\(", "||(");
        operatorsMap.put("\\)or\\(", ")||(");
        operatorsMap.put("\\)mod\\s+", ")%");
        operatorsMap.put("\\s+mod\\(", "%(");
        operatorsMap.put("\\)mod\\(", ")%(");
        operatorsMap.put("\\!\\s+\\=", "!=");
        operatorsMap.put("\\>\\s+\\=", ">=");
        operatorsMap.put("\\<\\s+\\=", "<=");
    }
}
