package com.example.appcambrer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inofmila.info.Model.Plat;

public class PlatsAdapter extends RecyclerView.Adapter<PlatsAdapter.ViewHolder> {
    List<Plat> plats;


    public PlatsAdapter(List<Plat> plats)
    {
        this.plats = plats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.filaplats;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plat p = plats.get(position);
        holder.txvNom.setText(p.getNom());
        holder.txvPreu.setText(String.valueOf(p.getPreu()));
        //Gestio imatge
        Bitmap bmp = BitmapFactory.decodeByteArray(p.getStreamFoto(), 0, p.getStreamFoto().length);
        holder.imvPlat.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return plats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnmenos, btnmas;
        TextView txvPreu, txvNom;
        ImageView imvPlat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnmenos = itemView.findViewById(R.id.btnMenos);
            btnmas = itemView.findViewById(R.id.btnMas);
            txvNom = itemView.findViewById(R.id.txvNomPlat);
            txvPreu = itemView.findViewById(R.id.txvPreu);
            imvPlat = itemView.findViewById(R.id.imvPlat);
        }
    }
}
