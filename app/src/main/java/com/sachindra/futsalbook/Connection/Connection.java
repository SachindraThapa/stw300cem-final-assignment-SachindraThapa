package com.sachindra.futsalbook.Connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connection {
    // public static final String host = "http://192.168.1.11:3000/";
    public static final String host = "http://10.0.2.2:3030/";
    // public static final String host = "http://172.100.100.5:3000/";
    public static String token = "Bearer ";
    public static final String apiKey = "AIzaSyBn0mkeXG0AQeuQIIalyJlRfnrefNuB8NE";
    public static final String imagePath = host + "uploads/users/";
    public static final String priductImage = host + "uploads/products/";

    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
