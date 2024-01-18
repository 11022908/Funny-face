package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.FragmentCommentBinding;
import com.thinkdiffai.futurelove.databinding.ItemCommentBinding;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent1Binding;
import com.thinkdiffai.futurelove.databinding.ItemRcvEvent3Binding;
import com.thinkdiffai.futurelove.model.Comment;
import com.thinkdiffai.futurelove.model.comment.CommentPage;
import com.thinkdiffai.futurelove.model.comment.EventsUser.SukienX;
import com.thinkdiffai.futurelove.view.fragment.CommentFragment;

import java.util.List;


public class EventFragmentEventAdapter extends RecyclerView.Adapter<EventFragmentEventAdapter.EventFragmentViewHolder>{
    private List<SukienX> list;
    private List<CommentPage> comments;
    private String urlImgMale;
    private String urlImgFemale;
    private Comment comment;
    Context context;

    public EventFragmentEventAdapter(List<SukienX> list, List<Comment> comment, Context context) {
        this.list = list;
        this.context = context;
//        this.comments = comment;
    }
    public void SetData(List<SukienX> x){
            this.list=x;
    }
    @NonNull
    @Override
    public EventFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRcvEvent1Binding itemRcvEvent1Binding=ItemRcvEvent1Binding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        ItemCommentBinding itemCommentBinding=ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new EventFragmentViewHolder(itemRcvEvent1Binding, itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFragmentViewHolder holder, int position) {
        SukienX a=list.get(position);
//        Comment cmt = comments.get(position);
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
        else if (urlImgFemale != null && !urlImgFemale.isEmpty() && urlImgMale != null && !urlImgMale.isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(urlImgMale).error(R.drawable.baseline_account_circle_24).into(holder.itemBinding.avatarImageView);
//            Glide.with(holder.itemView.getContext()).load(urlImgFemale).error(R.drawable.baseline_account_circle_24).into(holder.itemCommentBinding.imageAvatar2);
        }

    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public static class EventFragmentViewHolder extends RecyclerView.ViewHolder {
        private  final ItemRcvEvent1Binding itemBinding;
        private final ItemCommentBinding itemCommentBinding;
        public EventFragmentViewHolder(ItemRcvEvent1Binding itemBinding, ItemCommentBinding itemCommentBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.itemCommentBinding = itemCommentBinding;
        }
    }
}
