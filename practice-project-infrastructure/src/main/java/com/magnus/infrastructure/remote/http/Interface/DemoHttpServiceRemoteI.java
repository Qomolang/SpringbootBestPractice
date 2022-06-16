package com.magnus.infrastructure.remote.http.Interface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DemoHttpServiceRemoteI {

    /**
     * 被调用接口的路径 + 调用方式
     *
     * @return
     */
    @GET("/http/test")
    Call<ResponseBody> test();

}
