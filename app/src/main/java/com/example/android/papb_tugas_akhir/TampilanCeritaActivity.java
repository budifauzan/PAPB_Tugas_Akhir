package com.example.android.papb_tugas_akhir;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TampilanCeritaActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private TextView title, content;
    private Button bookmark;
    String userId = "idUSer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_cerita);
        title = (TextView) findViewById(R.id.txt_title);
        content = (TextView) findViewById(R.id.txt_content);
        bookmark = (Button) findViewById(R.id.btn_bookmark);

//        title.setText(getIntent().getStringExtra("title"));
//        content.setText(getIntent().getStringExtra("content"));
        db = FirebaseDatabase.getInstance();
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

//                db.getReference("bookmark").child("idUSer").child(getIntent().getStringExtra("id")).setValue("");

                final DatabaseReference myref = db.getReference("bookmark");

                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {

                            if (dataSnapshot.hasChild(userId)) {
                                if (dataSnapshot.child(userId).hasChild(getIntent().getStringExtra("id"))) {
                                    dataSnapshot.child(userId).child(getIntent().getStringExtra("id")).getRef().removeValue();
                                } else {
                                    myref.child(userId).child(getIntent().getStringExtra("id")).setValue("");
                                }
                            } else {
                                myref.child(userId).child(getIntent().getStringExtra("id")).setValue("");
                            }

                        } catch (Exception E) {
                            Toast.makeText(TampilanCeritaActivity.this, "Eror" + E.toString(), Toast.LENGTH_SHORT).show();
                        }
                        setBookmarkIcon();
                        myref.removeEventListener(this);
                        Intent intent = new Intent(TampilanCeritaActivity.this, BookmarkActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(TampilanCeritaActivity.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                        // Failed to read value
                        Log.w("Firebase", "Failed to read value.", error.toException());
                    }
                });
            }


        });
        setBookmarkIcon();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setBookmarkIcon();
        DatabaseReference myref = db.getReference("Story");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String judulString = dataSnapshot.child(getIntent().getStringExtra("id")).child("Judul").getValue(String.class);
                String kontenString = dataSnapshot.child(getIntent().getStringExtra("id")).child("Kontex").getValue(String.class);
                title.setText(judulString);
                content.setText(kontenString);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(TampilanCeritaActivity.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

    public void setBookmarkIcon() {
        final DatabaseReference myref = db.getReference("bookmark");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.child("idUSer").hasChild(getIntent().getStringExtra("id"))) {

                        bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
                    } else {
                        bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp));
                    }

                } catch (Exception E) {
                    Toast.makeText(TampilanCeritaActivity.this, "Eror" + E.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(TampilanCeritaActivity.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}
