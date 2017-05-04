package com.example.raphifou.find;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

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

    }
}
