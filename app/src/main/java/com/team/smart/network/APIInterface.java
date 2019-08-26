package com.team.smart.network;

import com.team.smart.vo.Foods;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface
{
    //protocall 약속
    @POST("/api/users")
    Call<Foods> DishesList(@Query("page") HashMap<String,String> param);

}
