package com.example.kiemtragiuaky.model;
import com.google.gson.annotations.SerializedName;


public class DangNhapModel {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;
    @SerializedName("nameRole")
    private String nameRole;

    public DangNhapModel() {
    }

    public DangNhapModel(String username, String password, String nameRole) {
        this.username = username;
        this.password = password;
        this.nameRole = nameRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameRole() {
        return nameRole;
    }

    @Override
    public String toString() {
        return "DangNhapModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
