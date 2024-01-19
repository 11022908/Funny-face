package com.thinkdiffai.futurelove.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.FragmentEditProfileBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProfileFragment extends Fragment {
    private static final int REQUEST_CODE_PICK_FILE = 0;
    private FragmentEditProfileBinding fragmentEditProfileBinding;
    private Dialog dialog;
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false);
        InitUI();
        return fragmentEditProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void InitUI(){
        ComebackMenu();
        UploadImage();
    }
    private void ComebackMenu(){
        fragmentEditProfileBinding.btnComeBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
    private void NavToUploadImage(){

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
        dialog.dismiss();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                activityResultLauncher.launch(data);
                fragmentEditProfileBinding.avatarUser.setImageURI(uri);

            }
        }
    }
    private void UploadImage(){
        fragmentEditProfileBinding.btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                Log.d("check_dialog", "onClick: ");
                dialog.setContentView(R.layout.dialog_bottom_sheet_selected_home);

                ImageButton btnGallery = dialog.findViewById(R.id.btn_select_image);
                ImageButton btnOpenCamera = dialog.findViewById(R.id.btn_open_camera);
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "gallery", Toast.LENGTH_SHORT).show();
                        openGallery();

                    }
                });
                btnOpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }
    @SuppressLint("Range")
    private String getImagePath(Uri uri, Context context){
        String res = null;
        if(uri.getScheme().equals("context")){
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
            if(res == null){
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if(cutt != -1){
                    res = res.substring(cutt + 1);
                }
            }
        }
        return res;
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        Log.d("check_path_image", "onActivityResult: " + getImagePath(uri, getContext()));
                    }
                }
            }
    );
//    private void openCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Tạo một file để lưu ảnh chụp
//            File photoFile = createImageFile();
//            if (photoFile != null) {
//                Uri uri = FileProvider.getUriForFile(getContext(), "com.example.myapp.fileprovider", photoFile);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(intent, REQUEST_CODE_CAMERA);
//            }
//        }
//    }

//    private PackageManager getPackageManager() {
//        return null;
//    }

//    private File createImageFile() {
//        // Tạo tên file ảnh duy nhất
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "IMG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        try {
//            // Tạo file ảnh trống
//            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
//            return imageFile;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private File getExternalFilesDir(String directoryPictures) {
//        return null;
//    }





//    private String getFileNameFromUri(Uri uri) {
//        String fileName = null;
//        if (uri.getScheme().equals("content")) {
//            Uri.Builder cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                if (nameIndex != -1) {
//                    fileName = cursor.getString(nameIndex);
//                }
//                cursor.close();
//            }
//        } else if (uri.getScheme().equals("file")) {
//            fileName = new File(uri.getPath()).getName();
//        }
//        return fileName;
//    }
//
//    private Uri.Builder getContentResolver() {
//        return null;
//    }


}
