package com.example.appcambrer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import inofmila.info.Model.Categoria;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>  {
    List<Categoria> categories;
    SelectedItemListenerCategoria listener;

    public CategoriesAdapter(List<Categoria> categories, SelectedItemListenerCategoria listener)
    {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.filacategories;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria c = categories.get(position);
        holder.txvNomCategoria.setText(c.getNom());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNomCategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNomCategoria = itemView.findViewById(R.id.txvNomCategoria);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        listener.OnSelectedItem(categories.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
