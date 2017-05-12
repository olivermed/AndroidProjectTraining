package com.example.raphifou.find;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.raphifou.find.Retrofit.ApiBackFireBase;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.FireBaseObject;
import com.example.raphifou.find.Retrofit.FireBaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckAuth extends AppCompatActivity {
    public String sendingFcm = null;
    public String loginUser = null;
    public String idUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_auth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnPositive = (Button)findViewById(R.id.btnPositive);
        Button btnNegative = (Button)findViewById(R.id.btnNegative);

        Intent intent = getIntent();
        sendingFcm = intent.getStringExtra(getString(R.string.sendingFcm));
        loginUser = intent.getStringExtra(getString(R.string.loginUser));
        idUser = intent.getStringExtra(getString(R.string.idUser));

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.preference_file_key), MODE_PRIVATE);

                String idFcm = sharedPref.getString(getString(R.string.idFcm), null);
                String login = sharedPref.getString(getString(R.string.login), null);
                String id = sharedPref.getString(getString(R.string.id), null);

                Log.w(getPackageName(), "My idFcm :: " + idFcm);
                Log.w(getPackageName(), "User sefndingFcm :: " + idFcm
                );
                Log.w(getPackageName(), "My login :: " + login);

                BackEndApiService service = ApiBackFireBase.getClientFireBase().create(BackEndApiService.class);
                Call<FireBaseResponse> call = service.sendMsgtToUser("application/json",
                        "key=AAAAYeZt82k:APA91bGQwNoUkZybkScveS_-koc2I6ySW9_9BXJBAKEN6t43Xs8S2diVxXp-5ERdYYSuj17QpUMc5rwINFDbjIyidzLYuw-2uNl5Qx1CSjrPqxFrDCPIzxCkxYSCBLg_5S5X6P4nCuXS",
                        new FireBaseObject(sendingFcm, login, "Here is the location of " + login, 0, idFcm, id, login));
                call.enqueue(new Callback<FireBaseResponse>() {
                    @Override
                    public void onResponse(Call<FireBaseResponse> call, Response<FireBaseResponse> response) {
                        Log.w(getPackageName(), response.toString());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<FireBaseResponse> call, Throwable t) {
                        Log.e(getPackageName(), t.toString());
                    }
                });
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
