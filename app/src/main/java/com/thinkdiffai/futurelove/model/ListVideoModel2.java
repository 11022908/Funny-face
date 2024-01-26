package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListVideoModel2 {
    @SerializedName("list_sukien_video")
    private List<VideoModel2> videoModel2List;
//    private List<Object> videoModel2List;
//
//    public List<Object> getVideoModel2List() {
//        return videoModel2List;
//    }
//
//    public void setVideoModel2List(List<Object> videoModel2List) {
//        this.videoModel2List = videoModel2List;
//    }
    //    public ListVideoModel2(List<VideoModel2> videoModel2List) {
//        this.videoModel2List = videoModel2List;
//    }
//
    public ListVideoModel2() {
    }

    public List<VideoModel2> getVideoModel2List() {
        return videoModel2List;
    }

    public void setVideoModel2List(List<VideoModel2> videoModel2List) {
        this.videoModel2List = videoModel2List;
    }
}
