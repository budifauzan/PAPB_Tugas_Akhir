package com.example.android.papb_tugas_akhir;

import java.util.ArrayList;

public class users {
    private static ArrayList<user> users = new ArrayList<>();

    users() {
        users.add(new user("mbuud", "123", "budifauzan1997@gmail.com",
                "Muhammad Budi Fauzan"));
        users.add(new user("tes", "123", "tes@gmail.com",
                "Data Testing"));
    }

    public ArrayList<user> getUsers() {
        return users;
    }
}
