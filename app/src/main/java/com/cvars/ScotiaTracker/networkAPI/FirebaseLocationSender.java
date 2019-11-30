package com.cvars.ScotiaTracker.networkAPI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.cvars.ScotiaTracker.activity.UserActivity;
import com.cvars.ScotiaTracker.model.pojo.LocationTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Locale;

public class FirebaseLocationSender {

    private Thread locationSenderLoop;
    private DatabaseReference mDataRef;
    private LocationManager locationManager;
    private String driverUsername;

    public FirebaseLocationSender(LocationManager locationManager, String driverUsername) {
        this.locationManager = locationManager;
        this.mDataRef = FirebaseDatabase.getInstance().getReference();
        this.driverUsername = driverUsername;

        loopBackgroundSendLocation();
    }

    private void loopBackgroundSendLocation() {
        locationSenderLoop = new Thread(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                while (true) {

                    if (Thread.interrupted()){
                        return;
                    }

                    Location location = null;

                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
                    DatabaseReference locationRef = mDataRef.child("Location").child(driverUsername);
                    LocationTime lt = new LocationTime(location.getLongitude(), location.getLatitude(), sdf.format(new Date()));
                    locationRef.setValue(lt).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            System.out.println(1);
                        }
                    });

                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });

        locationSenderLoop.start();
    }

    public void onDestroy() {
        locationSenderLoop.interrupt();
    }
}
