package com.magnus.infrastructure.remote.rpc;

import javax.annotation.Resource;

/**
 * 并不是dubbo
 */
public class DemoRpcRemote {

    @Resource
    private DemoRpcProvider rpcProvider;

    public String getNameById(){
        return null;
    }
}
