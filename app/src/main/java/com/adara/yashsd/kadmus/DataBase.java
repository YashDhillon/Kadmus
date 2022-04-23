package com.adara.yashsd.kadmus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Enteries.db";
    private final static String TABLE_NAME_1 = "enteries";
    private final static String TABLE_NAME_2 = "hashtags";
    private final static String TABLE_NAME_3 = "images";
    private final static String TABLE_NAME_4 = "temp_images";

    private final static String T_1_COL_1 = "ID";
    private final static String T_1_COL_2 = "NAME";
    private final static String T_1_COL_3 = "DAY_NAME";
    private final static String T_1_COL_4 = "DAY";
    private final static String T_1_COL_5 = "MONTH";
    private final static String T_1_COL_6 = "YEAR";
    private final static String T_1_COL_7 = "TIME";
    private final static String T_1_COL_8 = "MOOD";
    private final static String T_1_COL_9 = "BODY";
    private final static String T_1_COL_10 = "UUID";

    private final static String T_2_COL_1 = "ID";
    private final static String T_2_COL_2 = "HASHTAG";
    private final static String T_2_COL_3 = "UUID";

    private final static String T_3_COL_1 = "ID";
    private final static String T_3_COL_2 = "IMAGE";
    private final static String T_3_COL_3 = "UUID";

    private final static String T_4_COL_1 = "ID";
    private final static String T_4_COL_2 = "IMAGE";
    private final static String T_4_COL_3 = "URI";

    private SQLiteDatabase db;

    private Context context = null;

    public DataBase(Context context) {
        super(context,DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("create table " + TABLE_NAME_1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DAY_NAME TEXT,DAY TEXT,MONTH TEXT,YEAR TEXT,TIME TEXT,MOOD TEXT,BODY TEXT,UUID TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,HASHTAG TEXT,UUID TEXT)");
        db.execSQL("create table " + TABLE_NAME_3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,IMAGE BLOB,UUID TEXT)");
        db.execSQL("create table " + TABLE_NAME_4 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,IMAGE BLOB,URI TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_4);
        onCreate(db);
    }

    public boolean insertDataInTable_1 (String name,String day_name,String day,String month,String year,String time,String mood,String body,String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_1_COL_2,name);
        contentValues.put(T_1_COL_3,day_name);
        contentValues.put(T_1_COL_4,day);
        contentValues.put(T_1_COL_5,month);
        contentValues.put(T_1_COL_6,year);
        contentValues.put(T_1_COL_7,time);
        contentValues.put(T_1_COL_8,mood);
        contentValues.put(T_1_COL_9,body);
        contentValues.put(T_1_COL_10,uuid);
        long success = db.insert(TABLE_NAME_1,null,contentValues);
        if(success == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertDataInTable_2 (String hashtag,String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_2_COL_2,hashtag);
        contentValues.put(T_2_COL_3,uuid);
                long success = db.insert(TABLE_NAME_2,null,contentValues);
        if(success == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertDataInTable_3 (byte[] image, String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_3_COL_2,image);
        contentValues.put(T_3_COL_3,uuid);
        long success = db.insert(TABLE_NAME_3,null,contentValues);
        if(success == -1){
            Toast.makeText(context, "Save fail", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }


    public Cursor viewAllDataInTable_1()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME_1,null);
    }

    public Cursor viewAllDataInTable_2()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME_2,null);
    }

    public Cursor viewAllDataInTable_3()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME_3,null);
    }

    public Cursor viewAllDataInTable_4()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME_4,null);
    }


    public boolean updateDataInTable_1(String id,String name,String day_name,String day,String month,String year,String time,String mood,String body,String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_1_COL_1,id);
        contentValues.put(T_1_COL_2,name);
        contentValues.put(T_1_COL_3,day_name);
        contentValues.put(T_1_COL_4,day);
        contentValues.put(T_1_COL_5,month);
        contentValues.put(T_1_COL_6,year);
        contentValues.put(T_1_COL_7,time);
        contentValues.put(T_1_COL_8,mood);
        contentValues.put(T_1_COL_9,body);
        contentValues.put(T_1_COL_10,uuid);
        int effected = db.update(TABLE_NAME_1,contentValues,"ID = ?",new String[] { id } );
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean updateDataInTable_2(String id,String hashtag,String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_2_COL_1,id);
        contentValues.put(T_2_COL_2,hashtag);
        contentValues.put(T_2_COL_3,uuid);
        int effected = db.update(TABLE_NAME_2,contentValues,"ID = ?",new String[] { id } );
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean updateDataInTable_3(String id,String image,String uuid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_3_COL_1,id);
        contentValues.put(T_3_COL_2,image);
        contentValues.put(T_3_COL_3,uuid);
        int effected = db.update(TABLE_NAME_3,contentValues,"ID = ?",new String[] { id } );
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean deleteDataInTable_1(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int effected = db.delete(TABLE_NAME_1,"UUID = ?",new String[] { uuid });
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean deleteDataInTable_2(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int effected = db.delete(TABLE_NAME_2,"UUID = ?",new String[] { uuid });
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean deleteDataInTable_3(String uuid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int effected = db.delete(TABLE_NAME_3,"UUID = ?",new String[] { uuid });
        if(effected>0)
            return true;
        else
            return false;
    }

    public boolean deleteDataInTable_4(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int effected = db.delete(TABLE_NAME_4,"ID = ?",new String[] { id });
        if(effected>0)
            return true;
        else
            return false;
    }

    public ArrayList<String> idTable_1()
    {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                names.add(res.getString(0));
            }
            while (res.moveToNext());
        }
        else
        {
            names.add("");
        }
        res.close();
        return names;
    }

    public ArrayList<String> nameTable_1()
    {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                names.add(res.getString(1));
            }
            while (res.moveToNext());
        }
        else
        {
            names.add("");
        }
        res.close();
        return names;
    }

    public ArrayList<String> dayNameTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(2));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> dayTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(3));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> monthTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(4));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> yearTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(5));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> timeTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(6));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> moodTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(7));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> bodyTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(8));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> uuidTable_1()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_1,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(9));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> idTable_2()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_2,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(0));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> hashtagTable_2()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_2,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(1));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> hashtagFromUUIDTable_2(String uuid){
        ArrayList<String> hashtags = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_2,null);
        res.moveToFirst();
        if(res.getCount()>0){
            do{
                if(res.getString(2).equals(uuid)){
                    hashtags.add(res.getString(1));
                }
            }while (res.moveToNext());

            if(hashtags.size()==0){
                //hashtags.add("");
            }

        } else {
            //hashtags.add("");
        }
        res.close();
        return hashtags;
    }

    public ArrayList<String> uuidTable_2()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_2,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(2));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> idTable_3()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_3,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do{
                phonenumbers.add(res.getString(0));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<String> imagesTable_3()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_3,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(1));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public ArrayList<byte[]> imagesFromUUIDTable_3(String uuid){
        ArrayList<byte[]> images = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_3,null);
        res.moveToFirst();
        if(res.getCount()>0){
            do{
                if(res.getString(2).equals(uuid)){
                    images.add(res.getBlob(1));
                }
            }while (res.moveToNext());
        } else {
        }
        res.close();
        return images;
    }

    public ArrayList<String> uuidTable_3()
    {
        ArrayList<String> phonenumbers = new ArrayList<>();
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor res = mydb.rawQuery("select * from "+TABLE_NAME_3,null);
        res.moveToFirst();
        if(res.getCount()>0) {
            do {
                phonenumbers.add(res.getString(2));
            }
            while (res.moveToNext());
        }
        else
        {
            phonenumbers.add("");
        }
        res.close();
        return phonenumbers;
    }

    public void addEntry(byte[] image,String status) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(T_4_COL_2, image);
        cv.put(T_4_COL_3,status);
        database.insert( TABLE_NAME_4, null, cv );
    }


    public void clearTable4(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_4);
    }

    public void clearTable3(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_3);
    }

    public void clearTable2(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_2);
    }

    public void clearTable1(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_1);
    }

}
