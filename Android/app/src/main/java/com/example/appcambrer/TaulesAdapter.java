package com.example.appcambrer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inofmila.info.Model.Taula;

public class TaulesAdapter extends RecyclerView.Adapter<TaulesAdapter.ViewHolder> {
    List<Taula> taules;
    SelectedItemListener listener;
    int idxSeleccionat = -1;

    public TaulesAdapter(List<Taula> taules, SelectedItemListener listener)
    {
        this.taules = taules;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View filaView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filataules, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Taula t = taules.get(position);
        if(t.getCodi() == -1)
        {
            holder.txvNumero.setText(String.valueOf(t.getNumero()));
            holder.txvNom.setText("");
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.prbPlatsPrearats.setVisibility(View.INVISIBLE);
        }else
        {
            holder.txvNumero.setText(String.valueOf(t.getNumero()));
            holder.txvNom.setText(t.getNomCambrer());
            if(t.isEs_meva())
            {
                holder.prbPlatsPrearats.setVisibility(View.VISIBLE);
                holder.prbPlatsPrearats.setMax(t.getNumPlats());
                holder.prbPlatsPrearats.setProgress(t.getPlatsPreparats());
                holder.itemView.setBackgroundColor(Color.GREEN);
            }else
            {
                holder.itemView.setBackgroundColor(Color.GRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return taules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView txvNumero;
        public TextView txvNom;
        public ProgressBar prbPlatsPrearats;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNumero = itemView.findViewById(R.id.txvNumTaula);
            txvNom = itemView.findViewById(R.id.txvNomCambrer);
            prbPlatsPrearats = itemView.findViewById(R.id.prbPlatsPreparats);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int anticIdxSeleccionat = idxSeleccionat;
            idxSeleccionat = getAdapterPosition();
            notifyItemChanged(anticIdxSeleccionat);
            notifyItemChanged(idxSeleccionat);
            if(listener != null)
            {
                listener.onSelectedItem(taules.get(idxSeleccionat));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int anticIdxSeleccionat = idxSeleccionat;
            idxSeleccionat = getAdapterPosition();
            notifyItemChanged(anticIdxSeleccionat);
            notifyItemChanged(idxSeleccionat);
            if(listener != null)
            {
                listener.onLongSelectedItem(taules.get(idxSeleccionat), idxSeleccionat);
            }
            return true;
        }
    }
}
