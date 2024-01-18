package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListVideoModelParent {
    @SerializedName("list_sukien_video")
    private List<ListVideoModel> list;

    public ListVideoModelParent(List<ListVideoModel> list) {
        this.list = list;
    }

    public List<ListVideoModel> getList() {
        return list;
    }

    public void setList(List<ListVideoModel> list) {
        this.list = list;
    }
}
