package com.example.raphifou.find.Retrofit;

import com.example.raphifou.find.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public interface BackEndApiService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("Login") String login, @Field("Password") String password);
}
