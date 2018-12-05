package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Deklarasi Button logout
        Button logout = findViewById(R.id.main_logout_button);

        //Deklarasi TextView hello
        TextView hello = findViewById(R.id.main_hello_text_view);

        //Deklarasi nama dan email untuk menerima nama dan email yang telah dikirimkan dari activity login
        String nama = getIntent().getStringExtra("nama");
        String email = getIntent().getStringExtra("email");

        //Deklarasi pesan hello
        String helloText = "Hello, " + nama + "\nEmail anda : " + email;

        //Mengatur text dari hello untuk menampilkan pesan hello
        hello.setText(helloText);

        //Fungsi klik tombol logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                //Menampilkan toast logout berhasil
                Toast.makeText(MainActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
                //Kemudian menuju ke activity login
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
