package com.magnus.infrastructure.remote.rpc;

import com.alibaba.cola.dto.Command;
import com.alibaba.cola.dto.Query;
import com.alibaba.cola.dto.SingleResponse;
import org.springframework.stereotype.Service;

/**
 * 单工程，该类用来模拟其他工程提供的rpc接口
 */
@Service
public class DemoRpcProvider {

    public SingleResponse<String> getNameById(Query query) {

        SingleResponse<String> output = new SingleResponse();
        output.setData("testName");

        return output;
    }

}
