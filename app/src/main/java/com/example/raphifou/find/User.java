package com.example.raphifou.find;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public class User {
    public String FirstName;
    public String LastName;
    public String Login;
    public String Password;
    public String idFcm;
    public String _id;

    public User(String FirstName, String LastName, String Login, String Password,String idFcm) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Login = Login;
        this.Password = Password;
        this.idFcm = idFcm;
    }
}
