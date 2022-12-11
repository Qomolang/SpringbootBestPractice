package com.magnus.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author gaosong
 */
@Slf4j
@Configuration
public class RetrofitHttpConfig {

    @Resource
    private OkHttpClient okHttpClient;


    @Bean
    public OkHttpClient okHttpClient(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return client;

    }

    @Bean
    public Retrofit exampleHttp(){

        String baseUtl = "http://localhost:7001/";

        //todo 该对象也抽成bean，每个网址抽一个
        Retrofit DemoRemoteRetrofit = new Retrofit.Builder()
                .baseUrl(baseUtl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return DemoRemoteRetrofit;

    }

}
