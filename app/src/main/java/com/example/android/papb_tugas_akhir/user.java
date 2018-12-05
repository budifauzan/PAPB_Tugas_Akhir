package com.example.android.papb_tugas_akhir;

public class user {
    private String username, password, email, nama;

    user(String username, String password, String email, String nama) {
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
}
