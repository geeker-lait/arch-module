package org.arch.project.alarm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service("storeCheatComputer")
public class StoreCheatComputer extends AbstractRegComputer implements RegComputeable {

    @Override
    public String getRegKey() {
       return  RegDescr.STORE_CHEAT.getRegKey();
    }

    @Override
    public boolean compute(String expression) {

        return false;
    }


}
