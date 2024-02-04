package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.ItemCommentBinding;
import com.thinkdiffai.futurelove.model.ListCommentOfEventModel;

import java.util.ArrayList;
import java.util.List;

public class CommentForEventAdapter extends RecyclerView.Adapter<CommentForEventAdapter.ViewHolder>{
    private ListCommentOfEventModel listComments;
    private List<Integer> listId;
    private Context context;

    public CommentForEventAdapter(ListCommentOfEventModel listComments, Context context) {
        this.listComments = listComments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentForEventAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position_view = position;
        List<ListCommentOfEventModel.Comment> list = listComments.getCommentList();
        ListCommentOfEventModel.Comment comment = list.get(position_view);
        if(comment.getAvatar_user() != null && comment.getAvatar_user().contains("var")){
            String link_avatar = comment.getAvatar_user();
            String strReplace = link_avatar.substring(0, 25);
            String rs = link_avatar.replace(strReplace, "https://futurelove.online");
            Glide.with(holder.itemView.getContext()).load(rs).into(holder.binding.imageAvatar1);
        }

        holder.binding.tvUserName.setText(comment.getUser_name());
        holder.binding.tvContent.setText(comment.getNoi_dung_cmt());
        holder.binding.tvTime.setText(comment.getThoi_gian_release());
    }

    @Override
    public int getItemCount() {
         return listComments.getCommentList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCommentBinding binding;

        public ViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
