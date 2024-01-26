package com.thinkdiffai.futurelove.view.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

public class DetailVideoTemplateFragment extends Fragment {
    private FragmentDetailVideoBinding fragmentDetailVideoBinding;
    int id_video;
    String name_video;
    String url_video;
    private DialogBottomSheetSelectedHomeBinding dialogBinding;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private BottomSheetDialog bottomSheetDialog;
    private Uri selectedImageUri;

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
            id_video = Integer.parseInt(bundle.getString("id_video"));
            name_video = bundle.getString("name_video");
            url_video = bundle.getString("url_video");
            Log.d("check_bundle_get_data", id_video + name_video + url_video);
        }
        else {
            Log.d("check_bundle", "Bundle is null");
        }
        InitUI();
        InitAction();
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
}
