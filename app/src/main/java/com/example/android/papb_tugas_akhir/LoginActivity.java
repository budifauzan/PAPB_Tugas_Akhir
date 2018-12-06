package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //Inisialisasi class users yang menyimpan data-data para user
    users users = new users();

    //Deklarasi variabel index untuk menyimpan index dari data user
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Deklarasi TextView register
        TextView register = findViewById(R.id.login_sign_up_text_view);

        //Deklarasi Button login
        Button login = findViewById(R.id.login_login_button);

        //Deklarasi EditText username
        final EditText username = findViewById(R.id.login_username_edit_text);

        //Deklarasi EditText password
        final EditText password = findViewById(R.id.login_password_edit_text);

        //Fungsi klik tombol login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Deklarasi variabel valid untuk mengecek behasil tidaknya proses login
                boolean valid = false;

                for (int i = 0; i < users.getUsers().size(); i++) {
                    /*Apabila username dan password yang diinput cocok dengan yang ada di data user
                    maka valid akan bernilai true dan index akan bernilai i*/
                    if (username.getText().toString().equals(users.getUsers().get(i).getUsername()) &&
                            password.getText().toString().equals(users.getUsers().get(i).getPassword())) {
                        valid = true;
                        index = i;
                        break;
                    }
                }
                //Jika valid
                if (valid) {
                    //Menampilkan toast berhasil
                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);

                    //Menyimpan nama dan email dari user yang telah login
                    myIntent.putExtra("nama", users.getUsers().get(index).getNama());
                    myIntent.putExtra("email", users.getUsers().get(index).getEmail());

                    //Kemudian menuju ke activity main
                    LoginActivity.this.startActivity(myIntent);

                } else {
                    //Jika tidak valid maka menampilkan toast gagal
                    Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,Baca_Cerita.class);
                    LoginActivity.this.startActivity(intent);
                }
            }
        });

        //Apabila tulisan register diklik maka akan menuju ke activity register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }
}
