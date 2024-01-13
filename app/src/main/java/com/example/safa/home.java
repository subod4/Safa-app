package com.example.safa;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
//import android.support.v4.app.NotificationCompat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

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
                                    String flag=dataSnapshot.child("flag").getValue(String.class);

                                    displayNameTextView.setText("Name: " + name);
                                    displayEmailTextView.setText("Email: " + email);

                                    /*
                                    if("false".equals(flag)){
                                        Toast.makeText(home.this,"false",Toast.LENGTH_SHORT).show();
                                        FirebaseMessaging.getInstance().subscribeToTopic("News")
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        String msg = "Done";
                                                        if (!task.isSuccessful()) {
                                                            msg = "Failed";
                                                        }

                                                    }
                                                });

                                    }
                                    else{
                                        Toast.makeText(home.this,"True",Toast.LENGTH_SHORT).show();
                                    }
                                    */
                                }
                            }
                        });
                    }
                }
            }
        });
        public class NotificationHelper {

            private static final String CHANNEL_ID = "message_notification";
            private static final String CHANNEL_NAME = "Message Notification";

            public static void showNotification(Context context, String title, String message) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // Create a Notification Channel for Android Oreo and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Notification notification = builder.build();

                // Use a unique notification ID to update or cancel the notification
                int notificationId = 1;

                // Show the notification
                notificationManager.notify(notificationId, notification);
            }
        }
    }
}
