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
import java.util.List;
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

                    List<String> providers = locationManager.getProviders(true);
                    Location bestLocation = null;
                    for (String provider : providers) {
                        Location l = locationManager.getLastKnownLocation(provider);

                        if (l == null) {
                            continue;
                        }
                        if (bestLocation == null
                                || l.getAccuracy() < bestLocation.getAccuracy()) {
                            bestLocation = l;
                        }
                    }
                    if (bestLocation != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
                        DatabaseReference locationRef = mDataRef.child("Location").child(driverUsername);
                        LocationTime lt = new LocationTime(bestLocation.getLongitude(), bestLocation.getLatitude(), sdf.format(new Date()));
                        locationRef.setValue(lt);
                    }

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
