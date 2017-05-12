package com.example.raphifou.find.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oliviermedec on 12/05/2017.
 */

public class ApiBackFireBase {

    public static final String BASE_URL_FIREBASE = "https://fcm.googleapis.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClientFireBase() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_FIREBASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
