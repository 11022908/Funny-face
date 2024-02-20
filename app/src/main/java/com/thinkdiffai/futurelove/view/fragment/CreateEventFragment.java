package com.thinkdiffai.futurelove.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.DialogBottomSheetSelectedHomeBinding;
import com.thinkdiffai.futurelove.databinding.FragmentCreateEventsBinding;
import com.thinkdiffai.futurelove.service.api.ApiService;
import com.thinkdiffai.futurelove.service.api.RetrofitClient;
import com.thinkdiffai.futurelove.service.api.Server;

import java.io.File;
import java.util.jar.Attributes;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventsBinding fragmentCreateEventsBinding;
    private DialogBottomSheetSelectedHomeBinding dialogBinding;
    private BottomSheetDialog bottomSheetDialog;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;
    int check =0;
    String link_1;
    String link_2;
    int id_user;
    private Uri selectedImageUri;
    String uriResponse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentCreateEventsBinding = FragmentCreateEventsBinding.inflate(inflater, container, false);
        return fragmentCreateEventsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitUI();
    }

    private void InitUI(){
        fragmentCreateEventsBinding.shapeImageViewMale.setOnClickListener(v -> {
                    check = 1;
                    openDialog();
                }
        );
        fragmentCreateEventsBinding.shapeImageViewFemale.setOnClickListener(v ->
        {
            check = 2;
            openDialog();
        });

        fragmentCreateEventsBinding.btnCreateEvent.setOnClickListener(v -> {
            if(link_1 != null && link_2 != null){
                NavHostFragment.findNavController(CreateEventFragment.this).navigate(R.id.editEventFragment);
            }
            else
                Toast.makeText(getContext(), "Please choose image", Toast.LENGTH_SHORT).show();
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
                        fragmentCreateEventsBinding.shapeImageViewMale.setImageURI(selectedImageUri);
                        Log.d("log_selected_uri", ":l1 " + selectedImageUri);
                        bottomSheetDialog.dismiss();
                        postImageFile(selectedImageUri);
                    }else if(check == 2 && data != null){
                        Uri selectedImageUri = data.getData();
                        fragmentCreateEventsBinding.shapeImageViewFemale.setImageURI(selectedImageUri);
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
                fragmentCreateEventsBinding.shapeImageViewMale.setImageBitmap(bitmap);
                bottomSheetDialog.dismiss();
            }
            else if(check == 2){
                fragmentCreateEventsBinding.shapeImageViewFemale.setImageBitmap(bitmap);
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
