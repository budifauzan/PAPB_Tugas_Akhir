package com.example.android.papb_tugas_akhir;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alex on 12/5/2018.
 */

public class CeritaAdapter extends RecyclerView.Adapter<CeritaAdapter.ViewHolder> {
    List<ListCerita> listCerita;
    private Context context;

    public CeritaAdapter(List<ListCerita> listCerita, Context context) {
        this.listCerita = listCerita;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cerita,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListCerita list =listCerita.get(position);

        holder.title.setText(list.getTitle());
        holder.overview.setText(list.getOverview());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked "+list.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCerita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title, overview;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            overview= (TextView) itemView.findViewById(R.id.overview);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

}
