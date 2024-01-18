package com.thinkdiffai.futurelove.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.ItemCommentBinding;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.model.Comment;
import com.thinkdiffai.futurelove.model.comment.EventsUser.SukienX;

import java.util.List;

public class EventDetailAdapterCustom extends RecyclerView.Adapter<EventDetailAdapterCustom.EventDetailViewHolder>{

    private List<SukienX> sukienX;
    private List<Comment> comments;

    public EventDetailAdapterCustom(List<SukienX> sukienX, List<Comment> comments) {
        this.sukienX = sukienX;
        this.comments = comments;
    }




    @NonNull
    @Override
    public EventDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvEvent1Binding itemRcvEvent1Binding=ItemRcvEvent1Binding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        ItemCommentBinding itemCommentBinding=ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new EventDetailAdapterCustom.EventDetailViewHolder(itemRcvEvent1Binding, itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventDetailViewHolder holder, int position) {
        SukienX a=sukienX.get(position);
        holder.itemBinding.tvTenSuKien.setText(a.getTen_su_kien());
        holder.itemBinding.tvUserName.setText(a.getTen_nam());
        holder.itemBinding.tvCommentNumber.setText(String.valueOf(a.getCount_comment()));
        holder.itemBinding.tvEventDetail.setText(a.getNoi_dung_su_kien());
        holder.itemBinding.tvViewNumber.setText(String.valueOf(a.getCount_view()));
        holder.itemBinding.tvDate.setText(a.getReal_time());
        if (a.getLink_nam_goc() != null && !a.getLink_nam_goc().isEmpty() && a.getLink_nu_goc() != null && !a.getLink_nu_goc().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(a.getLink_nam_goc()).error(R.drawable.baseline_account_circle_24).into(holder.itemBinding.avatarImageView);
//            Glide.with(holder.itemView.getContext()).load(comment.getLinkNuGoc()).error(R.drawable.baseline_account_circle_24).into(holder.itemCommentBinding.imageAvatar2);
        }
//        else if (urlImgFemale != null && !urlImgFemale.isEmpty() && urlImgMale != null && !urlImgMale.isEmpty()) {
//            Glide.with(holder.itemView.getContext()).load(urlImgMale).error(R.drawable.baseline_account_circle_24).into(holder.itemBinding.avatarImageView);
////            Glide.with(holder.itemView.getContext()).load(urlImgFemale).error(R.drawable.baseline_account_circle_24).into(holder.itemCommentBinding.imageAvatar2);
//        }


    }



    @Override
    public int getItemCount() {
        return null == sukienX ? 0 : sukienX.size();
    }

    public static class EventDetailViewHolder extends RecyclerView.ViewHolder {
        private  final ItemRcvEvent1Binding itemBinding;
        private final ItemCommentBinding itemCommentBinding;
        public EventDetailViewHolder(ItemRcvEvent1Binding itemBinding, ItemCommentBinding itemCommentBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.itemCommentBinding = itemCommentBinding;
        }
    }
}
