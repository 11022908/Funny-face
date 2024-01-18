package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListVideoModel {
    @SerializedName("sukien_video")
    private List<VideoModelCustom> listSukienVideo;

    public ListVideoModel(List<VideoModelCustom> listSukienVideo) {
        this.listSukienVideo = listSukienVideo;
    }

    public List<VideoModelCustom> getListSukienVideo() {
        return listSukienVideo;
    }

    public void setListSukienVideo(List<VideoModelCustom> listSukienVideo) {
        this.listSukienVideo = listSukienVideo;
    }
}
