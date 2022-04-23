package com.adara.yashsd.kadmus;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class DiaryEntry {
    private String name;
    private String day;
    private String date;
    private String month;
    private String year;
    private String time;
    private String mood;
    private String boby;
    private String uuid;

    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<String> hashtags = new ArrayList<>();

    public DiaryEntry(String name,String day,String date,String month,String year,String time,
                      String mood,String boby,String uuid,ArrayList<Bitmap> bitmaps,ArrayList<String> hashtags){
        this.name = name;
        this.day = day;
        this.date = date;
        this.month = month;
        this.year = year;
        this.time = time;
        this.mood = mood;
        this.boby = boby;
        this.uuid = uuid;

        this.bitmaps = bitmaps;
        this.hashtags = hashtags;
    }


    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }

    public String getMood() {
        return mood;
    }

    public String getBoby() {
        return boby;
    }

    public String getUuid() {
        return uuid;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }
}
