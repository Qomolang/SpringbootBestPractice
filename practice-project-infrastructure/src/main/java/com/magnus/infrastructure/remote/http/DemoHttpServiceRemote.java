package com.magnus.infrastructure.remote.http;

import com.magnus.infrastructure.remote.http.Interface.DemoHttpServiceRemoteI;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DemoHttpServiceRemote {

    @Resource
    private Retrofit exampleHttp;

    public void httpTest() {

        DemoHttpServiceRemoteI demoHttpService = exampleHttp.create(DemoHttpServiceRemoteI.class);

        //构建request
        Call<ResponseBody> responseBodyCall = demoHttpService.testGet(null);

        //进行网络请求
        Response response = null;
        try {
            //同步调用
            response = responseBodyCall.execute();
            //异步调用
            //responseBodyCall.enqueue();
        } catch (Exception e) {
            log.error("e", e);
        }

        log.info("response:{}", response);
    }


}
