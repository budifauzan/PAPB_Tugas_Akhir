package com.example.android.papb_tugas_akhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Baca_Cerita extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView judul,body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        judul = findViewById(R.id.baca_carita_judul);
        body =findViewById(R.id.baca_cerita_konten);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca__cerita);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference myref = database.getReference("Story");



        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                judul = findViewById(R.id.baca_carita_judul);
                body =findViewById(R.id.baca_cerita_konten);
                String  judulString= dataSnapshot.child("id").child("Judul").getValue(String.class);
                String  kontenString = dataSnapshot.child("id").child("Kontex").getValue(String.class);
                judul.setText(judulString);
                body.setText(kontenString);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Baca_Cerita.this,"TERJADI GANGUAN KONEKSI",Toast.LENGTH_LONG).show();
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}
