package com.thinkdiffai.futurelove.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thinkdiffai.futurelove.databinding.VideoItemBinding;
import com.thinkdiffai.futurelove.model.ListVideoModel;
import com.thinkdiffai.futurelove.model.VideoModel;
import com.thinkdiffai.futurelove.model.VideoModelCustom;
import com.thinkdiffai.futurelove.view.fragment.RecyclerViewClickListener;

import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ViewHolder> {


//    adapter for my collections video
    private List<ListVideoModel> listVideoModels;
    public RecyclerViewClickListener onClickListener;


    private Context context;

    public ListVideoAdapter(List<ListVideoModel> listVideoModels, RecyclerViewClickListener onClickListener, Context context) {
        this.listVideoModels = listVideoModels;
        this.onClickListener = onClickListener;
        this.context = context;
    }
    public ListVideoAdapter(List<ListVideoModel> listVideoModels, Context context){
        this.listVideoModels = listVideoModels;
        this.context = context;
    }
    @NonNull
    @Override
    public ListVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding videoItemBinding = VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(videoItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVideoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ProgressBar progressBar = holder.videoItemBinding.progressBar;
        int position_view = position;
        VideoModelCustom videoModel = listVideoModels.get(position).getListSukienVideo().get(0);
        String urlVideo = videoModel.getLink_video_goc();

        String nameVideo =  videoModel.getNoidung_sukien();
        int id_video_int = videoModel.getId_user();
        Log.d("log_url", "onBindViewHolder: " + nameVideo);
        VideoView videoView = holder.videoItemBinding.viewVideo;

        try {
            videoView.setVideoURI(Uri.parse(urlVideo));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVolume(0f,0f);
                    mp.setLooping(true);
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                        progressBar.setVisibility(View.GONE);
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            videoView.start();

        }catch(Exception e){
            Log.e("TAG", "Error : " + e.toString());
        }

        holder.videoItemBinding.viewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setVisibility(View.GONE);
                onClickListener.onItemClick(urlVideo,nameVideo,id_video_int);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVideoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        VideoItemBinding videoItemBinding;

        public ViewHolder(@NonNull VideoItemBinding VideoItemBinding) {
            super(VideoItemBinding.getRoot());
            this.videoItemBinding = VideoItemBinding;
        }
    }
}
