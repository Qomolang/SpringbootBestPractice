package com.magnus.service.sms;

import com.google.common.base.Preconditions;
import com.magnus.excel.filternode.ScaleFilterNode;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
public class SmsFilterChain {

    @Resource
    private SmsFilterNode scaleFilterNode;

    /**
     * true代表通过
     */
    public boolean doCheck(EmpContext empContext) {
        Preconditions.checkNotNull(empContext);

        boolean scaleFlag = scaleFilterNode.checkScale(null, 1);
        if (!scaleFlag) {
            return false;
        }

        return true;
    }
}
