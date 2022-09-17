package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ShowActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public final int btn_num = 5;
    public Button[] btns;
    public Button create_activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        btns = new Button[btn_num];
        btns[0] = (Button) root.findViewById(R.id.show_all_btn);
        btns[1] = (Button) root.findViewById(R.id.TPE);
        btns[2]=(Button)root.findViewById(R.id.TAO);
        btns[3]=(Button)root.findViewById(R.id.TAI);
        btns[4]=(Button)root.findViewById(R.id.KAO);
        create_activity=(Button)root.findViewById(R.id.create_activity);
        create_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).create_form();
            }
        });
        for(int i = 0; i < btn_num; i++)
            btns[i].setOnClickListener(showActivity);
        return root;
    }

    private View.OnClickListener showActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean search = !(v.getId() == R.id.show_all_btn);
            String city = new String();
            for (int i = 0; i < btn_num; ++i) {
                if (btns[i].getId() == v.getId()) {
                    city = btns[i].getText().toString();
                    break;
                }
            }
            ((MainActivity) getActivity()).show(search, city);
        }
    };

}