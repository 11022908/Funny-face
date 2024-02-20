package com.thinkdiffai.futurelove.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.FragmentHomeBinding;
import com.thinkdiffai.futurelove.model.DetailEvent;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.model.DetailEventListParent;
import com.thinkdiffai.futurelove.model.ListVideoModel2;
import com.thinkdiffai.futurelove.model.VideoModel2;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;

import com.thinkdiffai.futurelove.view.adapter.ListVideoTemplateAdapter;
import com.thinkdiffai.futurelove.view.fragment.activity.MainActivity;
import com.thinkdiffai.futurelove.view.adapter.EventHomeAdapter;
import com.thinkdiffai.futurelove.view.adapter.PageEventAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private KProgressHUD kProgressHUD;
    private FragmentHomeBinding fragmentHomeBinding;
    private MainActivity mainActivity;

    private int id_user;
    private EventHomeAdapter eventHomeAdapter;

    private BubbleNavigationLinearView bubbleNavigationLinearView;

    private PageEventAdapter pageEventAdapter;
    private List<DetailEventList> eventList;

    private ArrayList<Integer> pageEventList;
    private LinearLayoutManager linearLayoutManager;

    private boolean isLoading;
    private boolean isLoadingMore;
    private boolean isLastPage;
//    private int currentPage = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mainActivity = (MainActivity) getActivity();
        kProgressHUD = mainActivity.createHud();

        try {
            initUi();
            loadData();
            getDataVideo();
            initData();
        } catch (Exception e) {
            Log.e("ExceptionRuntime", e.toString());
        }

        return fragmentHomeBinding.getRoot();
    }
    private void loadData() {
        if (!kProgressHUD.isShowing() && isLoadingMore) {
            kProgressHUD.show();
        }
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<DetailEventListParent> call = apiService.getEventListForHome(1,id_user);
        call.enqueue(new Callback<DetailEventListParent>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DetailEventListParent> call, Response<DetailEventListParent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DetailEventListParent detailEventListParent = response.body();
                    List<DetailEventList> detailEventLists = detailEventListParent.getListSukien();
                    if (!detailEventLists.isEmpty()){
                        eventHomeAdapter.setData(detailEventLists);
                        eventHomeAdapter.notifyDataSetChanged();
                    }
                }
                if (kProgressHUD.isShowing()) {
                    kProgressHUD.dismiss();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<DetailEventListParent> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                if (kProgressHUD.isShowing()) {
                    kProgressHUD.dismiss();
                    Log.e("MainActivityLog", t.getMessage().toString());
                }
                isLoading = false;
            }
        });
    }

    private void initData() {
        Log.d("CHECK_LOGIN_FRAGMENT", "initData: oke");
        Bundle data = getArguments();
        Log.d("CHECK_LOGIN_FRAGMENT", "initData: oke");
        if(data!=null){
            String user_id = data.getString("user_id");
            Log.d("id_user_detail", "initData: "+ user_id);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id_user",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id_user",user_id);
            editor.commit();
        }
        loadIdUser();
    }

    private void loadIdUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id_user",0);
        String id_user_str = sharedPreferences.getString("id_user", "");
        Log.d("check_user_id", "loadIdUser: "+ id_user_str);
        if (id_user_str == "") {
            id_user = 0;
        }else{
            id_user = Integer.parseInt(id_user_str);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initUi() {
        // Set isLoadingMore = true in order to set kud loading in the first api calling time
        isLoadingMore = true;

        //Reset all events
        mainActivity.soThuTuSuKien = 0;
        eventList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), GridLayoutManager.VERTICAL, false);
        fragmentHomeBinding.rcvHome.setLayoutManager(linearLayoutManager);
        // It automatically get the id of all events in EventHomeAdapter and assign into this::goToEventDetail
        eventHomeAdapter = new EventHomeAdapter(eventList, detailEvent -> goToEventDetail(detailEvent), getContext());
        fragmentHomeBinding.rcvHome.setAdapter(eventHomeAdapter);

        pageEventList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        for(int i=1; i<=19;i++){
            pageEventList.add(i);
        }
        pageEventAdapter = new PageEventAdapter(getContext(), pageEventList, position -> goToPageEvent(position));
        fragmentHomeBinding.btnCreate.setOnClickListener(v -> NavToSwapFaceWithYourVideo());
    }

    // Current page is 4 because it is Timeline Fragment
    private void goToEventDetail(DetailEvent detailEvent) {
        Log.d("check_id_toan_bo_su_kien", "goToEventDetail: "+ detailEvent);
        Bundle bundle = new Bundle();
        bundle.putString("id_event", String.valueOf(detailEvent.getId()));
        bundle.putString("name_event", detailEvent.getTenSuKien());
        bundle.putString("name_user", detailEvent.getTenNam());
        bundle.putInt("count_comment", detailEvent.getCountComment());
        bundle.putInt("count_view", detailEvent.getCountView());
        bundle.putString("detail_event", detailEvent.getNoiDungSuKien());
        bundle.putString("link_avatar_event", detailEvent.getLinkNamGoc());
        bundle.putString("stt_su_kien", String.valueOf(detailEvent.getSoThuTuSuKien()));
        bundle.putString("id_user", String.valueOf(detailEvent.getIdUser()));

        DetailEventFragment fragment = new DetailEventFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_home, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void goToPageEvent(int position){
        getData(position);
    }

    private void getData(int position) {
        if (!kProgressHUD.isShowing() && isLoadingMore) {
            kProgressHUD.show();
        }
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<DetailEventListParent> call = apiService.getEventListForHome(position,id_user);
        Log.d("check_response", "getData: "+ call.toString());
        call.enqueue(new Callback<DetailEventListParent>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<DetailEventListParent> call, Response<DetailEventListParent> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("check_response", "onResponse: "+ response.body());
                    DetailEventListParent detailEventListParent = response.body();

                    List<DetailEventList> detailEventLists = detailEventListParent.getListSukien();
                    if (!detailEventLists.isEmpty()){
                        eventHomeAdapter.notifyDataSetChanged();
                    }

                }
                if (kProgressHUD.isShowing()) {
                    kProgressHUD.dismiss();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<DetailEventListParent> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                if (kProgressHUD.isShowing()) {
                    kProgressHUD.dismiss();
                    Log.e("MainActivityLog", t.getMessage().toString());
                }
                isLoading = false;
            }
        });
    }

    private void getDataVideo() {
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<ListVideoModel2> call = apiService.getListVideo(1, 0);

        call.enqueue(new Callback<ListVideoModel2>() {
            @Override
            public void onResponse(Call<ListVideoModel2> call, Response<ListVideoModel2> response) {
                ListVideoModel2 listVideoModel2 = (ListVideoModel2) response.body();
                ListVideoTemplateAdapter listVideoTemplateAdapter = new ListVideoTemplateAdapter(listVideoModel2, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                listVideoTemplateAdapter.setOnVideoClickListener(new ListVideoTemplateAdapter.OnVideoClickListener() {
                    @Override
                    public void onVideoClick(VideoModel2 videoModel2) {
                        if(videoModel2 != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("id_video", String.valueOf(videoModel2.getId()));
                            bundle.putString("name_video", videoModel2.getNoi_dung());
                            bundle.putString("url_video", videoModel2.getLink_video());
                            Log.d("check_data_before_bundle", "onVideoClick: " + videoModel2.getId() + videoModel2.getNoi_dung() + videoModel2.getLink_video());
                            DetailVideoTemplateFragment fragment = new DetailVideoTemplateFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.homeFragment, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }

                });

                fragmentHomeBinding.rcvVideo.setLayoutManager(layoutManager);
                fragmentHomeBinding.rcvVideo.setAdapter(listVideoTemplateAdapter);
            }
            @Override
            public void onFailure(Call<ListVideoModel2> call, Throwable t) {
                Log.d("check_list_video_failure", "onResponse: " + t.getMessage());

            }
        });
    }
    private void NavToSwapFaceWithYourVideo(){
        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.swap2Image);
    }

}
