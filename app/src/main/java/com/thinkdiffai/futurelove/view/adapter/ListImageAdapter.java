package com.thinkdiffai.futurelove.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.databinding.FragmentListImageBinding;
import com.thinkdiffai.futurelove.databinding.ImageItemBinding;

import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder>{
    private List<String> listLinkImage;

    public ListImageAdapter(List<String> listLinkImage) {
        this.listLinkImage = listLinkImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding imageItemBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(imageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with()
    }

    @Override
    public int getItemCount() {
        if(listLinkImage.size() != 0)
            return listLinkImage.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageItemBinding imageItemBinding;

        public ViewHolder(ImageItemBinding imageItemBinding) {
            super(imageItemBinding.getRoot());
            this.imageItemBinding = imageItemBinding;
        }
    }
}
