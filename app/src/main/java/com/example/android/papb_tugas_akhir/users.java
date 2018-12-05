package com.example.android.papb_tugas_akhir;

import java.util.ArrayList;

public class users {
    private static ArrayList<User> Users = new ArrayList<>();

    users() {
        Users.add(new User("mbuud", "123", "budifauzan1997@gmail.com",
                "Muhammad Budi Fauzan"));
        Users.add(new User("tes", "123", "tes@gmail.com",
                "Data Testing"));
    }

    public ArrayList<User> getUsers() {
        return Users;
    }
}
