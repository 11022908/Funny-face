package com.thinkdiffai.futurelove.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thinkdiffai.futurelove.databinding.DialogBottomCatergoryVideoBinding;
import com.thinkdiffai.futurelove.databinding.FragmentListVideoBinding;
import com.thinkdiffai.futurelove.model.ListVideoModelParent;
import com.thinkdiffai.futurelove.model.ListVideoModel;
import com.thinkdiffai.futurelove.model.VideoModel;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.ListVideoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListVideoFragment extends Fragment{
    private FragmentListVideoBinding fragmentListVideoBinding;
    private SharedPreferences sharedPreferences;
    private DialogBottomCatergoryVideoBinding dialogBinding;
    private BottomSheetDialog bottomSheetDialog;
    private int id_user;
    private List<VideoModel> videoModels = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListVideoBinding = FragmentListVideoBinding.inflate(inflater, container, false);
        return fragmentListVideoBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{
            sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            id_user = Integer.parseInt(sharedPreferences.getString("id_user", "null"));
            InitUI();
            fragmentListVideoBinding.btnFilter.setOnClickListener(v -> openDialog());
        }catch (Exception e){
            Log.d("Error", "onCreateView: " + e.getMessage());
        }

    }
    private void openDialog(){
        dialogBinding = DialogBottomCatergoryVideoBinding.inflate(LayoutInflater.from(getContext()));
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        bottomSheetDialog.show();

    }

    private void InitUI(){
        callApiGetData();
        ComebackMenu();
    }
    private void ComebackMenu(){
        fragmentListVideoBinding.btnComeBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
    private void callApiGetData(){
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<ListVideoModelParent> call = apiService.getVidCreateByUser(id_user,0);

        call.enqueue(new Callback<ListVideoModelParent>() {
            @Override
            public void onResponse(Call<ListVideoModelParent> call, Response<ListVideoModelParent> response) {
                ListVideoModelParent listVideoModelParent = response.body();
                List<ListVideoModel> listVideoModelList = new ArrayList<>();
                if(listVideoModelParent == null){
                    Toast.makeText(getContext(), "No video collection", Toast.LENGTH_SHORT).show();
                }
                else{
                    listVideoModelList.addAll(listVideoModelParent.getList());
                    Log.d("check_video_list", "onResponse: "+ response.body().getList().get(0).getListSukienVideo().get(0).getLink_video_goc());
                    Log.d("check_video_list", "onResponse: " + listVideoModelList.get(0).getListSukienVideo().get(0).getLink_video_goc());
                    ListVideoAdapter listVideoAdapter = new ListVideoAdapter(listVideoModelList, getContext());
                    fragmentListVideoBinding.listViewRec.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    fragmentListVideoBinding.listViewRec.setAdapter(listVideoAdapter);
                }


                Log.d("check_respone_l_video", "onResponse: " + listVideoModelList);
            }

            @Override
            public void onFailure(Call<ListVideoModelParent> call, Throwable t) {
                Log.d("check_respone_l_video_failure", "onResponse: " + t.getMessage());
            }

        });
    }
}