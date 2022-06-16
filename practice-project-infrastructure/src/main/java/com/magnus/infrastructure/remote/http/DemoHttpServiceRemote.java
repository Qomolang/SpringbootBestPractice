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

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DemoHttpServiceRemote {

    public void httpTest() {
        //todo 实际使用中，需要把该项目改为bean，以进行全局网络相关请求进行复用
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        //todo 该对象也抽成bean，每个网址抽一个
        Retrofit DemoRemoteRetrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:7001/http/test")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DemoHttpServiceRemoteI demoHttpService = DemoRemoteRetrofit.create(DemoHttpServiceRemoteI.class);

        //构建request
        Call<ResponseBody> responseBodyCall = demoHttpService.test();

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
