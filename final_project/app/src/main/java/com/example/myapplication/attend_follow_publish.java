package com.example.myapplication;

public class attend_follow_publish {
    public String id;//帳戶id
    public String number;//活動編號


    public attend_follow_publish(){}
    public attend_follow_publish(String ID,String Number)
    {
        id=ID;
        number=Number;
    }
    public  String getId()
    {
        return id;
    }
    public String getNumber()
    {
        return number;
    }
}
