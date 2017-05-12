package com.example.raphifou.find.Retrofit;


/**
 * Created by oliviermedec on 12/05/2017.
 */

public class FireBaseObject {
    public String to;
    public NotifFireBase notification;
    public Data data;

    public class NotifFireBase {
        public String title;
        public String body;

        public NotifFireBase(String title, String boby, int flag) {
            this.body = boby;
            this.title = title;
        }
    }

    public class Data {
        public int flag = -1;
        public String latitude = "39.9488031";
        public String longitude = "116.3376983";
        public String idFcm;
        public String idUser;
        public String loginUser;

        public Data(int flag, String idFcm, String idUser, String loginUser) {
            this.flag = flag;
            this.idFcm = idFcm;
            this.idUser = idUser;
            this.loginUser = loginUser;
        }
    }

    public FireBaseObject(String to, String title, String body, int flag, String idFcm, String idUser, String loginUser) {
        this.to = to;
        this.notification = new NotifFireBase(title, body, flag);
        this.data = new Data(flag, idFcm, idUser, loginUser);
    }
}
