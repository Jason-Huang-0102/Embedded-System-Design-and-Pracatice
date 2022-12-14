package com.example.myapplication.ui.prepare;

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

public class PrepareFragment extends Fragment {

    private PrepareViewModel prepareViewModel;
    private Button prepare_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        prepareViewModel =
                ViewModelProviders.of(this).get(PrepareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_prepare, container, false);
        prepare_btn = root.findViewById(R.id.equip_btn);
        prepare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).show2(false);
            }
        });
        return root;
    }
}