package org.arch.project.sms.smgp;

import com.huawei.insa2.comm.smgp.message.SMGPDeliverMessage;
import com.huawei.insa2.comm.smgp.message.SMGPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SMGPSMProxy;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public class SmgpManageProxy extends SMGPSMProxy {

    private String passageId;
    private SmgpProxySender smgpProxySender;

    public SmgpManageProxy(SmgpProxySender smgpProxySender, String passageId, Args args) {
        super(args);
        this.passageId = passageId;
        this.smgpProxySender = smgpProxySender;
    }

    @Override
    public SMGPMessage onDeliver(SMGPDeliverMessage msg) {
        smgpProxySender.doProcessDeliverMessage(msg);
        return super.onDeliver(msg);
    }

    @Override
    public void onTerminate() {
        smgpProxySender.onTerminate(passageId);
    }

}
