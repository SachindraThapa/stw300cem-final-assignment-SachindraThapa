package com.sachindra.futsalbook.interfaces;

import android.widget.Toast;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.response.RegisterResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginInterface {

    boolean isSuccess = false;
    //
    public boolean loginData(String username, String password) {
        UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> usersCall = usersAPI.validateLogin(username, password);

        try {
            Response<RegisterResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Successful")) {

                Connection.token += loginResponse.body().getToken();
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
