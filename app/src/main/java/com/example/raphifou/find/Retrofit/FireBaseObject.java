package com.example.raphifou.find.Retrofit;


/**
 * Created by oliviermedec on 12/05/2017.
 */

public class FireBaseObject {
    public String to;
    public Data data;

    public class Data {
        public int flag = -1;
        public String latitude;
        public String longitude;
        public String idFcm;
        public String idUser;
        public String loginUser;
        public String title;
        public String body;

        public Data(String title, String body, int flag, String idFcm, String idUser, String loginUser, String latitude, String longitude) {
            this.flag = flag;
            this.idFcm = idFcm;
            this.idUser = idUser;
            this.loginUser = loginUser;
            this.longitude = longitude;
            this.latitude = latitude;
            this.body = body;
            this.title = title;
        }
    }

    public FireBaseObject(String to, String title, String body, int flag, String idFcm, String idUser, String loginUser, String latitude, String longitude) {
        this.to = to;
        this.data = new Data(title, body,flag, idFcm, idUser, loginUser, latitude, longitude);
    }
}
