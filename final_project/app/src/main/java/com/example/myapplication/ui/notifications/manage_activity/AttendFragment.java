package com.example.myapplication.ui.notifications.manage_activity;

//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ShowActivity;
import com.example.myapplication.ShowDetail;
import com.example.myapplication.User;
import com.example.myapplication.activity;
import com.example.myapplication.attend_follow_publish;
import com.example.myapplication.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class AttendFragment extends Fragment {

    public ListView lv;
    public FirebaseFirestore db;
    public boolean[] attend;
    public ArrayList arrayList ;
    public activity[] events;
    public String ID;
    public  String name;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attend, container, false);
        db = FirebaseFirestore.getInstance();
        lv=root.findViewById(R.id.Show_Attend_Activity);
        attend=new boolean [100];
        arrayList=new ArrayList();
        events=new activity[100];
        name= getActivity().getSharedPreferences("test", getContext().MODE_PRIVATE)
                .getString("name", "");
        ShowAttendActivity();
       // TextView textView = root.findViewById(R.id.text_attend);
        /*homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        //textView.setText("here is AttendFragment");
        return root;
    }
    public void ShowAttendActivity()
    {
        ID = getActivity().getSharedPreferences("test", getContext().MODE_PRIVATE)
                .getString("id", "");
        CollectionReference users=db.collection("attend");
        Query query =users.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot querySnapshot=task.isSuccessful()?task.getResult():null ;
                if(querySnapshot==null)
                    Log.d(TAG,"HEREHERE");
                else
                    Log.d(TAG,"TASK");
                for(DocumentSnapshot documentSnapshot :querySnapshot.getDocuments()) {
                    String id = documentSnapshot.toObject(attend_follow_publish.class).getId();
                    String num=documentSnapshot.toObject(attend_follow_publish.class).getNumber();
                    if (id.compareTo(ID)==0) {//有註冊過
                        attend[Integer.valueOf(num)]=true;
                        Log.d(TAG, "Attend" + id);
                    }
                    else {
                        Log.d(TAG, "Verified Failed" + id);
                    }
                }
                show();
            }
        });
    }
    public void show()
    {
        final CollectionReference activities=db.collection("activity");
        Query query =activities.limit(100);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                readData(task);
                Object list[] = arrayList.toArray();
                String[] stringArray = Arrays.copyOf(list, list.length, String[].class);
                lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringArray));
                lv.setOnItemClickListener(itemClick);
            }
        });
    }
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
            if(attend[Integer.valueOf(number)]==true) {
                arrayList.add("城市 : " + city + "   地點 : " + address + "\n時間 : " + time +
                        "         \n發起人 : " + user_ID +" ( "+publisher+" ) ");
                events[i] = new activity(city, address, time, publisher, contents,number,user_ID);
                i++;
            }
        }

    }
    private AdapterView.OnItemClickListener itemClick = new android.widget.AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            ((MainActivity) getActivity()).showAttend(events[position].getCity(),events[position].getAddress(),events[position].getTime(),
                    events[position].getPublisher(),events[position].getContents(),ID,events[position].getNum());
        }
    };
}
