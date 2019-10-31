package com.cvars.ScotiaTracker.networkAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A static class that holds a static Retrofit factory object for network API construction
 */
public class RetrofitNetwork {

    /**
     * Base URL of the connection, which will be in Heroku
     */
    private static String BASE_URL = "https://cvars.herokuapp.com/";

    /**
     * Static factory Retrofit object
     */
    public static Retrofit retrofit;

    /*
     Initializing the factory
     */
    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


        retrofit = new Retrofit.Builder().baseUrl(RetrofitNetwork.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
