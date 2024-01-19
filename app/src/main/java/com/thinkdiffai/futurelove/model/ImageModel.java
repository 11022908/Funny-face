package com.thinkdiffai.futurelove.model;

import java.util.ArrayList;


public class ImageModel {
    public ArrayList<String> image_links_nam;
    public ArrayList<String> image_links_nu;
    public ArrayList<String> image_links_video;

//    public ImageModel(ArrayList<String> image_links_nam) {
//        this.image_links_nam = image_links_nam;
//    }


    public ArrayList<String> getImage_links_nam() {
        return image_links_nam;
    }
    public void setImage_links(ArrayList<String> image_links) {
        this.image_links_nam = image_links;
    }
}
