package com.thinkdiffai.futurelove.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.FragmentListImageBinding;
import com.thinkdiffai.futurelove.databinding.ImageItemBinding;
import com.thinkdiffai.futurelove.model.ImageModel;

import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.ListImageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListImageFragment extends Fragment {
    private FragmentListImageBinding fragmentListImageBinding;
    private ListImageAdapter listImageAdapter;
    private ArrayList<String> listLinks = new ArrayList<>();
    private ImageItemBinding imageItemBinding;
    private SharedPreferences sharedPreferences;
    private int id_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("check_reponse_oncreate_view", "onResponse: ");
        fragmentListImageBinding = FragmentListImageBinding.inflate(inflater, container, false);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        id_user = Integer.parseInt(sharedPreferences.getString("id_user", "null"));
        InitUI();
        return fragmentListImageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void InitUI(){
        CallAPIGetListImage();
        ComebackMenu();
    }
    private void ComebackMenu(){
        fragmentListImageBinding.btnComeBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
    private void CallAPIGetListImage(){
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<ImageModel> call = apiService.getListImageUpload(id_user, "nam");
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                listImageAdapter = new ListImageAdapter(response.body(), getContext());
                fragmentListImageBinding.rcvlistImage.setLayoutManager(new GridLayoutManager(getContext(), 2));
                fragmentListImageBinding.rcvlistImage.setAdapter(listImageAdapter);
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Log.d("check_reponse_list_image_failure", "onResponse: ");
            }
        });
    }
}
