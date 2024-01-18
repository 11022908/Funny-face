package com.thinkdiffai.futurelove.view.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.thinkdiffai.futurelove.R;
import com.thinkdiffai.futurelove.databinding.MenuProfileBinding;
import com.thinkdiffai.futurelove.view.fragment.activity.MainActivity;
import com.thinkdiffai.futurelove.view.fragment.activity.SignInSignUpActivity;
import com.thinkdiffai.futurelove.view.fragment.dialog.MyOwnDialogFragment;

import java.util.Objects;

public class MenuProfileFragment extends Fragment {
    private MenuProfileBinding menuProfileBinding;
    private SharedPreferences sharedPreferences;
    private MyOwnDialogFragment myOwnDialogFragment;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        menuProfileBinding = MenuProfileBinding.inflate(inflater, container, false);
        try{
            InitUI();
        }catch (Exception e){
            Log.e("Exception init view", "onCreateView: menu profile");
        }
        return menuProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void InitUI(){
//        nhấn nút log out
        clickLogout();
//        lấy dữ liệu tài khoản đang được đăng nhập
        GetDataUser();
//      khởi tạo sự kiện chuyển trang cho các button
        menuProfileBinding.btnUserDetail.setOnClickListener(view -> navigateUserDetailFragment());
        menuProfileBinding.rltVideoCollections.setOnClickListener(view -> navigateVideoCollection());
        menuProfileBinding.rltImageCollections.setOnClickListener(view -> navigateImageCollection());
        menuProfileBinding.rltMyEvents.setOnClickListener(view -> navigateMyEvents());
    }
//












//    lấy thông tin người dùng được đăng nhập
    private void GetDataUser(){
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("id_user", null);
        String user_name_login = sharedPreferences.getString("name_user", null);
        String email_login = sharedPreferences.getString("email_user", null);
        String link_avatar = sharedPreferences.getString("avatar", null);
        Log.d("id_user_login", "GetDataUser: " + user_id);
//        set data user for menu profile binding
        menuProfileBinding.tvUserName.setText(user_name_login);

    }


//    click button to log out
    private void clickLogout() {
        Log.d("MenuProfileFragment", "clickLogout: ");
        menuProfileBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Show dialog to ask logout confirmation
            myOwnDialogFragment = new MyOwnDialogFragment(
                    "Log-out now?",
                    "Are you sure to quit the app?",
                    R.drawable.ic_warning,
                    new MyOwnDialogFragment.MyOwnDialogListener() {
                        @Override
                        public void onConfirm() {
                            // Navigate to Login Fragment
                            NavigateToLoginAndSignOutActivity();
                            // Update the LOGIN_STATE
                            // Change the LOGIN_STATE is FALSE - Not to keep the login state
                            changeLoginState();
                        }
                    }
            );
            mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                myOwnDialogFragment.show(mainActivity.getSupportFragmentManager(), "logout_dialog");
            }

        }
    });
    }

    private void changeLoginState() {
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LOGIN_STATE", false);
        editor.apply();
    }

    private void NavigateToLoginAndSignOutActivity() {
        Intent i = new Intent(getActivity(), SignInSignUpActivity.class);
        startActivity(i);
    }

    private void navigateUserDetailFragment(){
        NavHostFragment.findNavController(MenuProfileFragment.this).navigate(R.id.action_menuProfileFragment_to_userDetailFragment);
    }
    private void navigateVideoCollection(){
        NavHostFragment.findNavController(MenuProfileFragment.this).navigate(R.id.listVideoFragment);
    }
    private void navigateImageCollection(){
        NavHostFragment.findNavController(MenuProfileFragment.this).navigate(R.id.action_menuProfileFragment_to_listImageFragment);
    }
    private void navigateMyEvents(){
        NavHostFragment.findNavController(MenuProfileFragment.this).navigate(R.id.action_menuProfileFragment_to_myEventFragment);
    }
}
