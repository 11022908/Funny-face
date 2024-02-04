package com.thinkdiffai.futurelove.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.model.DetailEvent;
import com.thinkdiffai.futurelove.model.DetailEventList;

import java.util.List;

public class SetNameUserAdapter extends RecyclerView.Adapter<SetNameUserAdapter.ViewHolder>{
    private List<Integer> listId;

    public SetNameUserAdapter(String tenNam) {
        this.tenNam = tenNam;
    }
    String tenNam;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvEvent1Binding itemBinding = ItemRcvEvent1Binding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SetNameUserAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return null == listId ? 0 : listId.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemRcvEvent1Binding itemBinding;
        public ViewHolder(ItemRcvEvent1Binding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
