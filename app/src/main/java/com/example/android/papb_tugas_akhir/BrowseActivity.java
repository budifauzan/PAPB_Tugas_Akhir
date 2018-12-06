package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListCerita> listCeritas=new ArrayList<ListCerita>();
    private FirebaseDatabase db;
    private FloatingActionButton fab;
    private String idUser = "dummyIdUser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        recyclerView = (RecyclerView) findViewById(R.id.recV_cerita);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            //asdfasdfasdf
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrowseActivity.this, TambahCeritaActivity.class);
                intent.putExtra("idUser", idUser); // harusnya disini ambil iduser dari login
                startActivity(intent);
            }
        });


        db = FirebaseDatabase.getInstance();
        db.getReference("Story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                listCeritas.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    listCeritas.add( new ListCerita(sn.getKey().toString(),sn.child("Judul").getValue().toString(),sn.child("Kontex").getValue().toString()));
                    Log.d("coba log", "onDataChange: "+sn.child("Judul").getValue().toString());
                }
                adapter = new CeritaAdapter(listCeritas, BrowseActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

