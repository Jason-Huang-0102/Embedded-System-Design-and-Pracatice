package com.example.myapplication;

public class MESSAGE {
    public String number;
    public String id;
    public String name;
    public String contents;
    public String month;
    public String day;
    public String hour;
    public String minute;
    public String second;
    public String message_number;
    MESSAGE(){}
    MESSAGE(String NUMBER,String ID,String NAME,String CONTENTS,String MONTH,String DAY,String HOUR,String MINUTE,
            String SECOND,String MESSAGE_NUMBER)
    {
        number=NUMBER;
        id=ID;
        name=NAME;
        contents=CONTENTS;
        month=MONTH;
        day=DAY;
        hour=HOUR;
        minute=MINUTE;
        second=SECOND;
        message_number=MESSAGE_NUMBER;
    }
    public String getMonth(){return month;}
    public String getDay(){return day;}
    public String getHour(){return hour;}
    public String getMinute(){return minute;}
    public String getSecond(){return second;}
    public String getMessage_number(){return message_number;}
    public String getNumber()
    {
        return number;
    }
    public String getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public String getContents()
    {
        return contents;
    }
}
