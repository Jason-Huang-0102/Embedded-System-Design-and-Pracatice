package com.example.myapplication.ui.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.ui.notifications.manage_activity.AttendFragment;
import com.example.myapplication.ui.notifications.manage_activity.FollowFragment;
import com.example.myapplication.ui.notifications.manage_activity.SponsorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationsFragment extends Fragment  {
    private FragmentTransaction fragmentTransaction ;
    private AttendFragment attendFragment;
    private FollowFragment followFragment;
    private SponsorFragment sponsorFragment;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_activity, container, false);
        //String id = (String)getArguments().get("id");
        //String password = (String)getArguments().get("password");
        final TextView textView = root.findViewById(R.id.text_test);
        //BottomNavigationView navView = root.findViewById(R.id.nav_view2);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sponsor, R.id.navigation_attend, R.id.navigation_follow)
                .build();
        // NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment2);
        //NavController navController = Navigation.findNavController(root.findViewById(R.id.nav_host_fragment2));
        //NavController navController = Navigation.findNavController(root);
        //NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);

        BottomNavigationView navView = root.findViewById(R.id.nav_view2);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = getChildFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_sponsor:
                        if (sponsorFragment == null) sponsorFragment = new SponsorFragment();
                        if (followFragment != null) fragmentTransaction.hide(followFragment);
                        if (attendFragment != null) fragmentTransaction.hide(attendFragment);
                        if (sponsorFragment.isAdded()) {
                            fragmentTransaction.show(sponsorFragment);
                        } else {
                            fragmentTransaction.add(R.id.nav_host_fragment2, sponsorFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_attend:
                        if (attendFragment == null) attendFragment = new AttendFragment();
                        if (followFragment != null) fragmentTransaction.hide(followFragment);
                        if (sponsorFragment != null) fragmentTransaction.hide(sponsorFragment);
                        if (attendFragment.isAdded()) {
                            fragmentTransaction.show(attendFragment);
                        } else {
                            fragmentTransaction.add(R.id.nav_host_fragment2, attendFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_follow:
                        if (followFragment == null) followFragment = new FollowFragment();
                        if (sponsorFragment != null) fragmentTransaction.hide(sponsorFragment);
                        if (attendFragment != null) fragmentTransaction.hide(attendFragment);
                        if (followFragment.isAdded()) {
                            fragmentTransaction.show(followFragment);
                        } else {
                            fragmentTransaction.add(R.id.nav_host_fragment2, followFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                }
                return false;
            }
        });
       /* notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}