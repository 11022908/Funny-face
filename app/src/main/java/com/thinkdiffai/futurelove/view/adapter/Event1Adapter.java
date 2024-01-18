package com.thinkdiffai.futurelove.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;

public class Event1Adapter extends RecyclerView.Adapter<Event1Adapter.Event1ViewHolder> implements Filterable {
    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public Event1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvEvent1Binding itemRcvEvent1Binding = ItemRcvEvent1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Event1ViewHolder(itemRcvEvent1Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Event1ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class Event1ViewHolder extends RecyclerView.ViewHolder{
        private final ItemRcvEvent1Binding itemRcvEvent1Binding;
        public Event1ViewHolder(ItemRcvEvent1Binding itemRcvEvent1Binding){
            super(itemRcvEvent1Binding.getRoot());
            this.itemRcvEvent1Binding = itemRcvEvent1Binding;
        }
    }
}
