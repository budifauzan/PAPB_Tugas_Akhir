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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TambahCeritaActivity extends AppCompatActivity {

    Button btnSave;
    EditText edtTitle, edtContent;
    String idUser = "";
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_cerita);
        btnSave = findViewById(R.id.btn_save);
        edtTitle = findViewById(R.id.edt_add_title);
        edtContent = findViewById(R.id.edt_add_content);

        getSupportActionBar().setTitle("Tambah Cerita");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCerita(edtTitle.getText().toString(), edtContent.getText().toString());
            }
        });
    }

    private void addCerita(String title, String content){
        //disini kode untuk menambah ke firebase
        String key = mDatabase.push().getKey();
        String judul = edtTitle.getText().toString();
        String kontex = edtContent.getText().toString();

        Map<String, Object> tambahCerita = new HashMap<String, Object>();
        tambahCerita.put("/Story/" + key + "/Judul", judul);
        tambahCerita.put("/Story/" + key + "/Kontex", kontex);

        mDatabase.updateChildren(tambahCerita).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(TambahCeritaActivity.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
