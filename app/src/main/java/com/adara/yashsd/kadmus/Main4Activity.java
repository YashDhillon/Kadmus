package com.adara.yashsd.kadmus;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Main4Activity extends AppCompatActivity {

    DataBase dataBase;
    ZoomableImageView ziv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        dataBase = new DataBase(this);
        ziv = (ZoomableImageView)findViewById(R.id.zoomImageView);
        Bundle bun = getIntent().getExtras();

        String id = bun.getString("id");
        int idint = Integer.parseInt(id);

        Cursor cursor = dataBase.viewAllDataInTable_4();
        cursor.moveToFirst();

        do{
            int idTemp = cursor.getInt(0);
            if(idint == idTemp){
                try{
                    InputStream inputStream = new ByteArrayInputStream(cursor.getBlob(1));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(bufferedInputStream,null,options);
                    options.inSampleSize = calculateInSampleSize(options, 700,700);

                    // We close the input stream because we cant go back in a input stream, hence to use the variable again we have to close it
                    inputStream.close();

                    inputStream = new ByteArrayInputStream(cursor.getBlob(1));
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    options.inJustDecodeBounds = false;

                    Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream,null,options);
                    ziv.setImageBitmap(bitmap);
                }catch (Exception e){
                }
            }
        }while(cursor.moveToNext());
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
