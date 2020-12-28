package com.sachindra.futsalbook.ui.menu;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sachindra.futsalbook.API.UsersAPI;
import com.sachindra.futsalbook.AboutUsActivity;
import com.sachindra.futsalbook.Connection.Connection;
import com.sachindra.futsalbook.MainActivity;
import com.sachindra.futsalbook.ProfileActivity;
import com.sachindra.futsalbook.SearchPlaces;
import com.sachindra.futsalbook.channel.NotifyChannel;
import com.sachindra.futsalbook.model.User;
import com.sachindra.futsalbook.R;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    CircleImageView image;
    Button profile, about, contact, partner, logout;
    String imagePath = Connection.imagePath;
    public static String toke;
    NotificationManagerCompat notificationManagerCompat;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        image = view.findViewById(R.id.menu_image);

        if (!Connection.token.isEmpty()) {
            toke = Connection.token;

            UsersAPI usersAPI = Connection.getInstance().create(UsersAPI.class);
            Call<User> userCall = usersAPI.getUser(toke);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Code " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User data = response.body();

                    String path = imagePath + data.getImage();
                    try {
                        URL url = new URL(path);
                        image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Please Login First", Toast.LENGTH_SHORT).show();
        }
        profile = view.findViewById(R.id.menu_profile);
        about = view.findViewById(R.id.menu_about);
        contact = view.findViewById(R.id.menu_contact);
        partner = view.findViewById(R.id.menu_partner);
//        about = view.findViewById( R.id.tkn_email );

        logout = view.findViewById(R.id.menu_logout);

        logout.setOnClickListener(this);
        profile.setOnClickListener(this);
        about.setOnClickListener(this);
        contact.setOnClickListener(this);

        notificationManagerCompat=NotificationManagerCompat.from(getActivity());
        NotifyChannel channel=new NotifyChannel(getActivity());
        channel.CreateChannel();
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menu_profile:
                intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity((intent));
                return;

            case R.id.menu_about:
                intent = new Intent(getActivity(), SearchPlaces.class);
                startActivity((intent));
                return;

            case R.id.menu_contact:
//                intent = new Intent(getActivity(), ContactActivity.class);
//                startActivity((intent));
                return;

            case R.id.menu_partner:
//                intent = new Intent(getActivity(), PartnerActivity.class);
//                startActivity((intent));
                return;

            case R.id.menu_logout:
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username", "");
                editor.putString("password", "");
                editor.apply();
                Connection.token = "Bearer ";
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                return;
        }
    }
}
