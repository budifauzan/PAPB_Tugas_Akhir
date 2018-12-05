package com.example.android.papb_tugas_akhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    private List<ListCerita> listCeritas=new ArrayList<ListCerita>();
    private ArrayList<String> idBookmark = new ArrayList<>();
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        recyclerView = (RecyclerView) findViewById(R.id.recV_bookmark);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db = FirebaseDatabase.getInstance();
        db.getReference("bookmark").child("idUSer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    idBookmark.add(sn.getKey());

                }
                for (int i = 0; i < idBookmark.size(); i++) {
                    Log.d("bookmark", idBookmark.get(i));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        db.getReference("Story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                listCeritas.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    for (int i = 0; i < idBookmark.size(); i++) {
                        Log.d("sn", sn.getKey());
                        if (sn.getKey().equalsIgnoreCase(idBookmark.get(i))){
                            listCeritas.add( new ListCerita(sn.getKey().toString(),sn.child("Judul").getValue().toString(),sn.child("Kontex").getValue().toString()));
                            Log.d("coba log", "onDataChange: "+idBookmark.get(i));
                        }
                    }

                }
                adapter = new CeritaAdapter(listCeritas, BookmarkActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
