package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static String globalUsername;
    private static DatabaseReference mDatabase;
    private static boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        //Deklarasi TextView register
        TextView register = findViewById(R.id.login_sign_up_text_view);

        //Deklarasi Button login
        Button login = findViewById(R.id.login_login_button);

        //Deklarasi EditText username
        final EditText usernameEdit = findViewById(R.id.login_username_edit_text);

        //Deklarasi EditText password
        final EditText passwordEdit = findViewById(R.id.login_password_edit_text);

        //Fungsi klik tombol login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = true;
                login();
                if (result) {
                    final String username = usernameEdit.getText().toString();
                    final String password = passwordEdit.getText().toString();

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

    private void login() {
        result = true;
        //Deklarasi EditText username
        final EditText usernameEdit = findViewById(R.id.login_username_edit_text);

        //Deklarasi EditText password
        final EditText passwordEdit = findViewById(R.id.login_password_edit_text);

        //Jika username kosong, menampilkan notif error
        if (TextUtils.isEmpty(usernameEdit.getText().toString())) {
            usernameEdit.setError("Required");
            result = false;
        } else {
            usernameEdit.setError(null);
        }

        //Jika password kosong, menampilkan notif error
        if (TextUtils.isEmpty(passwordEdit.getText().toString())) {
            passwordEdit.setError("Required");
            result = false;
        } else {
            passwordEdit.setError(null);
        }
        //Mengecek apakah username dan password yang dimasukkan sudah benar
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    key = snapshot.getKey();
                    //Jika username dan password yang dimasukkan benar
                    if (usernameEdit.getText().toString().equals(dataSnapshot.child(key).child("username").getValue(String.class)) &&
                            passwordEdit.getText().toString().equals(dataSnapshot.child(key).child("password").getValue(String.class))) {
                        //Nilai result menjadi true
                        result = true;
                        break;
                        //Jika tidak
                    } else {
                        //Nilai result menjadi false
                        result = false;
                    }
                }
                //Jika result true
                if (result) {
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    //Mengirim nama dari user
                    myIntent.putExtra("nama", dataSnapshot.child(key).child("nama").getValue(String.class));
                    //Mengirim email dari user
                    myIntent.putExtra("email", dataSnapshot.child(key).child("email").getValue(String.class));
                    //Menuju ke activity login
                    LoginActivity.this.startActivity(myIntent);
                    //Menyimpan username ke dalam variabel global
                    globalUsername = usernameEdit.getText().toString();
                    //Jika result false
                } else {
                    //Menampilkan notifikasi
                    Toast.makeText(LoginActivity.this, "Username atau password yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    //Method untuk mengambil username
    private String getGlobalUsername() {
        return globalUsername;
    }
}
