package com.sachindra.futsalbook;

import com.sachindra.futsalbook.API.ProductsAPI;
import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.model.User;
import com.sachindra.futsalbook.response.RegisterResponse;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class UnitTest {
    @Test
    public void Test_login() {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> usersCall = usersAPI.validateLogin("samyamd", "password");
        try {
            Response<RegisterResponse> registerResponse = usersCall.execute();
            // RegisterResponse response  = registerResponse.body();

            assertTrue(registerResponse.isSuccessful());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void Test_registration() {
        User users = new User("samyam", "nepali", "android@123.com", "password",
                "user.png", 91223210);
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> registerCall = usersAPI.registerUser(users);
        try {
            Response<RegisterResponse> registerResponse = registerCall.execute();
            // RegisterResponse response  = registerResponse.body();

            assertTrue(registerResponse.isSuccessful());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void Test_Booking() {
        String token = Connection.token;
        String slug = "kookaburra-bat";
        int qty = 2;

        ProductsAPI productsAPI = Connection.getInstance().create(ProductsAPI.class);

        Call<RegisterResponse> addBooking = productsAPI.addBooking(token, slug, qty);
        try {
            Response<RegisterResponse> registerResponse = addBooking.execute();
            // RegisterResponse response  = registerResponse.body();

            assertTrue(registerResponse.isSuccessful());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void Test_ground() {
        String token = Connection.token;
        String book_time = " 6AM-7AM";
        String book_date = "2020-02-12";

        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);

        Call<RegisterResponse> bookingcall = usersAPI.groundBooking(token, book_date, book_time);

        try {
            Response<RegisterResponse> registerResponse = bookingcall.execute();

            assertTrue(registerResponse.isSuccessful());

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void Test_delete_cart() {
        String token = Connection.token;
        String id = "1234567ASD";

        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> responseCall = usersAPI.deletebooking(token, id);
        try {
            Response<RegisterResponse> registerResponse = responseCall.execute();

            assertTrue(registerResponse.isSuccessful());

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
