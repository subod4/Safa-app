package com.example.safa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        TextView displayNameTextView = findViewById(R.id.display_name);
        TextView displayEmailTextView = findViewById(R.id.display_email);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference().child("Users");

        userRef.limitToLast(1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        String userID = snapshot.getKey();

                        DatabaseReference specificUserRef = database.getReference().child("Users").child(userID);

                        specificUserRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    String email = dataSnapshot.child("email").getValue(String.class);

                                    displayNameTextView.setText("Name: " + name);
                                    displayEmailTextView.setText("Email: " + email);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
