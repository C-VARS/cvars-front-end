package com.cvars.ScotiaTracker.networkAPI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;


import com.cvars.ScotiaTracker.model.pojo.LocationTime;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FirebaseLocationSender {

    private DatabaseReference mDataRef;
    private String driverUsername;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            sendLocationToFirebase(locationResult.getLastLocation());
        }
    };

    public FirebaseLocationSender(Context context, String driverUsername) {
        this.mDataRef = FirebaseDatabase.getInstance().getReference();
        this.driverUsername = driverUsername;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }

    private void sendLocationToFirebase(Location location){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z", Locale.ENGLISH);
        DatabaseReference locationRef = mDataRef.child("Location").child(driverUsername);
        LocationTime lt = new LocationTime(location.getLongitude(), location.getLatitude(), sdf.format(new Date()));
        locationRef.setValue(lt);
    }

    public void onDestroy(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}
