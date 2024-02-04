package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.model.DetailEvent;

public class DetailEventAdapter extends RecyclerView.Adapter<DetailEventAdapter.ViewHolder> {
    private DetailEvent detailEvent;
    private String urlImgMale;
    private String urlImgFemale;
    private Context context;
    private String name_user;

    public DetailEventAdapter(DetailEvent detailEvent, String name_user, Context context) {
        this.detailEvent = detailEvent;
        this.context = context;
        this.name_user = name_user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvEvent1Binding itemBinding = ItemRcvEvent1Binding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailEventAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.tvTenSuKien.setText(detailEvent.getTenSuKien());
        holder.itemBinding.tvUserName.setText(name_user);
        holder.itemBinding.tvCommentNumber.setText(String.valueOf(detailEvent.getCountComment()));
        holder.itemBinding.tvEventDetail.setText(detailEvent.getNoiDungSuKien());
        holder.itemBinding.tvViewNumber.setText(String.valueOf(detailEvent.getCountView()));

        if(detailEvent.getLinkNamGoc() != null || detailEvent.getLinkNuGoc() != null){
            String link_avatar = detailEvent.getLinkNamGoc();
            String strReplace = link_avatar.substring(0, 25);
            String rs = link_avatar.replace(strReplace, "https://futurelove.online");
            Glide.with(holder.itemView.getContext()).load(rs).into(holder.itemBinding.avatarImageView);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemRcvEvent1Binding itemBinding;
        public ViewHolder(ItemRcvEvent1Binding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
