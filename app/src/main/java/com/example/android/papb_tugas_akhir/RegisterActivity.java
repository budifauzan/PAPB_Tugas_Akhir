package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //Inisialisasi class users yang menyimpan data-data para user
    users users = new users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Deklarasi Button register
        Button register = findViewById(R.id.register_register_button);

        //Deklarasi TextView login
        TextView login = findViewById(R.id.register_login_text_view);

        //Deklarasi EditText username
        final EditText username = findViewById(R.id.register_username_edit_text);

        //Deklarasi EditText nama
        final EditText nama = findViewById(R.id.register_nama_edit_text);

        //Deklarasi EditText email
        final EditText email = findViewById(R.id.register_email_edit_text);

        //Deklarasi EditText password
        final EditText password = findViewById(R.id.register_password_edit_text);

        //Fungsi klik tombol register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Deklarasi variabel valid untuk menentukan berhasil tidaknya proses register
                boolean valid = true;

                for (int i = 0; i < users.getUsers().size(); i++) {
                    //Apabila username sudah ada di data user, maka valid == false
                    if (username.getText().toString().equalsIgnoreCase(users.getUsers().get(i).getUsername())) {
                        valid = false;
                        break;
                    }
                }
                //Jika valid
                if (valid) {
                    // maka menyimpan data yang telah diinput ke dalam data user
                    users.getUsers().add(new user(username.getText().toString(), password.getText().toString(), email
                            .getText().toString(), nama.getText().toString()));
                    //Lalu menampilkan toast berhasil
                    Toast.makeText(RegisterActivity.this, "Register berhasil", Toast.LENGTH_SHORT).show();

                    //Kemudian menuju ke activity login
                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                } else {
                    //Jika tidak valid, maka menampilkan toast gagal
                    Toast.makeText(RegisterActivity.this, "Username sudah dipakai mamang",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Apabila tulisan login diklik maka akan menuju ke halaman login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            }
        });
    }
}
