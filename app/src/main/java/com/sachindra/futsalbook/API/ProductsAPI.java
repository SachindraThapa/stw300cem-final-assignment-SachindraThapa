package com.sachindra.futsalbook.API;

import com.sachindra.futsalbook.model.Product;
import com.sachindra.futsalbook.response.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductsAPI {

    @GET("product")
    Call<List<Product>> allProducts();

    @FormUrlEncoded
    @POST("product/{slug}/cart")
    Call<RegisterResponse> addBooking(@Header("Authorization") String token, @Path("slug") String slug, @Field("quantity") int qty);

    @GET("user/grounds")
    Call<List<Product>> myBooking(@Header("Authorization") String token);

}
