package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.databinding.FragmentListImageBinding;
import com.thinkdiffai.futurelove.databinding.ImageItemBinding;
import com.thinkdiffai.futurelove.model.ImageModel;

import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder>{
    private ImageModel listLinkImage;
    private Context context;

    public ListImageAdapter(ImageModel listLinkImage, Context context) {
        Log.d("check_list_image_adapter", "ListImageAdapter: ");
        this.listLinkImage = listLinkImage;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding imageItemBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(imageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String link = listLinkImage.getImage_links_nam().get(position);
        if(link != null){
            Glide.with(context).load(link).into(holder.imageItemBinding.cvImageCreated);
        }
    }

    @Override
    public int getItemCount() {
        if(listLinkImage.getImage_links_nam().size() != 0)
            return listLinkImage.getImage_links_nam().size();
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
