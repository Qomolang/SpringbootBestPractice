package com.magnus.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Slf4j
@Configuration
public class RetrofitConfig {

    @Resource
    private OkHttpClient okHttpClient;

    @Bean
    //@DependsOn(value = {"OkHttpClient"})
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
