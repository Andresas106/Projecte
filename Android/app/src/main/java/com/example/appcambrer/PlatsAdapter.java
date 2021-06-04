package com.example.appcambrer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import inofmila.info.Model.LiniaComanda;
import inofmila.info.Model.Plat;
import inofmila.info.Model.Taula;

public class PlatsAdapter extends RecyclerView.Adapter<PlatsAdapter.ViewHolder> {
    List<Plat> plats;
    int taula;
    List<LiniaComanda> linies = ComandaActivity.linies;
    LiniaComandaAdapter liniesAdapter;
    RecyclerView rcvLiniesComanda;
    Context c;


    public PlatsAdapter(List<Plat> plats, int taula, LiniaComandaAdapter liniesAdapter, RecyclerView rcvLiniesComanda,
                        Context c)
    {
        this.plats = plats;
        this.taula = taula;
        this.liniesAdapter = liniesAdapter;
        this.rcvLiniesComanda = rcvLiniesComanda;
        this.c = c;
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

        holder.btnmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long codiPlat = p.getCodi();
                boolean trobat = false;
                LiniaComanda lc = null;
                int posicio = 0;
                for(int i=0;i<linies.size();i++)
                {
                    if(codiPlat == linies.get(i).getCodiPlat())
                    {
                        posicio = i;
                        trobat = true;

                    }
                }

                if(trobat)
                {
                    linies.get(posicio).setQuantitat(linies.get(posicio).getQuantitat() - 1);
                    if(linies.get(posicio).getQuantitat() == 0)
                    {
                        linies.remove(linies.get(posicio));
                    }
                }else
                {
                    //No sha de fer res
                }

                LinearLayoutManager manager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
                rcvLiniesComanda.setLayoutManager(manager);
                liniesAdapter = new LiniaComandaAdapter(linies);
                rcvLiniesComanda.setAdapter(liniesAdapter);


            }
        });

        holder.btnmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long codiPlat = p.getCodi();
                boolean trobat = false;
                LiniaComanda lc = null;
                int posicio = 0;
                for(int i=0;i<linies.size();i++)
                {
                    if(codiPlat == linies.get(i).getCodiPlat())
                    {
                        posicio = i;
                        trobat = true;

                    }
                }

                if(trobat)
                {
                    linies.get(posicio).setQuantitat(linies.get(posicio).getQuantitat() + 1);
                }else
                {
                    linies.add(new LiniaComanda(linies.size() + 1, 1, p.getCodi(),
                            p.getNom(), p.getPreu()));
                }

                LinearLayoutManager manager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
                rcvLiniesComanda.setLayoutManager(manager);
                liniesAdapter = new LiniaComandaAdapter(linies);
                rcvLiniesComanda.setAdapter(liniesAdapter);
            }
        });
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
