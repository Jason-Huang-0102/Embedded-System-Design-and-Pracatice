package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class login extends Activity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public TextView message;
    public EditText inputAccnum;
    public EditText inputPassword;
    public EditText inputName;
    public Button send;
    public Button create;
    public  FirebaseFirestore db;
    public TextView nickname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //nickname.findViewById(R.id.nameText);
        send=findViewById(R.id.dengLuButton);
        create=findViewById(R.id.zhuCeButton);
        inputAccnum=findViewById(R.id.zhangHaoText);
        inputPassword=findViewById(R.id.miMaText);
        inputName=findViewById(R.id.nameText);
        message=findViewById(R.id.message);
        db = FirebaseFirestore.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    verified(inputAccnum.getText().toString(),inputPassword.getText().toString());
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID=inputAccnum.getText().toString();
                String password=inputPassword.getText().toString();
                String name=inputName.getText().toString();
                addNewMember(ID,password,name);
            }
        });
    }
    public void verified(final String ID, final String PASSWORD)
    {
        //nickname.setVisibility(View.INVISIBLE);
        CollectionReference users=db.collection("accounts");
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
                    String id = documentSnapshot.toObject(User.class).getId();
                    String password = documentSnapshot.toObject(User.class).getPassword();
                    String name = documentSnapshot.toObject(User.class).getName();
                    if (id.compareTo(ID)==0  && password.compareTo(PASSWORD)==0) {//有註冊過
                        Log.d(TAG, "Verified Successful" + id);
                        GoToMainActivity(id,password,name);
                    }
                    else {
                        Log.d(TAG, "Verified Failed" + id);
                        message.setText("您尚未註冊");
                    }
                }
            }
        });
    }

    public void addNewMember(String ID,String password,String name)//新增用戶
    {
        User user=new User(ID,password,name);
        db.collection("accounts").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"addedaddedaddedaddedaddedaddedaddedadded");
                    message.setText("註冊成功");
                }
                else{
                    Log.d(TAG,"Error");
                }
            }
        });
    }
    public void GoToMainActivity(String id,String password,String name)
    {
        Intent i0=new Intent(this,MainActivity.class);
        i0.putExtra("id",id);
        i0.putExtra("password",password);
        i0.putExtra("name",name);
        startActivity(i0);
    }
}
