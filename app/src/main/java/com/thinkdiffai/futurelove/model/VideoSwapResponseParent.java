package com.thinkdiffai.futurelove.model;

import java.util.List;

public class VideoSwapResponseParent {
    private List<GetYourVideoSwapModel> list;

    public VideoSwapResponseParent(List<GetYourVideoSwapModel> list) {
        this.list = list;
    }

    public List<GetYourVideoSwapModel> getList() {
        return list;
    }

    public void setList(List<GetYourVideoSwapModel> list) {
        this.list = list;
    }
}
