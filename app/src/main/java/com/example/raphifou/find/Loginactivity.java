package com.example.raphifou.find;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.LoginObject;
import com.example.raphifou.find.Retrofit.LoginResponse;
import com.example.raphifou.find.Retrofit.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginactivity extends AppCompatActivity {
    public static String Tag = Loginactivity.class.getSimpleName();
    SharedPreferences sharedPref = null;
    public EditText txtLogin;
    public EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtPassword  = (EditText)findViewById(R.id.txtPassword);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        setInputs();

        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView registerLink = (TextView) findViewById(R.id.TvRegisterhere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(Loginactivity.this, RegisterActivity.class);
                Loginactivity.this.startActivity(registerIntent);
            }
        });

        final BackEndApiService service = ApiBackend.getClient().create(BackEndApiService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject loginObject = new LoginObject(txtLogin.getText().toString(), txtPassword.getText().toString());

                if (txtLogin.getText() != null || txtPassword.getText() != null) {
                    Call<LoginResponse> call = service.login(loginObject.Login, loginObject.Password);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                Log.w(Tag, response.toString());

                            if (response.code() == 200) {
                                LoginResponse loginResponse = response.body().getResult();
                                String token = loginResponse.token;

                                if (token != null) {
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(getString(R.string.token), token);
                                    editor.putString(getString(R.string.password), txtPassword.getText().toString());
                                    editor.putString(getString(R.string.login), txtLogin.getText().toString());
                                    editor.putString(getString(R.string.id), loginResponse._id);
                                    editor.commit();

                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e(Tag, t.toString());
                        }
                    });
                }
            }
        });
    }

    private void setInputs() {
        if (sharedPref != null) {
            String login = sharedPref.getString(getString(R.string.login), null);
            String pwd = sharedPref.getString(getString(R.string.login), null);

            if (login != null) {
                txtLogin.setText(login);
            }
            if (pwd != null) {
                txtPassword.setText(pwd);
            }
        }
    }
}