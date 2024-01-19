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

import com.thinkdiffai.futurelove.databinding.FragmentMyEventsBinding;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.model.DetailEventListParent;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.EventHomeAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsFragment extends Fragment {
    private FragmentMyEventsBinding fragmentMyEventsBinding;
    private SharedPreferences sharedPreferences;
    private EventHomeAdapter eventHomeAdapter;
    private int id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMyEventsBinding = FragmentMyEventsBinding.inflate(inflater, container, false);
        return fragmentMyEventsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try{
            sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            id_user = Integer.parseInt(sharedPreferences.getString("id_user", "null"));
            InitUI();

        }catch (Exception e){
            Log.d("Error", "onCreateView: " + e.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void InitUI(){

    }
    private void getAllEventByUserId(){
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user_str = sharedPreferences.getString("id_user", "null");
        Log.d("check_id_user_profile", "getAllEventByUserId: " + id_user_str);
        Call<DetailEventListParent> call = apiService.getAllEventByUserId(Integer.parseInt(id_user_str));
        call.enqueue(new Callback<DetailEventListParent>() {
            @Override
            public void onResponse(Call<DetailEventListParent> call, Response<DetailEventListParent> response) {
                Log.d("hung_onrespone_user_detail", "onResponse: ");
                DetailEventListParent detailEventListParent = response.body();
                Log.d("check_size_event_list", "onResponse: "  + detailEventListParent);
                List<DetailEventList> detailEventLists = detailEventListParent.getListSukien();
                if(!detailEventLists.isEmpty()){
                    eventHomeAdapter = new EventHomeAdapter(detailEventLists, null, getContext());
                    eventHomeAdapter.setData(detailEventLists);
                    fragmentMyEventsBinding.rcvPersonalEvents.setAdapter(eventHomeAdapter);
                }
            }
            @Override
            public void onFailure(Call<DetailEventListParent> call, Throwable t) {
                Log.d("check_failure_detail_user", "onFailure: " + t.getMessage());
            }
        });
    }
}
