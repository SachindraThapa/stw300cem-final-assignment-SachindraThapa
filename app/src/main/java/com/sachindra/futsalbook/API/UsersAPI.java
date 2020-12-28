package com.sachindra.futsalbook.API;

import com.sachindra.futsalbook.model.BookingModel;
import com.sachindra.futsalbook.model.CartModel;
import com.sachindra.futsalbook.model.PriceModel;
import com.sachindra.futsalbook.response.ImageUploadResponse;
import com.sachindra.futsalbook.model.User;
import com.sachindra.futsalbook.response.RegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface UsersAPI {

    @FormUrlEncoded
    @POST("user/login")
    Call<RegisterResponse> validateLogin(@Field("email") String email, @Field("password") String password);

    @POST("user/register")
    Call<RegisterResponse> registerUser(@Body User users);

    @Multipart
    @POST("user/upload")
    Call<ImageUploadResponse> uploadImage(@Part MultipartBody.Part image);

    @GET("user/me")
    Call<User> getUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @PATCH("user/me/")
    Call<RegisterResponse> updateDetails(@Header("Authorization") String token, @Field("name") String name, @Field("email") String email, @Field("phone") long phone);

    @FormUrlEncoded
    @PATCH("user/me/image")
    Call<ImageUploadResponse> updateImage(@Header("Authorization") String token, @Field("image") String image);

    @FormUrlEncoded
    @PATCH("user/me/password")
    Call<RegisterResponse> updatePassword(@Header("Authorization") String token, @Field("password") String password, @Field("newpassword") String newpassword);

    @FormUrlEncoded
    @POST("user/ground-booking")
    Call<RegisterResponse> groundBooking(@Header("Authorization") String token, @Field("date") String date, @Field("time") String time);

    @GET("user/price")
    Call<List<PriceModel>> getPrice();

    @GET("user/ground-booking")
    Call<List<BookingModel>> allBooking();

    @GET("user/grounds")
    Call<List<BookingModel>> myBooking(@Header("Authorization") String token);

    @GET("user/carts")
    Call<List<CartModel>> myCarts(@Header("Authorization") String token);
    @DELETE("user/carts/{id}")
    Call<RegisterResponse> deletecart(@Header("Authorization") String token,@Path("id") String id);
    @DELETE("user/grounds/{id}")
    Call<RegisterResponse> deletebooking(@Header("Authorization") String token,@Path("id") String id);

}
