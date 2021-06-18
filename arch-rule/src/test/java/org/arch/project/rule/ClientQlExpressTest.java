package org.arch.project.rule;

import org.apache.commons.collections.map.ListOrderedMap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientQlExpressTest {


    @Autowired
    QLExpresionUtil qlExpresionUtil;

    /**
     * @param idList
     * @param etext
     * @param resultMap
     */
    @Test
    public void run(List<Long> idList, String etext, ListOrderedMap resultMap) throws Exception {
        for (Long id : idList) {
            Map<String, Object> innerContext = new HashMap<String, Object>();
            innerContext.put("id", id);
            Object result = qlExpresionUtil.execute(etext, innerContext);
            resultMap.put(id, result);
        }
    }

}
