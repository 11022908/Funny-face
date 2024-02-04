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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thinkdiffai.futurelove.databinding.DialogBottomSheetSelectedHomeBinding;
import com.thinkdiffai.futurelove.databinding.Swap2ImageBinding;
import com.thinkdiffai.futurelove.model.IpNetworkModel;
import com.thinkdiffai.futurelove.model.Sukien2Image;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.RetrofitIp;
import com.thinkdiffai.futurelove.service.api.Server;
import com.thinkdiffai.futurelove.view.fragment.activity.MainActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Swap2Image extends Fragment {
    private Swap2ImageBinding swap2ImageBinding;
    String link_1;
    String link_2;

    int check =0;
    String uriResponse;
    String deviceAddEvent;
    String ipAddEvent;
    String token_au;
    String link_result;
    int id_user;
    private Uri selectedImageUri;
    private BottomSheetDialog bottomSheetDialog;
    private DialogBottomSheetSelectedHomeBinding dialogBinding;

    private SharedPreferences sharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        swap2ImageBinding = Swap2ImageBinding.inflate(inflater, container, false);
        return swap2ImageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitData();
        InitUI();
    }
    private void InitUI(){
        swap2ImageBinding.imgPerson1.setOnClickListener(v -> {
                    check = 1;
                    openDialog();
                    }
                );
        swap2ImageBinding.imgPerson2.setOnClickListener(v ->
        {
            check = 2;
            openDialog();
        });
        swap2ImageBinding.btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swap2ImageBinding.progressBarHandle.setVisibility(View.VISIBLE);
                CallApi();
            }
        });
        swap2ImageBinding.btnDownloadToDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadImage(link_result, "Image");
            }
        });
    }
    private void downloadImage(String url, String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("Downloading image...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download.jpg");

        DownloadManager manager = (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);

        // Hiển thị Toast khi tải xuống thành công
        Toast.makeText(requireContext(), "Download starting", Toast.LENGTH_SHORT).show();
    }
    private void InitData(){
        loadDataUser();
        deviceAddEvent = getDeviceName();
        callApiAddress();
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
    private void callApiAddress() {
        ApiService apiService = RetrofitIp.getInstance(Server.GET_CITY_NAME_FROM_IP).getRetrofit().create(ApiService.class);
        Call<IpNetworkModel> call = apiService.getIpApiResponse();
        call.enqueue(new Callback<IpNetworkModel>() {
            @Override
            public void onResponse(Call<IpNetworkModel> call, Response<IpNetworkModel> response) {
                if(response.body()!= null && response.isSuccessful()){
                    Log.d("check_ip", "onResponse: "+ response.body().getIp());
                    ipAddEvent = response.body().getIp();
                }
            }

            @Override
            public void onFailure(Call<IpNetworkModel> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataUser(){
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        token_au = sharedPreferences.getString("token", "");
        id_user = Integer.parseInt(sharedPreferences.getString("id_user", "null"));
    }
    private void CallApi(){
        ApiService apiService = RetrofitClient.getInstance("").getRetrofit().create(ApiService.class);
        Call<Sukien2Image> call = apiService.GetResultImageSwap("Bearer " + token_au, link_1, link_2,deviceAddEvent, ipAddEvent, id_user);
        Log.d("check_info_user", "CallApi: " + "Bearer " + token_au + ", "+deviceAddEvent+ ", "+ipAddEvent+ ", "+id_user+ ", "+link_1+ ", "+link_2);
        call.enqueue(new Callback<Sukien2Image>() {
            @Override
            public void onResponse(Call<Sukien2Image> call, Response<Sukien2Image> response) {
                if(response.isSuccessful() && response.body() != null){
                    swap2ImageBinding.viewAction.setVisibility(View.GONE);
                    swap2ImageBinding.viewResult.setVisibility(View.VISIBLE);
                    link_result = response.body().getEventSwap2image().link_da_swap;
                    Glide.with(getContext()).load(response.body().getEventSwap2image().link_da_swap).into(swap2ImageBinding.imgResult);

                }
                else {
                    Log.d("check_link_result", "onResponse: nullaasfddxgfff");
                }
            }
            @Override
            public void onFailure(Call<Sukien2Image> call, Throwable t) {
                Log.d("check_failure_swap_image", "onFailure: ");
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
            Log.d("check_file_path", "getRealPathFromURI: " + filePath);
            return filePath;
        }
        return null;
    }
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
                    if(link_1 == null){
                        link_1 = uriResponse;
                    }
                    else if(link_2 == null){
                        link_2 = uriResponse;
                    }
                    if(link_1 != null && link_2 != null){
                        Toast.makeText(getContext(), "Upload successfully", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("check_response_post_image_file", "onResponse: " + response.body() + " link_1: " + link_1 + " link_2: " + link_2);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("check_upload_image_your_video", "onFailure: "+ t.getMessage());
            }
        });
    }
    ActivityResultLauncher<Intent> imgPerson = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK ){
                    Intent data = result.getData();
                    if(data != null && check == 1){
                        Uri selectedImageUri = data.getData();
                        swap2ImageBinding.imgPerson1.setImageURI(selectedImageUri);
                        Log.d("log_selected_uri", ":l1 " + selectedImageUri);
                        bottomSheetDialog.dismiss();
                        postImageFile(selectedImageUri);
                    }else if(check == 2 && data != null){
                        Uri selectedImageUri = data.getData();
                        swap2ImageBinding.imgPerson2.setImageURI(selectedImageUri);
                        Log.d("log_selected_uri", ":l2 " + selectedImageUri);
                        bottomSheetDialog.dismiss();
                        postImageFile(selectedImageUri);
                    }
                }
            }
    );

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(check == 1){
                swap2ImageBinding.imgPerson1.setImageBitmap(bitmap);
                bottomSheetDialog.dismiss();
            }
            else if(check == 2){
                swap2ImageBinding.imgPerson2.setImageBitmap(bitmap);
                bottomSheetDialog.dismiss();
            }
        }
    }

    private void openStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imgPerson.launch(intent);
    }

    private void openDialog() {
        dialogBinding = DialogBottomSheetSelectedHomeBinding.inflate(LayoutInflater.from(getContext()));
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        bottomSheetDialog.show();

        dialogBinding.btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
                }else{
                    openCamera();
                }
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

}
