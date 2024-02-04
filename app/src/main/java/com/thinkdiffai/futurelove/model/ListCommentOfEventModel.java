package com.thinkdiffai.futurelove.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCommentOfEventModel {
    @SerializedName("comment")
    public List<Comment> commentList;

    public ListCommentOfEventModel(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public class Comment{
        public String id_toan_bo_su_kien;
        public String noi_dung_cmt;
        public String dia_chi_ip;
        public String device_cmt;
        public int id_comment;
        public String imageattach;
        public String thoi_gian_release;
        public String user_name;
        public int id_user;
        public String avatar_user;
        public int so_thu_tu_su_kien;
        public String location;
        public String link_nam_goc;
        public String link_nu_goc;

        public Comment(String id_toan_bo_su_kien, String noi_dung_cmt, String dia_chi_ip, String device_cmt, int id_comment, String imageattach, String thoi_gian_release, String user_name, int id_user, String avatar_user, int so_thu_tu_su_kien, String location, String link_nam_goc, String link_nu_goc) {
            this.id_toan_bo_su_kien = id_toan_bo_su_kien;
            this.noi_dung_cmt = noi_dung_cmt;
            this.dia_chi_ip = dia_chi_ip;
            this.device_cmt = device_cmt;
            this.id_comment = id_comment;
            this.imageattach = imageattach;
            this.thoi_gian_release = thoi_gian_release;
            this.user_name = user_name;
            this.id_user = id_user;
            this.avatar_user = avatar_user;
            this.so_thu_tu_su_kien = so_thu_tu_su_kien;
            this.location = location;
            this.link_nam_goc = link_nam_goc;
            this.link_nu_goc = link_nu_goc;
        }

        public String getId_toan_bo_su_kien() {
            return id_toan_bo_su_kien;
        }

        public void setId_toan_bo_su_kien(String id_toan_bo_su_kien) {
            this.id_toan_bo_su_kien = id_toan_bo_su_kien;
        }

        public String getNoi_dung_cmt() {
            return noi_dung_cmt;
        }

        public void setNoi_dung_cmt(String noi_dung_cmt) {
            this.noi_dung_cmt = noi_dung_cmt;
        }

        public String getDia_chi_ip() {
            return dia_chi_ip;
        }

        public void setDia_chi_ip(String dia_chi_ip) {
            this.dia_chi_ip = dia_chi_ip;
        }

        public String getDevice_cmt() {
            return device_cmt;
        }

        public void setDevice_cmt(String device_cmt) {
            this.device_cmt = device_cmt;
        }

        public int getId_comment() {
            return id_comment;
        }

        public void setId_comment(int id_comment) {
            this.id_comment = id_comment;
        }

        public String getImageattach() {
            return imageattach;
        }

        public void setImageattach(String imageattach) {
            this.imageattach = imageattach;
        }

        public String getThoi_gian_release() {
            return thoi_gian_release;
        }

        public void setThoi_gian_release(String thoi_gian_release) {
            this.thoi_gian_release = thoi_gian_release;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getId_user() {
            return id_user;
        }

        public void setId_user(int id_user) {
            this.id_user = id_user;
        }

        public String getAvatar_user() {
            return avatar_user;
        }

        public void setAvatar_user(String avatar_user) {
            this.avatar_user = avatar_user;
        }

        public int getSo_thu_tu_su_kien() {
            return so_thu_tu_su_kien;
        }

        public void setSo_thu_tu_su_kien(int so_thu_tu_su_kien) {
            this.so_thu_tu_su_kien = so_thu_tu_su_kien;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLink_nam_goc() {
            return link_nam_goc;
        }

        public void setLink_nam_goc(String link_nam_goc) {
            this.link_nam_goc = link_nam_goc;
        }

        public String getLink_nu_goc() {
            return link_nu_goc;
        }

        public void setLink_nu_goc(String link_nu_goc) {
            this.link_nu_goc = link_nu_goc;
        }
    }
}
