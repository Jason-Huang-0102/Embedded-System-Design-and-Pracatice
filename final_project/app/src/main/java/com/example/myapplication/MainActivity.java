package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static android.content.ContentValues.TAG;

import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.example.myapplication.ui.notifications.manage_activity.AttendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final int btn_num = 5;
    //public Button[] btns;
    //public Button test;
    //private Button tpe_btn;
    boolean login;
    private User user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User();
        login = false;
        Intent i2 = getIntent();
        //判斷是否登入
        if (i2.getStringExtra("id") == null || i2.getStringExtra("password") == null) {
            Log.d(TAG, "NONONONONO");
            Intent i0 = new Intent(this, login.class);
            startActivity(i0);

        } else if (i2.getStringExtra("id") != null && i2.getStringExtra("password") != null) {
            user.id = i2.getStringExtra("id");
            user.password = i2.getStringExtra("password");
            user.name=i2.getStringExtra("name");
            login = true;
        }
        /*NotificationsFragment notificationsFragment = new NotificationsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", user.getId());//这里的values就是我们要传的值
        bundle.putString("password", user.getPassword());//这里的values就是我们要传的值
        notificationsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();*/
        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
        pref.edit()
                .putString("id", user.getId())
                .commit();
        pref.edit()
                .putString("name", user.getName())
                .commit();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_prepare, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public void show(boolean search, String city) {
        if (login == false) {
            Intent i0 = new Intent(this, login.class);
            startActivity(i0);
        } else {
            Intent i0 = new Intent(MainActivity.this, ShowActivity.class);
            i0.putExtra("search", search);
            i0.putExtra("city", city);
            i0.putExtra("user_id", user.getId());
            startActivity(i0);
        }
    }
    public void create_form()
    {
        if(login==false) {
            Intent i0 = new Intent(this, login.class);
            startActivity(i0);
        }
        else {
            Intent i0 = new Intent(MainActivity.this, CreateActivity.class);
            i0.putExtra("user_id", user.getId());
            i0.putExtra("password", user.getPassword());
            startActivity(i0);
        }
    }

    public void show2(boolean b) {
        if (login == false) {
            Intent i0 = new Intent(this, login.class);
            startActivity(i0);
        } else {
            if (b)
                startActivity(new Intent(MainActivity.this, Information2.class));
            else
                startActivity(new Intent(MainActivity.this, Prepare2.class));
        }
    }
    public void showAttend(String city,String address ,String time ,String publisher,String contents,String user_id, String NUM ) {
        Intent i0 = new Intent(MainActivity.this, ShowDetail.class);
        i0.putExtra("city",city);
        i0.putExtra("address",address);
        i0.putExtra("time",time);
        i0.putExtra("publisher",publisher);
        i0.putExtra("contents",contents);
        i0.putExtra("user_id",user_id);
        i0.putExtra("activity_number",NUM);
        //i0.putExtra("Attendnumber",AttendNumber[Integer.valueOf(events[0].getNum())]);
        startActivity(i0);
    }
}
