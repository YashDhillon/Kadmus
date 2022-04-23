package com.adara.yashsd.kadmus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.content.Intent;
import com.greenfrvr.hashtagview.HashtagView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbarLayout;

    DataBase dataBase;

    FloatingActionButton menubutton,editButton,deletebutton;

    ViewFlipper viewFlipper;

    String uuid;

    HashtagView hashtagView;

    Boolean isOpen = false;

    public List<String> hashtaglist;

    TextView tvdate, tvtime, tvbody , tvtitle;

    int height = 500;

    Animation anim1,anim2,anim3,anim4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        editButton = (FloatingActionButton)findViewById(R.id.edit);
        menubutton = (FloatingActionButton)findViewById(R.id.menu);
        deletebutton = (FloatingActionButton)findViewById(R.id.delete);

        tvtitle = (TextView)findViewById(R.id.tvtitle6);

        tvbody = (TextView)findViewById(R.id.tvbody6);

        tvdate = (TextView)findViewById(R.id.tvdate6);

        tvtime = (TextView)findViewById(R.id.tvtime6);

        viewFlipper = (ViewFlipper)findViewById(R.id.ViewFlipperEntry);

        hashtagView = (HashtagView)findViewById(R.id.hashTag6);

        //toolbar = (Toolbar)findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingTB);

        anim1 = AnimationUtils.loadAnimation(this,R.anim.button_open);
        anim2 = AnimationUtils.loadAnimation(this,R.anim.button_close);
        anim3 = AnimationUtils.loadAnimation(this,R.anim.rotate_open);
        anim4 = AnimationUtils.loadAnimation(this,R.anim.rotate_close);

        editButton.setVisibility(View.INVISIBLE);
        editButton.setClickable(false);
        deletebutton.setVisibility(View.INVISIBLE);
        deletebutton.setClickable(false);

        dataBase = new DataBase(this);
        Bundle bun = getIntent().getExtras();
        uuid = bun.getString("uuid");
        final int numEntries = bun.getInt("numEntries");

        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen == false)
                {
                    menubutton.startAnimation(anim3);

                    editButton.startAnimation(anim1);
                    deletebutton.startAnimation(anim1);

                    editButton.setClickable(true);
                    deletebutton.setClickable(true);

                    isOpen = true;
                }
                else if(isOpen == true)
                {
                    menubutton.startAnimation(anim4);

                    editButton.startAnimation(anim2);
                    deletebutton.startAnimation(anim2);

                    editButton.setClickable(false);
                    deletebutton.setClickable(false);

                    isOpen = false;
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen == false)
                {
                    menubutton.startAnimation(anim3);

                    editButton.startAnimation(anim1);
                    deletebutton.startAnimation(anim1);

                    editButton.setClickable(true);
                    deletebutton.setClickable(true);

                    isOpen = true;
                }
                else if(isOpen == true)
                {
                    menubutton.startAnimation(anim4);

                    editButton.startAnimation(anim2);
                    deletebutton.startAnimation(anim2);

                    editButton.setClickable(false);
                    deletebutton.setClickable(false);

                    isOpen = false;
                }

                Intent i = new Intent(Main5Activity.this,Main6Activity.class);
                i.putExtra("uuid",uuid);
                startActivity(i);
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen == false)
                {
                    menubutton.startAnimation(anim3);

                    editButton.startAnimation(anim1);
                    deletebutton.startAnimation(anim1);

                    editButton.setClickable(true);
                    deletebutton.setClickable(true);

                    isOpen = true;
                }
                else if(isOpen == true)
                {
                    menubutton.startAnimation(anim4);

                    editButton.startAnimation(anim2);
                    deletebutton.startAnimation(anim2);

                    editButton.setClickable(false);
                    deletebutton.setClickable(false);

                    isOpen = false;
                }

                if(numEntries == 1){
                    dataBase.clearTable1();
                    dataBase.clearTable2();
                    dataBase.clearTable3();
                } else{
                    dataBase.deleteDataInTable_1(uuid);
                    dataBase.deleteDataInTable_2(uuid);
                    dataBase.deleteDataInTable_3(uuid);
                }

                finish();
            }
        });

        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main5Activity.this,Main7Activity.class);
                i.putExtra("uuid",uuid);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLayout();
    }

    public Bitmap getScaledBitmap(byte[] bytes){
        try{
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream,null,options);

            options.inSampleSize = calculateInSampleSize(options,(int)(0.5 * height),(int)(0.5 * height));
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

    public void flipperImagesBitmap(Bitmap image){
        ImageView iv = new ImageView(this);
        iv = new ImageView(getApplicationContext());
        iv.setImageBitmap(image);
        iv.setMaxHeight((int)(0.5*height));
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        viewFlipper.addView(iv);

        viewFlipper.setInAnimation(this,android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this,android.R.anim.fade_out);
    }

    public void loadLayout(){

        class LoadEntries extends AsyncTask<Void, Void, Void> {
            private boolean ConnectSuccess = true;

            @Override
            protected void onPreExecute() {
                Cursor cursor = dataBase.viewAllDataInTable_1();
                cursor.moveToFirst();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                height = displayMetrics.heightPixels;

                do{
                    if(cursor.getString(9).equals(uuid)){
                        break;
                    }
                }while(cursor.moveToNext());

                ArrayList<byte[]> bitmaps = dataBase.imagesFromUUIDTable_3(uuid);

                String title = cursor.getString(1);
                String date = cursor.getString(3) + "/" + cursor.getString(4) + "/" +cursor.getString(5);
                String time = cursor.getString(6);
                String body = cursor.getString(8);

                if(bitmaps.size()>0) {
                    viewFlipper.removeAllViews();
                    for (int i = 0; i < bitmaps.size(); i++) {
                        flipperImagesBitmap(getScaledBitmap(bitmaps.get(i)));
                    }
                } else {
                    viewFlipper.removeAllViews();
                    tvtitle.setText(title);
                }

                ArrayList<String> hashtags = dataBase.hashtagFromUUIDTable_2(uuid);
                hashtagView.setData(hashtags);


                //toolbar.setTitle(title);

                collapsingToolbarLayout.setTitle(title);
                // Remember to set the title of the toolbar multiple times set the title of the collapsing tool bar
                // instead of the toolbar inside the collapsing toolbar
                // but still toolbaar is required in order to set title

                /*setSupportActionBar(toolbar);
                if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                getSupportActionBar().setTitle("");
                getSupportActionBar().setTitle(title);*/

                tvtime.setText(time);

                tvdate.setText(date);

                tvbody.setText(body);

                if(viewFlipper.getChildCount() <= 0) {
                    tvtitle.setText(title);
                } else {
                    tvtitle.setText("");
                    tvtitle.setVisibility(View.INVISIBLE);
                }

                if(viewFlipper.getChildCount() <= 1) {
                    viewFlipper.stopFlipping();
                }
                if(viewFlipper.getChildCount() >= 2){
                    viewFlipper.startFlipping();
                }
            }

            @Override
            protected Void doInBackground(Void... devices) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }

        }
        new LoadEntries().execute();
    }
}