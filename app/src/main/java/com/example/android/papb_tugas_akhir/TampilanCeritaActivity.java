package com.example.android.papb_tugas_akhir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TampilanCeritaActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private TextView title, content;
    private Button bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_cerita);
        title = (TextView) findViewById(R.id.txt_title);
        content = (TextView) findViewById(R.id.txt_content);
        bookmark = (Button) findViewById(R.id.btn_bookmark);

        title.setText(getIntent().getStringExtra("title"));
        content.setText(getIntent().getStringExtra("content"));

        db=FirebaseDatabase.getInstance();
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getReference("bookmark").child("idUSer").child(getIntent().getStringExtra("id")).setValue("");
                Intent intent = new Intent(TampilanCeritaActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });



    }
}
