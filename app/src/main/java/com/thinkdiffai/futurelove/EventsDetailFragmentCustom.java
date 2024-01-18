package com.thinkdiffai.futurelove;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thinkdiffai.futurelove.databinding.FragmentEventDetailBinding;
import com.thinkdiffai.futurelove.model.Comment;
import com.thinkdiffai.futurelove.model.comment.EventsUser.EventsUser;
import com.thinkdiffai.futurelove.model.comment.EventsUser.Sukien;
import com.thinkdiffai.futurelove.model.comment.EventsUser.SukienX;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.CommentAdapter;
import com.thinkdiffai.futurelove.view.adapter.EventDetailAdapterCustom;
import com.thinkdiffai.futurelove.view.adapter.EventFragmentEventAdapter;
import com.thinkdiffai.futurelove.view.adapter.EventHomeAdapter;
import com.thinkdiffai.futurelove.view.adapter.StringAdapter;
import com.thinkdiffai.futurelove.view.adapter.StringAdapter1;
import com.thinkdiffai.futurelove.view.fragment.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsDetailFragmentCustom extends Fragment {
    private FragmentEventDetailBinding fragmentEventDetailBinding;
    private EventDetailAdapterCustom eventDetailAdapterCustom;
    private List<SukienX> sukienXList= new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    private MainActivity mainActivity;
    private KProgressHUD kProgressHUD;

    private List<String> stringList= new ArrayList<>();
    private EventFragmentEventAdapter eventFragmentEventAdapter;
    private StringAdapter stringAdapter;
    private StringAdapter1 stringAdapter1;
    public EventHomeAdapter eventHomeAdapter;
    public CommentAdapter commentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentEventDetailBinding= FragmentEventDetailBinding.inflate(inflater, container, false);
        kProgressHUD = mainActivity.createHud();


//        OnClickNavigation();
        // xu ly rcv in navigation
//        XuLyRcycNavigation();
        // xu ly rcv events
//        XulyRcy();
        // xu ly navigation
//        XulyNavigation();
//        fragmentEventDetailBinding.llEvents.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(EventsDetailFragmentCustom.this).navigate(R.id.action_eventsFragment_to_addEventFragment);
//            }
//        });
        //xu ly viewpaper
//            ViewPaper();
        // xu ly loading
//        XulyLoading();
        return fragmentEventDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataEventUser();
        Log.d("create_view", "onViewCreated: ");
    }

    private void getDataEventUser(){
        Log.d("tagInEventDetail", "onCreateView: ");
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN2).getRetrofit().create(ApiService.class);
        Call<EventsUser> call = apiService.getEventUser(5);
        call.enqueue(new Callback<EventsUser>() {
            @Override
            public void onResponse(Call<EventsUser> call, Response<EventsUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EventsUser eventsUser=response.body();
                    List<Sukien> sukien= eventsUser.getList_sukien();
                    for (Sukien s: sukien){
                        //sukienXList=s.getSukien();
                        for (SukienX a: s.getSukien()){
                            stringList.add(a.getTen_su_kien());
                            sukienXList.add(a);
                        }
                    }
//                    Glide.with(getContext()).load(sukien.get(0).getSukien().get(0).getLink_nam_goc()).into(fragmentEventDetailBinding.imgPerson1Events);
                    Glide.with(getContext()).load(sukien.get(0).getSukien().get(0).getLink_nu_goc()).into(fragmentEventDetailBinding.imgPerson2Events);
                    stringAdapter.notifyDataSetChanged();
                    Log.d("stringadapter1", "onResponse: " + stringAdapter1);
                    stringAdapter1.notifyDataSetChanged();
//                    fragmentEventDetailBinding.rcvEvents.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            int currentItem = fragmentEventDetailBinding.vp2Events.getCurrentItem();
//                            int totalItems = eventFragmentEventAdapter.getItemCount();
//                            if (currentItem < totalItems - 1) {
//                                fragmentEventDetailBinding.vp2Events.setCurrentItem(currentItem + 1);
//                            }
//                        }
//                    });
                    eventFragmentEventAdapter.SetData(sukienXList);
                    eventFragmentEventAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<EventsUser> call, Throwable t) {

            }
        });
    }
    //
    //xu ly navigation button
//    private void OnClickNavigation(){
//        fragmentEventDetailBinding.rcv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToCommentFragment();
//            }
//        });
//        // Click btn Pairing
//        fragmentEventDetailBinding.btnPairingSk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToPairingFragment();
//            }
//        });
//        // Click btn Home
//        fragmentEventDetailBinding.btnHomeSk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToHomeFragment();
//            }
//        });
//    }

    private void goToPairingFragment() {
        NavHostFragment.findNavController(EventsDetailFragmentCustom.this).navigate(R.id.action_eventsFragment_to_pairingFragment);
    }

    private void goToHomeFragment() {
        NavHostFragment.findNavController(EventsDetailFragmentCustom.this).navigate(R.id.action_eventsFragment_to_homeFragment);
    }

    private void goToCommentFragment() {
        NavHostFragment.findNavController(EventsDetailFragmentCustom.this).navigate(R.id.action_eventsFragment_to_commentFragment2);
    }
//    private void XuLyRcycNavigation(){
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        fragmentEventDetailBinding.rcvEvent.setLayoutManager(linearLayoutManager);
//        eventHomeAdapter = new EventHomeAdapter(sukienXList, );
//        fragmentEventDetailBinding.rcvEventsNavigation.setAdapter(stringAdapter);
//    }
//    private void XulyNavigation(){
//        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(getActivity(),
//                fragmentEventDetailBinding.drawerEvents,
//                fragmentEventDetailBinding.constraintLayoutEvents,R.string.Open,R.string.Close);
//        fragmentEventDetailBinding.drawerEvents.addDrawerListener(toggle);
//        toggle.syncState();
//    }
//    private  void  XulyLoading(){
//        fragmentEventDetailBinding.customLoading.setVisibility(View.VISIBLE);
//
//        // Start the animation of the progress bar
//        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.right_loading);
//        fragmentEventDetailBinding.customLoading.startAnimation(anim);
//
//        fragmentEventDetailBinding.customLoading.animation();
//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    private void XulyRcy(){
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
//        fragmentEventDetailBinding.rcvEvents.setLayoutManager(linearLayoutManager);
//        stringAdapter1= new StringAdapter1(stringList, new StringAdapter1.OnItemClickListener() {
//            @Override
//            public void onItemClick(String item) {
//
//            }
//        });
//        fragmentEventDetailBinding.rcvEvents.setAdapter(stringAdapter1);
//    }
//    private void ViewPaper(){
//        eventFragmentEventAdapter= new EventFragmentEventAdapter(sukienXList,getContext());
//        fragmentEventsBinding.vp2Events.setAdapter(eventFragmentEventAdapter);
//        fragmentEventsBinding.vp2Events.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(EventsFragment.this).navigate(R.id.action_eventsFragment_to_eventAndCommentFragment);
//            }
//        });
//    }
}