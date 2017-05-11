package com.example.raphifou.find.Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public interface BackEndApiService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("Login") String login, @Field("Password") String password);

    @FormUrlEncoded
    @POST("addUser")
    Call<mainResponseObject> register(@Field("Login") String login, @Field("FirstName") String firstName,
                                 @Field("LastName") String lastName, @Field("Password") String password,
                                 @Field("idFcm") String idFcm);

    @GET("api/getUsers")
    Call<UsersResponse> getUsers(@Header("x-access-token") String token);

    @POST("send")
    Call<FireBaseResponse> sendMsgtToUser(@Header("Content-Type") String contentType, @Header("Authorization") String key);

}
