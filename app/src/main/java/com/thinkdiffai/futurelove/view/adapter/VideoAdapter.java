package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thinkdiffai.futurelove.databinding.VideoItemBinding;
import com.thinkdiffai.futurelove.model.ListVideoModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ListVideoViewHolder>{
    private Context contect;
    public List<ListVideoModel> listVideoModel;

    public VideoAdapter(Context contect) {
        this.contect = contect;
        this.listVideoModel = listVideoModel;
    }

    public void setData(List<ListVideoModel> listVideoModel){
        this.listVideoModel = listVideoModel;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding videoItemBinding = VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new VideoAdapter.ListVideoViewHolder(videoItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVideoViewHolder holder, int position) {
        ProgressBar progressBar = holder.videoItemBinding.progressBar;
        int position_view = position;
        ListVideoModel videoModel = listVideoModel.get(position_view);
        String urlVideo = videoModel.getLink_video();
        String nameVideo =  videoModel.getNoi_dung();
        int id_video_int = videoModel.getId();
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
//                holder.itemView.setVisibility(View.GONE);
                Log.d("log tag detail video", "onClick: ");
//                onClickListener.onItemClick(urlVideo,nameVideo,id_video_int);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listVideoModel != null){
            Log.d("Hunglistvideo", "getItemCount: "+listVideoModel.size());
            return listVideoModel.size();
        }
        return 0;
    }

    public class ListVideoViewHolder extends RecyclerView.ViewHolder{

        VideoItemBinding videoItemBinding;
        public ListVideoViewHolder(@NonNull VideoItemBinding VideoItemBinding) {
            super(VideoItemBinding.getRoot());
            this.videoItemBinding = VideoItemBinding;
        }
    }
}
