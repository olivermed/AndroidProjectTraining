package com.example.raphifou.find;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.raphifou.find.Retrofit.ApiBackend;
import com.example.raphifou.find.Retrofit.BackEndApiService;
import com.example.raphifou.find.Retrofit.LoginResponse;
import com.example.raphifou.find.Retrofit.mainResponseObject;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static String Tag = RegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText registerSurname = (EditText) findViewById(R.id.registerSurname);
        final EditText registerFirstname = (EditText)findViewById(R.id.registerFirstname);
        final EditText registerLogin = (EditText) findViewById(R.id.registerLogin);
        final EditText registerPassword = (EditText)findViewById(R.id.registerPassword);
        final EditText registerConfirmPassword = (EditText)findViewById(R.id.registerConfirmpassword);


        final Button registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BackEndApiService service = ApiBackend.getClient().create(BackEndApiService.class);

                final String Surname = registerSurname.getText().toString();
                final String Firstname = registerFirstname.getText().toString();
                final String Login = registerLogin.getText().toString();
                final String Password = registerPassword.getText().toString();
                final String ConfirmPassword = registerConfirmPassword.getText().toString();

                Call<mainResponseObject> call = service.register(Login, Surname, Firstname, Password, FirebaseInstanceId.getInstance().getToken());
                call.enqueue(new Callback<mainResponseObject>() {
                    @Override
                    public void onResponse(Call<mainResponseObject> call, Response<mainResponseObject> response) {
                        if (response.code() == 200) {
                            Log.w(Tag, "Registered");
                            finish();
                        } else {
                            Log.w(Tag, "Registered failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<mainResponseObject> call, Throwable t) {
                        Log.e(Tag, t.toString());
                    }
                });
            }
        });
    }
}
