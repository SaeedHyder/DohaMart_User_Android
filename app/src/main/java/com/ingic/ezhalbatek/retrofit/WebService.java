package com.ingic.ezhalbatek.retrofit;


import com.ingic.ezhalbatek.entities.LoggedInUser;
import com.ingic.ezhalbatek.entities.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebService {


    @POST("login")
    Call<ResponseWrapper<LoggedInUser>> login(@Body String body);

}