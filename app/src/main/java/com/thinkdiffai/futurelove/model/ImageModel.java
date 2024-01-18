package com.thinkdiffai.futurelove.model;

import java.util.ArrayList;


public class ImageModel {
    public ArrayList<String> image_links;

    public ImageModel(ArrayList<String> image_links) {
        this.image_links = image_links;
    }

    public ArrayList<String> getImage_links() {
        return image_links;
    }

    public void setImage_links(ArrayList<String> image_links) {
        this.image_links = image_links;
    }
}
