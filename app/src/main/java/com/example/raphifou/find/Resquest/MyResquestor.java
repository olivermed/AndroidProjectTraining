package com.example.raphifou.find.Resquest;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.raphifou.find.R;
import com.example.raphifou.find.ResponseObjects.getFriendsResponse;
import com.example.raphifou.find.Retrofit.UsersResponse;

import java.util.Map;

/**
 * Created by oliviermedec on 15/05/2017.
 */

public class MyResquestor {
    private Context mContext = null;
    private RequestQueue queue = null;
    private String url = null;

    public MyResquestor(Context context) {
        mContext = context;
        queue = Volley.newRequestQueue(mContext);
        url = mContext.getString(R.string.url_api);
    }

    public GsonRequest getFriend(String token, String idFriend) {
        url += "api/getFriends/" + idFriend;

        Map<String, String> headers = new ArrayMap<String, String>();
        headers.put("x-access-token", token);

        GsonRequest gsonRequest = new GsonRequest(url, getFriendsResponse.class, headers, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // Handle response
                getFriendsResponse friends = (getFriendsResponse)response;
                Log.w(MyResquestor.class.getSimpleName(), friends.results.get(0).friend.Login);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.w(MyResquestor.class.getSimpleName(), error.toString());}
        });

        queue.add(gsonRequest);
        return gsonRequest;
    }

    public void searchFriend(String token, String friend) {
        url += "api/searchFriends/" + friend;

        Map<String, String> headers = new ArrayMap<String, String>();
        headers.put("x-access-token", token);

        GsonRequest gsonRequest = new GsonRequest(url, UsersResponse.class, headers, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // Handle response
                UsersResponse friends = (UsersResponse)response;
                Log.w(MyResquestor.class.getSimpleName(), friends.results.get(0).Login);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.w(MyResquestor.class.getSimpleName(), error.toString());}
        });
        queue.add(gsonRequest);
    }

    public void addFriend(String token, String friend) {
        url += "api/addFriend/" + friend;

        Map<String, String> headers = new ArrayMap<String, String>();
        headers.put("x-access-token", token);

        GsonRequest gsonRequest = new GsonRequest(url, UsersResponse.class, headers, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                // Handle response
                UsersResponse friends = (UsersResponse)response;
                Log.w(MyResquestor.class.getSimpleName(), friends.results.get(0).Login);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.w(MyResquestor.class.getSimpleName(), error.toString());}
        });
        queue.add(gsonRequest);
    }
}
