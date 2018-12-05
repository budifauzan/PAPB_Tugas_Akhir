package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public static String usernameGlobal;
    private static DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                final String username = usernameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (dataSnapshot.child("user").child("usernameList").hasChild(username) &&
                                    password.equals(dataSnapshot.child("user").child("usernameList").child(username).child("password").getValue(String.class))) {
                                Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                                myIntent.putExtra("nama", dataSnapshot.child("user").child("usernameList").child(username).child("nama").getValue(String.class));
                                myIntent.putExtra("email", dataSnapshot.child("user").child("usernameList").child(username).child("email").getValue(String.class));
                                LoginActivity.this.startActivity(myIntent);
                                usernameGlobal = username;
                                break;
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
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
