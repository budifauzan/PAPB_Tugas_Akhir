package com.example.android.papb_tugas_akhir;

import android.content.Context;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Baca_Cerita extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView judul, body;
    Button bookmark;
    private String userId = "id", storyId = "11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(savedInstanceState.get("idStory")!=null){
//            storyId=(String) savedInstanceState.get("idStory");
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baca__cerita);
        judul = findViewById(R.id.baca_carita_judul);
        body = findViewById(R.id.baca_cerita_konten);
        bookmark = findViewById(R.id.activity_baca_bookmark);
        setBookmarkIcon();
    }


    public void setBookmarkIcon() {
        final DatabaseReference myref = database.getReference("bookmark");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.hasChild(userId)) {
                        if (dataSnapshot.child(userId).hasChild(storyId)) {
                            bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
                        } else {
                            bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp));
                        }
                    } else {
                        bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp));
                    }

                } catch (Exception E) {
                    Toast.makeText(Baca_Cerita.this, "Eror" + E.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Baca_Cerita.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference myref = database.getReference("Story");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                judul = findViewById(R.id.baca_carita_judul);
                body = findViewById(R.id.baca_cerita_konten);
                String judulString = dataSnapshot.child("id").child("Judul").getValue(String.class);
                String kontenString = dataSnapshot.child("id").child("Kontex").getValue(String.class);
                judul.setText(judulString);
                body.setText(kontenString);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Baca_Cerita.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference myref = database.getReference("bookmark");
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.hasChild(userId)) {
                                if (dataSnapshot.child(userId).hasChild(storyId)) {
                                    dataSnapshot.child(userId).child(storyId).getRef().removeValue();
                                    bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp));
                                    Toast.makeText(Baca_Cerita.this, "Berhasil Dihapus dari Bookmark", Toast.LENGTH_SHORT);
                                } else {
                                    bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
                                    myref.child(userId).child(storyId).setValue(true);
                                    Toast.makeText(Baca_Cerita.this, "Berhasil Ditambahkan Ke Bookmark", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                myref.child(userId).child(storyId).setValue(true);
                                bookmark.setBackground(getResources().getDrawable(R.drawable.ic_bookmark_black_24dp));
                                Toast.makeText(Baca_Cerita.this, "Berhasil Ditambahkan Ke Bookmark", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception E) {
                            Toast.makeText(Baca_Cerita.this, "Eror" + E.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(Baca_Cerita.this, "TERJADI GANGUAN KONEKSI", Toast.LENGTH_LONG).show();
                        // Failed to read value
                        Log.w("Firebase", "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}

class bookMark {
    String id;
    boolean isBookMark;
}
