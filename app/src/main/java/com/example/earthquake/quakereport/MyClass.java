package com.example.earthquake.quakereport;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MyClass {
    private double Magnitude;
    private String Primary;
    private String Offset;
    private String Date;
    private String time;
    private String Url;

    MyClass(double magnitude,String location, long Time,String url){

        DecimalFormat format = new DecimalFormat("0.0");
           String    MMagnitude = format.format(magnitude);
           Magnitude=Double.parseDouble(MMagnitude);
        Date date=new Date(Time);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM DD, YYYY");
        String DateToDisplay=dateFormat.format(date);
        SimpleDateFormat TimeFormat=new SimpleDateFormat("h:mm a");
         String timeToDisplay=TimeFormat.format(date);
        time=timeToDisplay;
        Date=DateToDisplay;

       String[]  place=location.split("of");
       Offset= place[0]+"of";
       Primary=place[1];
       Url=url;

    }

    public String getUrl() {
        return Url;
    }

    public double getMagnitude() {
        return Magnitude;
    }

    public String getOffset() {
        return Offset;
    }

    public String getPrimary() {
        return Primary;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return time;
    }
}
