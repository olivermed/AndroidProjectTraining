package com.example.raphifou.find.ResponseObjects;

import com.example.raphifou.find.Retrofit.UserResponse;
import com.example.raphifou.find.Retrofit.UsersResponse;

import java.util.List;

/**
 * Created by oliviermedec on 15/05/2017.
 */

public class getFriendsResponse {
    public List<Friend> results;

    public class Friend {
        public UserResponse.User friend;
    }
}
