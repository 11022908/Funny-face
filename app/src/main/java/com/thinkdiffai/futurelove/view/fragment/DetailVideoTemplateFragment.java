package com.thinkdiffai.futurelove.view.fragment;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.DialogBottomSheetSelectedHomeBinding;
import com.thinkdiffai.futurelove.databinding.FragmentDetailVideoBinding;
import com.thinkdiffai.futurelove.model.EventVideo;
import com.thinkdiffai.futurelove.model.EventVideoParent;
import com.thinkdiffai.futurelove.model.IpNetworkModel;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.RetrofitIp;
import com.thinkdiffai.futurelove.service.api.Server;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailVideoTemplateFragment extends Fragment {
    private FragmentDetailVideoBinding fragmentDetailVideoBinding;
    String id_video;
    String name_video;
    String url_video;
    private DialogBottomSheetSelectedHomeBinding dialogBinding;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private BottomSheetDialog bottomSheetDialog;
    private Uri selectedImageUri;
    private String uriResponse;
    private int id_user;
    private String deviceName;
    private String ip_them_su_kien;
    String token_au;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDetailVideoBinding = FragmentDetailVideoBinding.inflate(inflater, container, false);
        return fragmentDetailVideoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            id_video = bundle.getString("id_video");
            name_video = bundle.getString("name_video");
            url_video = bundle.getString("url_video");
            Log.d("check_bundle_get_data", id_video + name_video + url_video);
        }
        else {
            Log.d("check_bundle", "Bundle is null");
        }
        InitUI();
        InitAction();
        InitData();
    }
    private void callApiAddress() {
        ApiService apiService = RetrofitIp.getInstance(Server.GET_CITY_NAME_FROM_IP).getRetrofit().create(ApiService.class);
        Call<IpNetworkModel> call = apiService.getIpApiResponse();
        call.enqueue(new Callback<IpNetworkModel>() {
            @Override
            public void onResponse(Call<IpNetworkModel> call, Response<IpNetworkModel> response) {
                if(response.body()!= null && response.isSuccessful()){
                    Log.d("check_ip", "onResponse: "+ response.body().getIp());
                    ip_them_su_kien = response.body().getIp();
                }
            }

            @Override
            public void onFailure(Call<IpNetworkModel> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadIdUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs",0);
        String id_user_str = sharedPreferences.getString("id_user","");
        String token = sharedPreferences.getString("token","o");
        Log.d("check_token", "loadIdUser: "+ token);
        token_au = token;
        if(id_user_str.equals("")){
            id_user =0;
        }else{
            id_user = Integer.parseInt(id_user_str);
        }
    }
    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    private void InitData(){
        callApiAddress();
        loadIdUser();
        deviceName = getDeviceName();
    }
    private void InitUI(){
        ProgressBar progressBar = fragmentDetailVideoBinding.progressBar;
        try{
            fragmentDetailVideoBinding.viewVideo.setVideoURI(Uri.parse(url_video));
            fragmentDetailVideoBinding.viewVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                }
            });
            fragmentDetailVideoBinding.viewVideo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                    if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                        progressBar.setVisibility(View.GONE);
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            fragmentDetailVideoBinding.viewVideo.start();
        }catch (Exception e){
            Log.d("TAG", "onBindViewHolder: " + e.getMessage());
        }
    }
    private void InitAction(){
        fragmentDetailVideoBinding.btnConfirm.setOnClickListener(v -> ChangeState());
    }
    private void ChangeState(){
        fragmentDetailVideoBinding.btnConfirm.setText("Swap");
        fragmentDetailVideoBinding.imgPerson1.setVisibility(View.VISIBLE);
        fragmentDetailVideoBinding.imgPerson1.setOnClickListener(v -> openDialog());
        fragmentDetailVideoBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uriResponse == null){
                    Toast.makeText(getContext(), "Upload your image", Toast.LENGTH_SHORT).show();
                }
                else {
                    fragmentDetailVideoBinding.view.setVisibility(View.VISIBLE);
                    fragmentDetailVideoBinding.progressBarHandle.setVisibility(View.VISIBLE);
                    CallApiSwap();
                }
            }
        });
    }
    private void openDialog() {
        dialogBinding = DialogBottomSheetSelectedHomeBinding.inflate(LayoutInflater.from(getContext()));
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        bottomSheetDialog.show();

        dialogBinding.btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        dialogBinding.btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    openStorage();
                } else {
                    openStorage();
                }
            }
        });
    }
    private void openCamera() {
        Toast.makeText(getContext(), "open camera", Toast.LENGTH_SHORT).show();
    }
    private void openStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launchSomeActivity.launch(intent);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK ){
                    Intent data = result.getData();
                    if(data != null){
                        selectedImageUri = data.getData();
                        fragmentDetailVideoBinding.imgPerson1.setImageURI(selectedImageUri);
                        bottomSheetDialog.dismiss();
                        postImageFile(selectedImageUri);
                    }
                }
            }
    );
    private void postImageFile(Uri selectedImageUri) {
        String filePath = getRealPathFromURI(requireContext(), selectedImageUri);
        File imageFile = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("src_img", imageFile.getName(), requestBody);
        ApiService apiService = RetrofitClient.getInstance(Server.DOMAIN4).getRetrofit().create(ApiService.class);
        Call<String> call = apiService.uploadImage(id_user,"src_nam",imagePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    uriResponse = response.body();
                    Toast.makeText(getContext(), "Upload successfully", Toast.LENGTH_SHORT).show();
                    Log.d("check_upload_image_your_video", "onResponse: "+ uriResponse);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("check_upload_image_your_video", "onFailure: "+ t.getMessage());
            }
        });
    }
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    private void CallApiSwap(){
        ApiService apiService = RetrofitClient.getInstance("").getRetrofit().create(ApiService.class);
        Call<EventVideo> call = apiService.getVideoSwap("Bearer " + token_au, id_video, deviceName, ip_them_su_kien, id_user, uriResponse, "Swap.mp4");
        Log.d("check_rs_api_swap", "CallApiSwap: " + token_au + id_video+deviceName+ip_them_su_kien+uriResponse+id_user);
        call.enqueue(new Callback<EventVideo>() {
            @Override
            public void onResponse(Call<EventVideo> call, Response<EventVideo> response) {
                if(response.isSuccessful() && response.body() != null){
                    String link_vid_swap = response.body().getSukien_video().link_vid_swap;
                    if(link_vid_swap != null){
                        fragmentDetailVideoBinding.cardView2.setVisibility(View.GONE);
                        fragmentDetailVideoBinding.btnConfirm.setVisibility(View.GONE);
                        fragmentDetailVideoBinding.imgPerson1.setVisibility(View.GONE);
                        fragmentDetailVideoBinding.view.setVisibility(View.GONE);
                        fragmentDetailVideoBinding.progressBarHandle.setVisibility(View.GONE);

                        fragmentDetailVideoBinding.cardViewRs.setVisibility(View.VISIBLE);
                        fragmentDetailVideoBinding.frameActionDownload.setVisibility(View.VISIBLE);
                        fragmentDetailVideoBinding.progressBarRs.setVisibility(View.VISIBLE);
                        fragmentDetailVideoBinding.viewVideoRs.setVideoURI(Uri.parse(link_vid_swap));

                        fragmentDetailVideoBinding.btnDownloadToDevice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                downloadVideo(link_vid_swap, response.body().getSukien_video().getTen_video());
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<EventVideo> call, Throwable t) {
                Log.d("check_rs_api_swap_failure", "onResponse: " + t.getMessage());
            }
        });
    }
    private void downloadVideo(String url, String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("Downloading video...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download.mp4");

        DownloadManager manager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);

        // Hiển thị Toast khi tải xuống thành công
        Toast.makeText(requireContext(), "Download starting", Toast.LENGTH_SHORT).show();
    }

}
