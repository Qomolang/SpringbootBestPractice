package com.magnus.infrastructure.remote.http.Interface;

import okhttp3.ResponseBody;
import org.springframework.data.domain.PageRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DemoHttpServiceRemoteI {

    /**
     * 被调用接口的路径 + 调用方式
     *
     * @return
     */
    @GET("/http/test")
    Call<ResponseBody> testGet(@Header("Cookie") String cookieString);

    @POST("/http/test")
    Call<ResponseBody> testPost(@Body PageRequest demoRequest);

}
