package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ShowActivity extends Activity {
    public final int beach_num = 100;
    ListView lv;
    String CITY;
    public FirebaseFirestore db;
    boolean search;
    String user_id;
    String name;
    //String number;/////////活動編號
    public int AttendNumber[];
    public ArrayList arrayList ;
    public activity[] events;
    //public String[] test = {"Start the Game", "Help", "About the Game"};
    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity);
        lv = (ListView) findViewById(R.id.Show_Activity);
        db = FirebaseFirestore.getInstance();
        events=new activity[beach_num];
        arrayList=new ArrayList();
        Intent i2=getIntent();
        AttendNumber=new int[beach_num];
        CITY=i2.getStringExtra("city");
        search=i2.getBooleanExtra("search",false);
        user_id=i2.getStringExtra("user_id");
        ShowAllActivity();
        //getAttendNum();
    }

    public void ShowAllActivity()
    {

        final CollectionReference activities=db.collection("activity");
        Query query =activities.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                readData(task);
                Object list[] = arrayList.toArray();
                String[] stringArray = Arrays.copyOf(list, list.length, String[].class);
                lv.setAdapter(new ArrayAdapter<String>(ShowActivity.this, android.R.layout.simple_list_item_1, stringArray));
                lv.setOnItemClickListener(itemClick);
            }
        });
    }



    public AdapterView.OnItemClickListener itemClick = new android.widget.AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            /*switch(position){
                case 0:
                    Intent i0 = new Intent(ShowActivity.this, ShowDetail.class);
                    i0.putExtra("city",events[0].getCity());
                    i0.putExtra("address",events[0].getAddress());
                    i0.putExtra("time",events[0].getTime());
                    i0.putExtra("publisher",events[0].getPublisher());
                    i0.putExtra("contents",events[0].getContents());
                    i0.putExtra("user_id",user_id);
                    i0.putExtra("activity_number",events[0].getNum());
                    //i0.putExtra("Attendnumber",AttendNumber[Integer.valueOf(events[0].getNum())]);
                    startActivity(i0);
                    break;
            }*/
            Intent i0 = new Intent(ShowActivity.this, ShowDetail.class);
            i0.putExtra("city",events[position].getCity());
            i0.putExtra("address",events[position].getAddress());
            i0.putExtra("time",events[position].getTime());
            i0.putExtra("publisher",events[position].getPublisher());
            i0.putExtra("contents",events[position].getContents());
            i0.putExtra("user_id",events[position].getId());
            i0.putExtra("activity_number",events[position].getNum());
            //i0.putExtra("Attendnumber",AttendNumber[Integer.valueOf(events[0].getNum())]);
            startActivity(i0);
        }
    };

    private void readData(Task<QuerySnapshot> task)
    {
        QuerySnapshot querySnapshot=task.isSuccessful()?task.getResult():null ;
        if(querySnapshot==null)
            Log.d(TAG,"NULL");
        else
            Log.d(TAG,"TASK");
        int i=0;
        for(DocumentSnapshot documentSnapshot :querySnapshot.getDocuments()) {
            String city = documentSnapshot.toObject(activity.class).getCity();
            String address = documentSnapshot.toObject(activity.class).getAddress();
            String time=documentSnapshot.toObject(activity.class).getTime();
            String publisher=documentSnapshot.toObject(activity.class).getPublisher();
            String contents=documentSnapshot.toObject(activity.class).getContents();
            String number=documentSnapshot.toObject(activity.class).getNum();
            String user_ID=documentSnapshot.toObject(activity.class).getId();
            name = getSharedPreferences("test", MODE_PRIVATE)
                    .getString("name", "");
            String id = getSharedPreferences("test", MODE_PRIVATE)
                    .getString("id", "");
            if(search==false||(search==true&&CITY.compareTo(city)==0)) {
                arrayList.add("城市 : " + city + "   地點 : " + address + "\n時間 : " + time +
                        "         \n發起人 : " + user_ID+ " ( "+publisher +" ) ");
                events[i] = new activity(city, address, time, publisher, contents,number,user_ID);
                i++;
            }
        }

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
            }
        });
    }

}
