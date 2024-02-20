package com.thinkdiffai.futurelove.view.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thinkdiffai.futurelove.R;

public class ContainerEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btn_events = findViewById(R.id.btn_events);
        btn_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView5, VideoFragment.class, null)
                        .setReorderingAllowed(true).addToBackStack("name").commit();
            }
        });
    }
}