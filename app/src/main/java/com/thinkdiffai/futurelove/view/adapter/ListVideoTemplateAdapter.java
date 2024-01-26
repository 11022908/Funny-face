package com.thinkdiffai.futurelove.view.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.thinkdiffai.futurelove.databinding.FragmentDetailVideoBinding;
import com.thinkdiffai.futurelove.databinding.VideoItemBinding;
import com.thinkdiffai.futurelove.model.ListVideoModel2;
import com.thinkdiffai.futurelove.model.ListVideoModel2Parent;
import com.thinkdiffai.futurelove.model.VideoModel2;
import com.thinkdiffai.futurelove.view.fragment.DetailVideoTemplateFragment;
import com.thinkdiffai.futurelove.view.fragment.PairingFragment;
import com.thinkdiffai.futurelove.view.fragment.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class ListVideoTemplateAdapter extends RecyclerView.Adapter<ListVideoTemplateAdapter.ViewHolder> {

    private ListVideoModel2 listVideoModel2;

    private Context context;
    public interface OnVideoClickListener {
        void onVideoClick(VideoModel2 videoModel2);
    }

    private OnVideoClickListener onVideoClickListener;

    public void setOnVideoClickListener(OnVideoClickListener listener) {
        this.onVideoClickListener = listener;
    }

    public ListVideoTemplateAdapter(ListVideoModel2 listVideoModel2, Context context) {
        this.listVideoModel2 = listVideoModel2;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding videoItemBinding = VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(videoItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int position_view = position;
        VideoModel2 videoModel2 = listVideoModel2.getVideoModel2List().get(position_view);
        ProgressBar progressBar = holder.videoItemBinding.progressBar;
        String url = videoModel2.getLink_video();
        int id_video = videoModel2.getId();
        String name_video = videoModel2.getNoi_dung();
        VideoView videoView = holder.videoItemBinding.viewVideo;

        try{
            videoView.setVideoURI(Uri.parse(url));
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
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
        }catch (Exception e){
            Log.d("TAG", "onBindViewHolder: " + e.getMessage());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onVideoClickListener != null) {
                    VideoModel2 videoModel2= listVideoModel2.getVideoModel2List().get(position_view);
                    onVideoClickListener.onVideoClick(videoModel2);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return listVideoModel2.getVideoModel2List().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        VideoItemBinding videoItemBinding;
        public ViewHolder(VideoItemBinding videoItemBinding) {
            super(videoItemBinding.getRoot());
            this.videoItemBinding = videoItemBinding;
        }
    }
}
