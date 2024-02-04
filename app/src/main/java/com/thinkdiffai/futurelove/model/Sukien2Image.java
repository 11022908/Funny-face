package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

public class Sukien2Image {
    @SerializedName("sukien_2_image")
    public eventSwap2image eventSwap2image;

    public Sukien2Image.eventSwap2image getEventSwap2image() {
        return eventSwap2image;
    }

    public void setEventSwap2image(Sukien2Image.eventSwap2image eventSwap2image) {
        this.eventSwap2image = eventSwap2image;
    }

    public static class eventSwap2image{
        public String id_saved;
        public String link_src_goc;
        public String link_tar_goc;
        public String link_da_swap;
        public String id_toan_bo_su_kien;
        public String thoigian_sukien;
        public String device_them_su_kien;
        public String ip_them_su_kien;
        public int id_user;
        public int count_comment;
        public int count_view;
        public int id_template;
        public String loai_sukien;

        public eventSwap2image(String id_saved, String link_src_goc, String link_tar_goc, String link_da_swap, String id_toan_bo_su_kien, String thoigian_sukien, String device_them_su_kien, String ip_them_su_kien, int id_user, int count_comment, int count_view, int id_template, String loai_sukien) {
            this.id_saved = id_saved;
            this.link_src_goc = link_src_goc;
            this.link_tar_goc = link_tar_goc;
            this.link_da_swap = link_da_swap;
            this.id_toan_bo_su_kien = id_toan_bo_su_kien;
            this.thoigian_sukien = thoigian_sukien;
            this.device_them_su_kien = device_them_su_kien;
            this.ip_them_su_kien = ip_them_su_kien;
            this.id_user = id_user;
            this.count_comment = count_comment;
            this.count_view = count_view;
            this.id_template = id_template;
            this.loai_sukien = loai_sukien;
        }

        public String getId_saved() {
            return id_saved;
        }

        public void setId_saved(String id_saved) {
            this.id_saved = id_saved;
        }

        public String getLink_src_goc() {
            return link_src_goc;
        }

        public void setLink_src_goc(String link_src_goc) {
            this.link_src_goc = link_src_goc;
        }

        public String getLink_tar_goc() {
            return link_tar_goc;
        }

        public void setLink_tar_goc(String link_tar_goc) {
            this.link_tar_goc = link_tar_goc;
        }

        public String getLink_da_swap() {
            return link_da_swap;
        }

        public void setLink_da_swap(String link_da_swap) {
            this.link_da_swap = link_da_swap;
        }

        public String getId_toan_bo_su_kien() {
            return id_toan_bo_su_kien;
        }

        public void setId_toan_bo_su_kien(String id_toan_bo_su_kien) {
            this.id_toan_bo_su_kien = id_toan_bo_su_kien;
        }

        public String getThoigian_sukien() {
            return thoigian_sukien;
        }

        public void setThoigian_sukien(String thoigian_sukien) {
            this.thoigian_sukien = thoigian_sukien;
        }

        public String getDevice_them_su_kien() {
            return device_them_su_kien;
        }

        public void setDevice_them_su_kien(String device_them_su_kien) {
            this.device_them_su_kien = device_them_su_kien;
        }

        public String getIp_them_su_kien() {
            return ip_them_su_kien;
        }

        public void setIp_them_su_kien(String ip_them_su_kien) {
            this.ip_them_su_kien = ip_them_su_kien;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public int getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(int count_comment) {
            this.count_comment = count_comment;
        }

        public int getCount_view() {
            return count_view;
        }

        public void setCount_view(int count_view) {
            this.count_view = count_view;
        }

        public int getId_template() {
            return id_template;
        }

        public void setId_template(int id_template) {
            this.id_template = id_template;
        }

        public String getLoai_sukien() {
            return loai_sukien;
        }

        public void setLoai_sukien(String loai_sukien) {
            this.loai_sukien = loai_sukien;
        }
    }
}
