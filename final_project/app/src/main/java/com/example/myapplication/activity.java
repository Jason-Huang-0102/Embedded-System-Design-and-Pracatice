package com.example.myapplication;

public class activity {
    public String city;
    public String address;
    public String time;
    public String publisher;
    public String contents;
    public String number;
    public String id;
    public activity(){}
    public activity(String CITY,String ADDRESS,String TIME,String PUBLISHER,String CONTENTS,String NUMBER,String ID)
    {
        city=CITY;
        address=ADDRESS;
        time=TIME;
        publisher=PUBLISHER;
        contents=CONTENTS;
        number=NUMBER;
        id=ID;
    }
    public String getId(){return id;}
    public String getPublisher(){return publisher;}
    public String getTime(){return time;}
    public  String getCity()
    {
        return city;
    }
    public String getAddress() { return address; }
    public String  getContents(){return contents;}
    public String getNum(){return number;}

}
