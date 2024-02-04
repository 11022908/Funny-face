package com.thinkdiffai.futurelove.view.fragment;

import static android.app.PendingIntent.getActivity;
import static com.thinkdiffai.futurelove.util.Util.kProgressHUD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.protobuf.Api;
import com.thinkdiffai.futurelove.databinding.FragmentEventDetailBinding;
import com.thinkdiffai.futurelove.model.DetailEvent;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.model.DetailEventListParent;
import com.thinkdiffai.futurelove.model.ListCommentOfEventModel;
import com.thinkdiffai.futurelove.model.comment.CommentList;
import com.thinkdiffai.futurelove.model.comment.CommentPage;
import com.thinkdiffai.futurelove.model.comment.DetailUser;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.CommentAdapter;
import com.thinkdiffai.futurelove.view.adapter.CommentForEventAdapter;
import com.thinkdiffai.futurelove.view.adapter.DetailEventAdapter;
import com.thinkdiffai.futurelove.view.adapter.EventHomeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventFragment extends Fragment {

    private FragmentEventDetailBinding fragmentEventDetailBinding;
    private DetailEvent detailEvent;
    private DetailEventAdapter detailEventAdapter;

    ArrayList<ListCommentOfEventModel.Comment> commentArrayList;
    String id_event;
    String name_event;
    String name_user;
    int count_comment;
    int count_view;
    String stt_su_kien;
    String content_event;
    String link_avatar;
    int id_user_event;
    String new_comment;
    int id_user;
    String avatar_user;
    String TenUser;
    private SharedPreferences sharedPreferences;
    CommentForEventAdapter commentAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user_str = sharedPreferences.getString("id_user", "");
        String avatar_user = sharedPreferences.getString("avatar", "");
        if (id_user_str == "") {
            id_user = 0;
        }else{
            id_user = Integer.parseInt(id_user_str);
        }


        Bundle bundle = getArguments();
        if(bundle != null){
            id_event = bundle.getString("id_event");
            name_event = bundle.getString("name_event");
            name_user = bundle.getString("name_user");
            count_comment = bundle.getInt("count_comment");
            count_view = bundle.getInt("count_view");
            content_event = bundle.getString("detail_event");
            link_avatar = bundle.getString("link_avatar_event");
            stt_su_kien = bundle.getString("stt_su_kien").substring(0, bundle.getString("stt_su_kien").length() - 2);
            id_user_event = Integer.parseInt(bundle.getString("id_user"));
            GetUserName();
            Log.d("check_un_event", "onViewCreated: " + id_user_event + " " + name_user);

            detailEvent = new DetailEvent(count_comment, count_view, Long.parseLong(id_event), link_avatar, content_event, name_user, name_event);


            Log.d("check_adapter", "onViewCreated: " + detailEventAdapter);
            Log.d("check_bundle_get_data", stt_su_kien + "" + count_view + count_comment);
        }
        else {
            Log.d("check_bundle", "Bundle is null");
        }
        InitData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentEventDetailBinding = FragmentEventDetailBinding.inflate(inflater, container, false);
        return fragmentEventDetailBinding.getRoot();
    }

    private void InitData(){
        GetListComment();

        fragmentEventDetailBinding.btnSearchProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_comment = fragmentEventDetailBinding.edtGivecomment.getText().toString();
                Log.d("check_new_cmt", "InitData: " + new_comment);
                if(new_comment.isEmpty())
                    Toast.makeText(getContext(), "enter your crush name", Toast.LENGTH_SHORT).show();
                else
                    postComment();
            }
        });
        if(avatar_user != null && avatar_user.contains("var")){

            String strReplace = avatar_user.substring(0, 25);
            String rs = avatar_user.replace(strReplace, "https://futurelove.online");
            Glide.with(fragmentEventDetailBinding.imgPerson2Events).load(rs).into(fragmentEventDetailBinding.imgPerson2Events);
        }

    }
    private void GetUserName(){
        ApiService apiService = RetrofitClient.getInstance("").getRetrofit().create(ApiService.class);
        Call<DetailUser> call = apiService.getDetailUser(id_user_event);
        call.enqueue(new Callback<DetailUser>() {
            @Override
            public void onResponse(Call<DetailUser> call, Response<DetailUser> response) {
                if(response.isSuccessful() && response.body() != null){
                    TenUser = response.body().getUser_name();
                    Log.d("check_rs_hung_ten_user", "onResponse: " + TenUser);
                    DetailAdapter(TenUser);
                }
            }

            @Override
            public void onFailure(Call<DetailUser> call, Throwable t) {
                Log.d("check_failure", "onFailure: " + t.getMessage());
            }
        });
    }
    private void DetailAdapter(String name){
        detailEventAdapter = new DetailEventAdapter(detailEvent, name, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentEventDetailBinding.rcvEvent.setLayoutManager(linearLayoutManager);
        fragmentEventDetailBinding.rcvEvent.setAdapter(detailEventAdapter);
    }
    private void postComment(){
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<Object> call = apiService.postComment(id_user, new_comment, "xiaomi", id_event, Integer.parseInt(stt_su_kien), "", "", id_user_event, "", "");
        Log.d("data_bf_call", "id_event: " + id_event + " stt sk: " + stt_su_kien + " id_user_event: " + id_user_event);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("check_rs_post_cmt", "onResponse: " + response.body());
                Toast.makeText(getContext(), "Comment successfully", Toast.LENGTH_SHORT).show();
                fragmentEventDetailBinding.edtGivecomment.setText("");
                GetListComment();
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("check_rs_post_cmt", "onfailure: " + t.getMessage());
            }
        });
    }
    private void GetListComment(){
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<ListCommentOfEventModel> call = apiService.getAllComment(Integer.parseInt(stt_su_kien), id_event, 217);
        call.enqueue(new Callback<ListCommentOfEventModel>() {
            @Override
            public void onResponse(Call<ListCommentOfEventModel> call, Response<ListCommentOfEventModel> response) {
                if(response.isSuccessful() ){
                    Log.d("check_rs_comment_1", "onResponse: "+ response.body());
                    if(response.body().getCommentList() != null){
                        ListCommentOfEventModel listCommentOfEventModel = response.body();

                        commentAdapter = new CommentForEventAdapter(listCommentOfEventModel, getContext());


                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        fragmentEventDetailBinding.rcvComment.setLayoutManager(linearLayoutManager);
                        fragmentEventDetailBinding.rcvComment.setAdapter(commentAdapter);
                    }
                    else{
                        Toast.makeText(getContext(), "No comment", Toast.LENGTH_SHORT).show();
                        Log.d("check_rs_comment_1", "onResponse: null" );
                    }
                }
                else{
                    Log.d("check_rs_comment", "onResponse_null: null");
                }
            }
            @Override
            public void onFailure(Call<ListCommentOfEventModel> call, Throwable t) {
                Log.d("check_rs_comment", "onResponse_null:" + t.getMessage());
            }
        });
    }

}
