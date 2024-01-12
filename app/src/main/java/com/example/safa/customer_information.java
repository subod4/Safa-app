package com.example.safa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class customer_information extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView tvLatitude;
    private TextView tvLongitude;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_information);

        EditText Name, email, password;
        Button Submit, request_location;
        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("Users");
        Name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editTextPassword);
        Submit = findViewById(R.id.Submit);
        request_location = findViewById(R.id.btnRequestLocation);

        Spinner locationSpinner = findViewById(R.id.spinnerLocation);
        Spinner areaSpinner = findViewById(R.id.spinnerArea);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<CharSequence> areaAdapter = ArrayAdapter.createFromResource(this, R.array.area_array, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        // Initialize locationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Initialize locationListener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle the updated location here
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // Update TextViews with the new values
                tvLatitude.setText("Latitude: " + latitude);
                tvLongitude.setText("Longitude: " + longitude);

                // Do something with the location data
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Initialize TextViews
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String email_add = email.getText().toString();
                int selectedRadioButtonID = radioGroupGender.getCheckedRadioButtonId();
                String selectgender = "";

                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
                    selectgender = selectedRadioButton.getText().toString();
                }

                // Update the userMap with String values
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name", name);
                userMap.put("email", email_add);
                userMap.put("gender", selectgender);
                userMap.put("latitude", String.valueOf(latitude));
                userMap.put("longitude", String.valueOf(longitude));

                // Save data to Firebase
                root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(customer_information.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                });

                // Navigate to the home activity
                Intent intent = new Intent(customer_information.this, home.class);
                startActivity(intent);
            }
        });
    }

    // This method is triggered when the "Request Location Updates" button is clicked
    public void onRequestLocationClick(View view) {
        // Check and request location updates here
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            // Request location updates
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    locationListener
            );
        } else {
            // Handle the case when permission is not granted
            // You can request permission here using ActivityCompat.requestPermissions()
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }
    }
}
