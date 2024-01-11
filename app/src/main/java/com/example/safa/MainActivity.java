package com.example.safa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    TextView safa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        safa=findViewById(R.id.safa);
        Button start=findViewById(R.id.start_front);
        start.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id;
        id=v.getId();
        if(id==R.id.start_front)
        {startNewActivity();


        }
        else {
            System.out.println("Error!none clicked");
        }
    }
    public void startNewActivity()
    {
        Intent intent=new Intent(this,customer_information.class);
        startActivity(intent);
    }

}

