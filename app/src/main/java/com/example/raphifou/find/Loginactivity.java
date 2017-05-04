package com.example.raphifou.find;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Loginactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        final EditText connectLogin = (EditText) findViewById(R.id.connectLogin);
        final EditText loginPassword = (EditText)findViewById(R.id.loginPassword);

        final Button blogin = (Button) findViewById(R.id.blogin);
        final TextView registerLink = (TextView) findViewById(R.id.TvRegisterhere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(Loginactivity.this,RegisterActivity.class);
                Loginactivity.this.startActivity(registerIntent);
            }
        });
    }
}
