package com.adara.yashsd.kadmus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main7Activity extends AppCompatActivity {

    ViewPager viewPager;

    DataBase dataBase;

    String uuid;

    int height = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

        dataBase = new DataBase(this);
        Bundle bun = getIntent().getExtras();
        uuid = bun.getString("uuid");

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        ArrayList<byte[]> bitmaps = dataBase.imagesFromUUIDTable_3(uuid);
        Bitmap[] bitmaps1 = new Bitmap[bitmaps.size()];
        String[] ids = new String[bitmaps.size()];

        for(int i = 0 ; i < bitmaps.size() ; i++) {
            bitmaps1[i] = getScaledBitmap(bitmaps.get(i));
            ids[i] = "";
        }

        viewPagerAdapter viewpageradapter = new viewPagerAdapter(Main7Activity.this,bitmaps1,ids);
        viewPager.setAdapter(viewpageradapter);

    }

    public Bitmap getScaledBitmap(byte[] bytes){
        try{
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream,null,options);

            options.inSampleSize = calculateInSampleSize(options,(int)(height),(int)(height));
            // We close the input stream because we cant go back in a input stream, hence to use the variable again we have to close it
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
}
