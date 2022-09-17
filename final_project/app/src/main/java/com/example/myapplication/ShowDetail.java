package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import static android.content.ContentValues.TAG;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ShowDetail extends Activity {
    public TextView CONTENTS;
    public Button attend_btn;
    public Button follow_btn;
    public Button chatroom_btn;
    public String city;
    public String address;
    public String time;
    public String publisher;
    public String contents;
    public Integer Attendnumber;
    public String number;
    public String id;
    public String name;
    public FirebaseFirestore db;
    public TextView message;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mPostReference;
    public int AttendNumber[];
    String current_user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity_detail);
        db = FirebaseFirestore.getInstance();
        //activitykind.getAttendNum(db);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("/");
        Attendnumber=0;
        AttendNumber =new int[100];
        CONTENTS = findViewById(R.id.content);
        attend_btn = findViewById(R.id.attend_button);
        follow_btn = findViewById(R.id.follow_button);
        chatroom_btn = findViewById(R.id.chatroom_button);
        message = findViewById(R.id.message);
        name = getSharedPreferences("test", MODE_PRIVATE)
                .getString("name", "");
        Intent i0 = getIntent();
        number = i0.getStringExtra("activity_number");
        mPostReference = mFirebaseInstance.getReference("/activity" + number);
        city = i0.getStringExtra("city");
        address = i0.getStringExtra("address");
        time = i0.getStringExtra("time");
        publisher = i0.getStringExtra("publisher");
        contents = i0.getStringExtra("contents");
        id = i0.getStringExtra("user_id");
        current_user_id= getSharedPreferences("test",MODE_PRIVATE)
                .getString("id", "");
        getAttendNum();


        attend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendnumber++;
                //mFirebaseDatabase.child("activity" + Attendnumber).child("attend").setValue(Attendnumber);
                CONTENTS.setText("城市 : " + city + "\n地點 : " + address + "\n時間 : " + time + "\n發起人 : " + id+" ( "+publisher+" ) "
                        + "\n活動內容 : " + contents + "\n\n參加人數 : " + String.valueOf(Attendnumber));
                attend_follow_publish user = new attend_follow_publish(current_user_id, number);
                db.collection("attend").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "addedaddedaddedaddedaddedaddedaddedadded");
                            message.setText("報名成功");
                        } else {
                            Log.d(TAG, "Error");
                        }
                    }
                });
                //activitykind.getAttendNum(db);
            }
        });
        follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFirebaseDatabase.child("activity" + Attendnumber).child("attend").setValue(Attendnumber);

                attend_follow_publish user = new attend_follow_publish(current_user_id, number);
                db.collection("follow").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "addedaddedaddedaddedaddedaddedaddedadded");
                            message.setText("關注成功");
                        } else {
                            Log.d(TAG, "Error");
                        }
                    }
                });
                //activitykind.getAttendNum(db);
            }
        });

        chatroom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i0=new Intent(ShowDetail.this,chatroom.class);
                i0.putExtra("number",number);
                i0.putExtra("id",current_user_id);
                i0.putExtra("name",name);
                startActivity(i0);
            }
        });


    }
    public void getAttendNum()
    {
        final CollectionReference attendnumber=db.collection("attend");
        Query query =attendnumber.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot querySnapshot=task.isSuccessful()?task.getResult():null ;
                if(querySnapshot==null)
                    Log.d(TAG,"NULL");
                else
                    Log.d(TAG,"TASK");
                for(DocumentSnapshot documentSnapshot :querySnapshot.getDocuments()) {
                    String numofactivity=documentSnapshot.toObject(attend_follow_publish.class).getNumber();
                    int numactivity = Integer.valueOf(numofactivity);
                    AttendNumber[numactivity]++;
                }
                Attendnumber=AttendNumber[Integer.valueOf(number)];
                CONTENTS.setText("城市 : " + city + "\n地點 : " + address + "\n時間 : " + time + "\n發起人 : " + id+" ( "+publisher+" ) "
                        + "\n活動內容 : " + contents + "\n\n參加人數 : " + String.valueOf(Attendnumber));
            }
        });
    }
}

