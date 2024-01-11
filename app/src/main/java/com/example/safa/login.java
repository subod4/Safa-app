package com.example.safa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView noaccount = (TextView) findViewById(R.id.donthaveaccount);
        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                id = v.getId();
                if (id == R.id.donthaveaccount) {
                    startNewActivity();
                }

            }
        });

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    Toast.makeText(login.this, "LOGIN SUCESSFUL", Toast.LENGTH_SHORT).show();
                } else {
                    //incorrect
                    Toast.makeText(login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
    public void startNewActivity()
    {
        Intent intent=new Intent(login.this, customer_information.class);
        startActivity(intent);
    }
}