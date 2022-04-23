package com.adara.yashsd.kadmus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab1,fab2;//,fab3,fab4;

    Animation anim1,anim2,anim3,anim4;

    Boolean isOpen = false;

    DataBase dataBase;

    ArrayList<DiaryEntry> mDiaryList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private DiaryAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab1 = (FloatingActionButton)findViewById(R.id.fabOne);
        fab2 = (FloatingActionButton)findViewById(R.id.fabTwo);
        //fab3 = (FloatingActionButton)findViewById(R.id.fabThree);
        //fab4 = (FloatingActionButton)findViewById(R.id.fabFour);

        fab2.setVisibility(View.INVISIBLE);
        fab2.setClickable(false);
        /*fab3.setVisibility(View.INVISIBLE);
        fab3.setClickable(false);
        fab4.setVisibility(View.INVISIBLE);
        fab4.setClickable(false);*/

        anim1 = AnimationUtils.loadAnimation(this,R.anim.button_open);
        anim2 = AnimationUtils.loadAnimation(this,R.anim.button_close);
        anim3 = AnimationUtils.loadAnimation(this,R.anim.rotate_open);
        anim4 = AnimationUtils.loadAnimation(this,R.anim.rotate_close);

        dataBase = new DataBase(this);

        //listView = (ListView)findViewById(R.id.lv);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewMainMenu);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen == false)
                {
                    fab1.startAnimation(anim3);

                    fab2.startAnimation(anim1);
                    //fab3.startAnimation(anim1);
                    //fab4.startAnimation(anim1);

                    fab2.setClickable(true);
                    //fab3.setClickable(true);
                    //fab4.setClickable(true);

                    isOpen = true;
                }
                else if(isOpen == true)
                {
                    fab1.startAnimation(anim4);

                    fab2.startAnimation(anim2);
                    //fab3.startAnimation(anim2);
                    //fab4.startAnimation(anim2);

                    fab2.setClickable(false);
                    //fab3.setClickable(false);
                    //fab4.setClickable(false);

                    isOpen = false;
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);

                if(isOpen == true)
                {
                    fab1.startAnimation(anim4);

                    fab2.startAnimation(anim2);
                    //fab3.startAnimation(anim2);
                    //fab4.startAnimation(anim2);

                    fab2.setClickable(false);
                    //fab3.setClickable(false);
                    //fab4.setClickable(false);

                    isOpen = false;
                }
            }
        });

        /*fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen == true)
                {
                    fab1.startAnimation(anim4);

                    fab2.startAnimation(anim2);
                    fab3.startAnimation(anim2);
                    fab4.startAnimation(anim2);

                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    fab4.setClickable(false);

                    isOpen = false;
                }
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen == true)
                {
                    fab1.startAnimation(anim4);

                    fab2.startAnimation(anim2);
                    fab3.startAnimation(anim2);
                    fab4.startAnimation(anim2);

                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    fab4.setClickable(false);

                    isOpen = false;
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecyclerView.removeAllViews();

        mDiaryList.clear();

        ArrayList<String> arrayListid = dataBase.idTable_1();
        String[] idArr = new String[arrayListid.size()];
        idArr = arrayListid.toArray(idArr);

        ArrayList<String> arrayListName = dataBase.nameTable_1();
        String[] nameArr = new String[arrayListName.size()];
        nameArr = arrayListName.toArray(nameArr);

        ArrayList<String> arrayListDay = dataBase.dayNameTable_1();
        String[] dayArr = new String[arrayListDay.size()];
        dayArr = arrayListDay.toArray(dayArr);

        ArrayList<String> arrayListDate = dataBase.dayTable_1();
        String[] dateArr = new String[arrayListDate.size()];
        dateArr = arrayListDate.toArray(dateArr);

        ArrayList<String> arrayListMonth = dataBase.monthTable_1();
        String[] monthArr = new String[arrayListMonth.size()];
        monthArr = arrayListMonth.toArray(monthArr);

        ArrayList<String> arrayListYear = dataBase.yearTable_1();
        String[] yearArr = new String[arrayListYear.size()];
        yearArr = arrayListYear.toArray(yearArr);

        ArrayList<String> arrayListMood = dataBase.moodTable_1();
        String[] moodArr = new String[arrayListDate.size()];
        moodArr = arrayListMood.toArray(moodArr);

        ArrayList<String> arrayListBody = dataBase.bodyTable_1();
        String[] bodyArr = new String[arrayListBody.size()];
        bodyArr = arrayListBody.toArray(bodyArr);

        ArrayList<String> arrayListTime = dataBase.timeTable_1();
        String[] timeArr = new String[arrayListTime.size()];
        timeArr = arrayListTime.toArray(timeArr);

        ArrayList<String> arrayListUUID = dataBase.uuidTable_1();
        String[] uuidArr = new String[arrayListUUID.size()];
        uuidArr = arrayListUUID.toArray(uuidArr);

        for(int i =0 ; i< uuidArr.length ; i++ ){
            ArrayList<String> hashtags = dataBase.hashtagFromUUIDTable_2(uuidArr[i]);
            ArrayList<byte[]> images = dataBase.imagesFromUUIDTable_3(uuidArr[i]);
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for(int o = 0; o < images.size();o++){
                bitmaps.add(getScaledBitmap(images.get(o)));
            }
            DiaryEntry diaryEntry = new DiaryEntry(nameArr[i],dayArr[i].toUpperCase(),dateArr[i],monthArr[i].toUpperCase(),yearArr[i],timeArr[i],moodArr[i],bodyArr[i],uuidArr[i],bitmaps,hashtags);
            mDiaryList.add(diaryEntry);
        }

        //if(mDiaryList.get(0).getUuid().equals("")){}else{
        mAdapter = new DiaryAdapter(this,mDiaryList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(MainActivity.this,Main5Activity.class);
                i.putExtra("uuid",mDiaryList.get(position).getUuid());
                i.putExtra("numEntries",mDiaryList.size());
                startActivity(i);
            }
        });
        //}

        if(mDiaryList.size() == 1){
            if(mDiaryList.get(0).getUuid().equals("")){
                mRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }

        if(mDiaryList.size() > 1){
            if(mDiaryList.get(0).getUuid().equals("") && mDiaryList.get(1).getUuid().equals("")){
                mRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public Bitmap getScaledBitmap(byte[] bytes){
        try{
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream,null,options);
            options.inSampleSize = calculateInSampleSize(options, 500,500);
            //We close the input stream because we cant go back in a input stream, hence to use the variable again we have to close it
            inputStream.close();

            inputStream = new ByteArrayInputStream(bytes);
            bufferedInputStream = new BufferedInputStream(inputStream);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream,null,options);

            return bitmap;
        }catch (Exception e){
        }
        return null;
    }
}
