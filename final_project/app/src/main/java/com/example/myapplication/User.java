package com.example.myapplication;

public class User {
    public String id;
    public String password;
    public String name;


    public User(){}
    public User(String ID,String Password,String Name)
    {
        id=ID;
        password=Password;
        name=Name;
    }
    public  String getId()
    {
        return id;
    }
    public String getPassword()
    {
        return password;
    }
    public String getName()
    {return name;}

}
