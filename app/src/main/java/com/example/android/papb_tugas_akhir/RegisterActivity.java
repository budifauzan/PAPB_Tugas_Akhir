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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static DatabaseReference mDatabase;
    private static boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference("user");

        //Deklarasi Button register
        Button register = findViewById(R.id.register_register_button);

        //Deklarasi TextView login
        TextView login = findViewById(R.id.register_login_text_view);

        //Deklarasi EditText username
        final EditText usernameEdit = findViewById(R.id.register_username_edit_text);

        //Deklarasi EditText password
        final EditText passwordEdit = findViewById(R.id.register_password_edit_text);

        //Deklarasi EditText email
        final EditText emailEdit = findViewById(R.id.register_email_edit_text);

        //Deklarasi EditText nama
        final EditText namaEdit = findViewById(R.id.register_nama_edit_text);

        //Fungsi klik tombol register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        //Apabila tulisan login diklik maka akan menuju ke halaman login
        login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            }
        });

    }

    /**
     * @param username dari field username
     * @param password dari field password
     * @param email    dari field email
     * @param nama     dari field nama
     */
    private static void createNewUser(String username, String password, String email, String nama) {
        //Memnginisialisasi object user
        User user = new User(username, password, email, nama);

        //Mendeklarasi variabel key
        String key = mDatabase.push().getKey();

        //Menyimpan data user kedalam HashMap
        Map<String, Object> userDetail = user.detailToMap();

        //Menginisalisasi HashMap
        Map<String, Object> childUpdates = new HashMap<>();

        //Menambakan data user
        childUpdates.put(key, userDetail);


        //Mengupdate child dari database
        mDatabase.updateChildren(childUpdates);
    }


    //Method untuk validasi form
    private void register() {
        result = true;
        //Deklarasi EditText password
        final EditText passwordEdit = findViewById(R.id.register_password_edit_text);

        //Deklarasi EditText email
        final EditText emailEdit = findViewById(R.id.register_email_edit_text);

        //Deklarasi EditText nama
        final EditText namaEdit = findViewById(R.id.register_nama_edit_text);

        //Deklarasi EditText confirm password
        final EditText confirmPasswordEdit = findViewById(R.id.register_confirm_password_edit_text);

        //Deklarasi EditText username
        final EditText usernameEdit = findViewById(R.id.register_username_edit_text);

        //Jika username kosong, menampilkan notif error
        if (TextUtils.isEmpty(usernameEdit.getText().toString())) {
            usernameEdit.setError("Required");
            result = false;
        } else {
            usernameEdit.setError(null);
            //Mengecek apakah username sudah ada di firebase atau belum
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        //Jika sudah ada maka menampilkan error pada editText
                        if (dataSnapshot.child(key).child("username").getValue(String.class).equalsIgnoreCase(usernameEdit.getText().toString())) {
                            usernameEdit.setError("Username already used");
                            result = false;
                        }
                    }
                    //Jika tidak ada
                    if (result) {
                        //Menambahkan data user ke firebase
                        createNewUser(usernameEdit.getText().toString(), passwordEdit.getText().toString(),
                                emailEdit.getText().toString(), namaEdit.getText().toString());
                        //Menampilkan notif register berhasil
                        Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                        //Menuju ke activity login
                        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(myIntent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }

        //Jika password kosong, menampilkan notif error
        if (TextUtils.isEmpty(passwordEdit.getText().toString())) {
            passwordEdit.setError("Required");
            result = false;
        } else {
            passwordEdit.setError(null);
        }

        //Jika email kosong, menampilkan notif error
        if (TextUtils.isEmpty(emailEdit.getText().toString())) {
            emailEdit.setError("Required");
            result = false;
        } else {
            emailEdit.setError(null);
        }

        //Jika nama kosong, menampilkan notif error
        if (TextUtils.isEmpty(namaEdit.getText().toString())) {
            namaEdit.setError("Required");
            result = false;
        } else {
            namaEdit.setError(null);
        }

        //Jika kolom password dan confirm password tidak cocok, menampilkan notif error
        if (!confirmPasswordEdit.getText().toString().equals(passwordEdit.getText().toString())) {
            confirmPasswordEdit.setError("Password isn't match");
            result = false;
        } else {
            confirmPasswordEdit.setError(null);
        }

    }
}
