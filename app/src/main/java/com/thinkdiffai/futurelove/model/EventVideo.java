package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

public class EventVideo {
    public SukienVideo sukien_video;

    public SukienVideo getSukien_video() {
        return sukien_video;
    }

    public void setSukien_video(SukienVideo sukien_video) {
        this.sukien_video = sukien_video;
    }

    public class SukienVideo{
        public String id_sukien_video;
        public int id_video_swap;
        public String linkimg;
        public String link_vid_swap;
        public String link_vid_goc;
        public String ten_video;
        public String noidung;
        public String thoigian_sukien;
        public double thoigian_swap;
        public String device_tao_vid;
        public String ip_tao_vid;

        public String getId_sukien_video() {
            return id_sukien_video;
        }

        public void setId_sukien_video(String id_sukien_video) {
            this.id_sukien_video = id_sukien_video;
        }

        public int getId_video_swap() {
            return id_video_swap;
        }

        public void setId_video_swap(int id_video_swap) {
            this.id_video_swap = id_video_swap;
        }

        public String getLinkimg() {
            return linkimg;
        }

        public void setLinkimg(String linkimg) {
            this.linkimg = linkimg;
        }

        public String getLink_vid_swap() {
            return link_vid_swap;
        }

        public void setLink_vid_swap(String link_vid_swap) {
            this.link_vid_swap = link_vid_swap;
        }

        public String getLink_vid_goc() {
            return link_vid_goc;
        }

        public void setLink_vid_goc(String link_vid_goc) {
            this.link_vid_goc = link_vid_goc;
        }

        public String getTen_video() {
            return ten_video;
        }

        public void setTen_video(String ten_video) {
            this.ten_video = ten_video;
        }

        public String getNoidung() {
            return noidung;
        }

        public void setNoidung(String noidung) {
            this.noidung = noidung;
        }

        public String getThoigian_sukien() {
            return thoigian_sukien;
        }

        public void setThoigian_sukien(String thoigian_sukien) {
            this.thoigian_sukien = thoigian_sukien;
        }

        public double getThoigian_swap() {
            return thoigian_swap;
        }

        public void setThoigian_swap(double thoigian_swap) {
            this.thoigian_swap = thoigian_swap;
        }

        public String getDevice_tao_vid() {
            return device_tao_vid;
        }

        public void setDevice_tao_vid(String device_tao_vid) {
            this.device_tao_vid = device_tao_vid;
        }

        public String getIp_tao_vid() {
            return ip_tao_vid;
        }

        public void setIp_tao_vid(String ip_tao_vid) {
            this.ip_tao_vid = ip_tao_vid;
        }
    }


}
