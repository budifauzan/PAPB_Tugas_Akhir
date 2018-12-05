package com.example.android.papb_tugas_akhir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListCerita> listCeritas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        recyclerView = (RecyclerView) findViewById(R.id.recV_cerita);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listCeritas =  new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ListCerita listCerita = new ListCerita(
                    "title " + (i+1),
                    "TEs dulu ini overvirew"
            );
            listCeritas.add(listCerita);
        }

        adapter = new CeritaAdapter(listCeritas,this);
        recyclerView.setAdapter(adapter);


    }
}

