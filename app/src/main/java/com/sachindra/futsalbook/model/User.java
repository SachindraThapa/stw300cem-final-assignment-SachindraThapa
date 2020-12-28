package com.sachindra.futsalbook.model;

public class User {
    String name, username, email, password, profile_image;
    long phone;

    public User(String name, String username, String email, String password, String profile_image, long phone) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile_image = profile_image;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return profile_image;
    }

    public void setImage(String profile_image) {
        this.profile_image = profile_image;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
