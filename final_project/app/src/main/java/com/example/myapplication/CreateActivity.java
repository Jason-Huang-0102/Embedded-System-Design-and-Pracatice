package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class CreateActivity extends Activity {
    public Button send;
    public TextView message;
    public EditText publisher;
    public EditText city;
    public EditText location;
    public EditText time;
    public EditText contents;
   // public String Publisher;
    public String City;
    public String Location;
    public String Time;
    public String Contents;
    public String name;
    public FirebaseFirestore db;
    public int number;
    public boolean finish;
    public  String user_id;
    public  String password;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);
        db = FirebaseFirestore.getInstance();
        count();
        Intent i0=getIntent();
        user_id=i0.getStringExtra("user_id");
        password=i0.getStringExtra("password");
        send=findViewById(R.id.send);
       // publisher=findViewById(R.id.publisher);
        city=findViewById(R.id.city);
        location=findViewById(R.id.location);
        time=findViewById(R.id.Time);
        contents=findViewById(R.id.contents);
        message=findViewById(R.id.message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while(finish==false)
                    ;
                name = getSharedPreferences("test",MODE_PRIVATE)
                        .getString("name", "");
                //Publisher=publisher.getText().toString();
                City=city.getText().toString();
                Location=location.getText().toString();
                Time=time.getText().toString();
                Contents =contents.getText().toString();
                activity newactivity=new activity(City,Location,Time,name,Contents,String.valueOf(number+1),user_id);
                db.collection("activity").add(newactivity).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"addedaddedaddedaddedaddedaddedaddedadded");
                            message.setText("創建成功");

                        }
                        else{
                            Log.d(TAG,"Error");
                        }
                    }
                });
                attend_follow_publish user=new attend_follow_publish(user_id,String.valueOf(number+1));
                db.collection("publish").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"addedaddedaddedaddedaddedaddedaddedadded");
                            Intent i1=new Intent(CreateActivity.this,MainActivity.class);
                            i1.putExtra("id",user_id);
                            i1.putExtra("password",password);
                            startActivity(i1);
                            //message.setText("註冊成功");
                        }
                        else{
                            Log.d(TAG,"Error");
                        }
                    }
                });
            }
        });
    }
    public void count() {
        number=0;

        CollectionReference users = db.collection("activity");
        Query query = users.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot querySnapshot = task.isSuccessful() ? task.getResult() : null;
                if (querySnapshot == null)
                    Log.d(TAG, "HEREHERE");
                else
                    Log.d(TAG, "TASK");
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    number++;
                }
                finish=true;
            }
        });
    }
}
