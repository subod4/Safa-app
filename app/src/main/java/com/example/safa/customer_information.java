package com.example.safa;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.RadioGroup;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class customer_information extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_information);
        EditText Name,email,password;
        Button Submit;
        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference root=db.getReference().child("Users");
       Name=findViewById(R.id.editTextName);
       email=findViewById(R.id.editEmail);
       password=findViewById(R.id.editTextPassword);
       Submit=findViewById(R.id.Submit);

        Spinner locationSpinner = findViewById(R.id.spinnerLocation);
        Spinner areaSpinner = findViewById(R.id.spinnerArea);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<CharSequence> areaAdapter = ArrayAdapter.createFromResource(this, R.array.area_array, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString();
                String email_add = email.getText().toString();
                int selectedRadioButtonID=radioGroupGender.getCheckedRadioButtonId();
                String selectgender="";

                if (selectedRadioButtonID!=-1)
                {
                    RadioButton selectedRadioButton=findViewById(selectedRadioButtonID);
                    selectgender=selectedRadioButton.getText().toString();
                }

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name", name);
                userMap.put("email", email_add);
                userMap.put("gender",selectgender);
                root.push().setValue(userMap);
                Intent intent=new Intent(customer_information.this, home.class);
                startActivity(intent);
            }
        });
    }

}
