package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.Collection;
import androidx.annotation.NonNull;
import  java.util.Calendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import static android.content.ContentValues.TAG;

public class chatroom extends Activity {
    public ListView lv;
    public EditText Message;
    public Button send;
    public FirebaseFirestore db;
    public boolean[] attend;
    public ArrayList arrayList ;
    public MESSAGE[] messages;
    public String number;
    public String id;
    public  String name;
    public String contents;
    public Integer num_message;
    public boolean finish;
    public boolean[] exist_in_activity;
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.chatroom);
        lv=findViewById(R.id.chatroom);
        Message=findViewById(R.id.messageText);
        send=findViewById(R.id.send);
        db = FirebaseFirestore.getInstance();
        attend=new boolean [100];
        arrayList=new ArrayList();
        messages=new MESSAGE[100];
        exist_in_activity=new boolean[100];
        Intent i0=getIntent();
        number=i0.getStringExtra("number");
        id=i0.getStringExtra("id");
        name=i0.getStringExtra("name");
        showMessage();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String  month = String.valueOf(c.get(Calendar.MONTH));
                String  day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                String  hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                String  minute = String.valueOf(c.get(Calendar.MINUTE));
                String second=String.valueOf(c.get(Calendar.SECOND));
                MESSAGE message=new MESSAGE(number,id,name,Message.getText().toString(),month,day,hour,minute,second,String.valueOf(num_message+1));
                db.collection("board").add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"addedaddedaddedaddedaddedaddedaddedadded");
                            //message.setText("註冊成功");
                        }
                        else{
                            Log.d(TAG,"Error");
                        }
                    }
                });
                arrayList.add( month+"/"+day+" "+hour+":"+minute+":"+"\n"+ id+ " ( "+name +" ) : "+Message.getText());
                Object list[] = arrayList.toArray();
                String[] stringArray = Arrays.copyOf(list, list.length, String[].class);
                lv.setAdapter(new ArrayAdapter<String>(chatroom.this, android.R.layout.simple_list_item_1, stringArray));
            }
        });
    }
    public void showMessage()
    {
        final CollectionReference activities=db.collection("board");
        Query query =activities.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                readData(task);
                while(finish==false)
                    ;
                Object list[] = arrayList.toArray();
                String[] stringArray = Arrays.copyOf(list, list.length, String[].class);
                lv.setAdapter(new ArrayAdapter<String>(chatroom.this, android.R.layout.simple_list_item_1, stringArray));
               // lv.setOnItemClickListener(itemClick);
            }
        });
    }
    private void readData(Task<QuerySnapshot> task)
    {
        finish=false;
        QuerySnapshot querySnapshot=task.isSuccessful()?task.getResult():null ;
        if(querySnapshot==null)
            Log.d(TAG,"NULL");
        else
            Log.d(TAG,"TASK");
        int i=0;
        String[] m=new String[100];
        //ArrayList<Integer> n=new ArrayList<>();
        num_message=0;
        for(DocumentSnapshot documentSnapshot :querySnapshot.getDocuments()) {
            String activity_number=documentSnapshot.toObject(MESSAGE.class).getNumber();
            String message_id=documentSnapshot.toObject(MESSAGE.class).getID();
            String message_name=documentSnapshot.toObject(MESSAGE.class).getName();
            String message_contents=documentSnapshot.toObject(MESSAGE.class).getContents();
            String month=documentSnapshot.toObject(MESSAGE.class).getMonth();
            String day=documentSnapshot.toObject(MESSAGE.class).getDay();
            String hour=documentSnapshot.toObject(MESSAGE.class).getHour();
            String minute=documentSnapshot.toObject(MESSAGE.class).getMinute();
            String second=documentSnapshot.toObject(MESSAGE.class).getContents();
            String message_number=documentSnapshot.toObject(MESSAGE.class).getMessage_number();
            num_message++;
            if(activity_number.compareTo(number)==0) {
                m[Integer.valueOf(message_number)]=month+"/"+day+" "+hour+":"+minute+":"+"\n"+message_id+ " ( "+message_name +" ) : "+message_contents;
                exist_in_activity[Integer.valueOf(message_number)]=true;
                //n.add(Integer.valueOf(message_number));
                //arrayList.add( month+"/"+day+" "+hour+":"+minute+":"+"\n"+message_name+ " ( "+message_id +" ) : "+message_contents);

                //events[i] = new activity(city, address, time, name, contents,number);
                //i++;
            }

        }
        for(int j=0;j<100;j++)
        {
            if(exist_in_activity[j]==true)
                arrayList.add(m[j]);
        }
        finish=true;
    }
}
