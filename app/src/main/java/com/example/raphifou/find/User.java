package com.example.raphifou.find;

/**
 * Created by oliviermedec on 11/05/2017.
 */

public class User {
    public String FirstName;
    public String Surname;
    public String Login;
    public String Password;
    public String idFcm;
    public String _id;

    public User(String FirstName, String Surname, String Login, String Password,String idFcm) {
        this.FirstName = FirstName;
        this.Surname = Surname;
        this.Login = Login;
        this.Password = Password;
        this.idFcm = idFcm;
    }
}
