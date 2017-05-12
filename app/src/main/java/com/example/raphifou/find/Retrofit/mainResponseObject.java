package com.example.raphifou.find.Retrofit;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public class mainResponseObject {
    public class Results {
        String ok;
        String n;
    }

    public String message;

    public Results results;

    public mainResponseObject getResult() {
        return this;
    }
}
