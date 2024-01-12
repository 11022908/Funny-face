package com.thinkdiffai.futurelove.view.fragment;

import static android.app.PendingIntent.getActivity;
import static com.thinkdiffai.futurelove.util.Util.kProgressHUD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.thinkdiffai.futurelove.databinding.FragmentEventDetailBinding;
import com.thinkdiffai.futurelove.model.DetailEventList;
import com.thinkdiffai.futurelove.model.DetailEventListParent;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.adapter.CommentAdapter;
import com.thinkdiffai.futurelove.view.adapter.EventHomeAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventFragment extends Fragment {
    private FragmentEventDetailBinding fragmentEventDetailBinding;
    private EventHomeAdapter eventHomeAdapter;
    private CommentAdapter commentAdapter;
    boolean isLoadingMore;
    boolean isLoading;
    int id_user;
    Context context;


    private void LoadDataEvent(){

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
                    Log.d("check_response_1", "onResponse: pke");
                    Log.d("check_response_1", "onResponse: "+ response.body());
                    DetailEventListParent detailEventListParent = response.body();
                    List<DetailEventList> detailEventLists = detailEventListParent.getListSukien();
                    if (!detailEventLists.isEmpty()){
                        eventHomeAdapter.setData(detailEventLists);
                        Log.d("hung", "onResponse: "+detailEventLists.size());
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

    private void InitUI(){

    }
}
