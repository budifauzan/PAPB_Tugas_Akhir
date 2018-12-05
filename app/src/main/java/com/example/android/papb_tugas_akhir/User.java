package com.example.android.papb_tugas_akhir;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username, password, email, nama;


    public User(String username, String password, String email, String nama) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNama() {
        return nama;
    }

    @Exclude
    public Map<String, Object> detailToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", password);
        result.put("email", email);
        result.put("nama", nama);
        return result;
    }

}