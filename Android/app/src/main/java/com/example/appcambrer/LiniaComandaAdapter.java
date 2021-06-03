package com.example.appcambrer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inofmila.info.Model.LiniaComanda;

public class LiniaComandaAdapter extends RecyclerView.Adapter<LiniaComandaAdapter.ViewHolder> {
    List<LiniaComanda> linies;

    public LiniaComandaAdapter(List<LiniaComanda> linies)
    {
        this.linies = linies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.filalinies;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new LiniaComandaAdapter.ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiniaComanda lc = linies.get(position);
        holder.txvQuantitat.setText(String.valueOf(lc.getQuantitat()));
        holder.txvNom.setText(lc.getNomPlat());
        holder.txvPreuXPlat.setText(String.valueOf(lc.getPreu()));
        holder.txvPreuXLinia.setText(String.valueOf(lc.getPreu() * lc.getQuantitat()));
    }

    @Override
    public int getItemCount() {
        return linies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvQuantitat, txvNom, txvPreuXPlat, txvPreuXLinia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvQuantitat = itemView.findViewById(R.id.txvQuantitat);
            txvNom = itemView.findViewById(R.id.txvNomPlat);
            txvPreuXPlat = itemView.findViewById(R.id.txvPreuXPlat);
            txvPreuXLinia = itemView.findViewById(R.id.txvPreuXLinia);
        }
    }
}
