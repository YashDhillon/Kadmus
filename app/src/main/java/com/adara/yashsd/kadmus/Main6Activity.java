package com.adara.yashsd.kadmus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.greenfrvr.hashtagview.HashtagView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    ViewFlipper vFlipper;
    FloatingActionButton fab5,fab6,fab8,fab9;
    Animation anim1,anim2,anim3,anim4;
    TextView tvHead;

    Button hashtagEdit;

    Boolean isOpen = false;

    public List<String> hashtaglist;

    HashtagView hashtagView;

    public static final int PICK_IMAGE = 001;

    DataBase dataBase;

    EditText etName,etBody;

    int numberOfPictures = 0;

    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        hashtagView = (HashtagView)findViewById(R.id.hashTagView);

        tvHead = (TextView)findViewById(R.id.tvHeadView);
        hashtaglist =  new ArrayList<>();

        etName = (EditText) findViewById(R.id.etnameView);
        etBody = (EditText)findViewById(R.id.etBodyView);

        hashtagEdit = (Button)findViewById(R.id.hashTagEditView);

        dataBase = new DataBase(this);

        dataBase.clearTable4();

        Bundle bun = getIntent().getExtras();
        uuid = bun.getString("uuid");

        fab5 = (FloatingActionButton)findViewById(R.id.fabFiveView);
        fab6 = (FloatingActionButton)findViewById(R.id.fabSixView);
        fab8 = (FloatingActionButton)findViewById(R.id.fabEightView);
        fab9 = (FloatingActionButton)findViewById(R.id.fabnineView);

        anim1 = AnimationUtils.loadAnimation(this,R.anim.button_open);
        anim2 = AnimationUtils.loadAnimation(this,R.anim.button_close);
        anim3 = AnimationUtils.loadAnimation(this,R.anim.rotate_open);
        anim4 = AnimationUtils.loadAnimation(this,R.anim.rotate_close);

        fab6.setVisibility(View.INVISIBLE);
        fab6.setClickable(false);
        fab8.setVisibility(View.INVISIBLE);
        fab8.setClickable(false);
        fab9.setVisibility(View.INVISIBLE);
        fab9.setClickable(false);
        hashtagEdit.setVisibility(View.INVISIBLE);
        hashtagEdit.setClickable(false);

        Cursor cursor = dataBase.viewAllDataInTable_1();
        cursor.moveToFirst();

        vFlipper = (ViewFlipper)findViewById(R.id.vFlipperView);
        vFlipper.setClickable(false);

        do{
            if(cursor.getString(9).equals(uuid)){
                break;
            }
        }while(cursor.moveToNext());

        ArrayList<byte[]> bitmaps = dataBase.imagesFromUUIDTable_3(uuid);

        if(bitmaps.size()>0) {
            for (int i = 0; i < bitmaps.size(); i++) {
                dataBase.addEntry(bitmaps.get(i),"true");
                flipperImagesBitmap(getScaledBitmap(bitmaps.get(i)));
            }
        }

        hashtaglist = dataBase.hashtagFromUUIDTable_2(uuid);

        if(hashtaglist.size()>0){
            hashtagEdit.setVisibility(View.VISIBLE);
            hashtagEdit.setClickable(true);
        }else{
            hashtagEdit.setVisibility(View.INVISIBLE);
            hashtagEdit.setClickable(false);
        }

        hashtagView.setData(hashtaglist);

        final String stringDate = cursor.getString(3);
        final String stringMonth = cursor.getString(4);
        final String stringYear = cursor.getString(5);
        final String stringDay = cursor.getString(2);
        final String stringtime = cursor.getString(6);

        final String date = cursor.getString(3) + "/" + cursor.getString(4) + "/" +cursor.getString(5);
        String body = cursor.getString(8);
        String title = cursor.getString(1);

        tvHead.setText(date);
        etName.setText(title);
        etBody.setText(body);

        vFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main6Activity.this,Main3Activity.class);
                startActivity(i);
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen == false)
                {
                    fab5.startAnimation(anim3);

                    fab6.startAnimation(anim1);
                    fab8.startAnimation(anim1);
                    fab9.startAnimation(anim1);

                    fab6.setClickable(true);
                    fab8.setClickable(true);
                    fab9.setClickable(true);

                    isOpen = true;
                }
                else if(isOpen == true)
                {
                    fab5.startAnimation(anim4);

                    fab6.startAnimation(anim2);
                    fab8.startAnimation(anim2);
                    fab9.startAnimation(anim2);

                    fab6.setClickable(false);
                    fab8.setClickable(false);
                    fab9.setClickable(false);

                    isOpen = false;
                }
            }
        });

        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab5.startAnimation(anim4);

                fab6.startAnimation(anim2);
                fab8.startAnimation(anim2);
                fab9.startAnimation(anim2);

                fab6.setClickable(false);
                fab8.setClickable(false);
                fab9.setClickable(false);

                isOpen = false;

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        fab8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab5.startAnimation(anim4);

                fab6.startAnimation(anim2);
                fab8.startAnimation(anim2);
                fab9.startAnimation(anim2);

                fab6.setClickable(false);
                fab8.setClickable(false);
                fab9.setClickable(false);

                isOpen = false;

                dataBase.deleteDataInTable_1(uuid);
                Boolean flag1 = false ,flag2 = false ,flag3 = false;

                flag1 = dataBase.insertDataInTable_1(etName.getText().toString(),stringDay,stringDate,stringMonth,stringYear,
                        stringtime,"good",etBody.getText().toString(),uuid);

                dataBase.deleteDataInTable_2(uuid);
                if(hashtaglist.size()>0) {
                    for (int i = 0; i < hashtaglist.size(); i++) {
                        flag2 = dataBase.insertDataInTable_2(hashtaglist.get(i), uuid);
                    }
                }else{
                    flag2 = false;
                }

                dataBase.deleteDataInTable_3(uuid);
                Cursor cursor = dataBase.viewAllDataInTable_4();
                cursor.moveToFirst();

                if(vFlipper.getChildCount()>0) {
                    do {
                        flag3 = dataBase.insertDataInTable_3(cursor.getBlob(1), uuid);
                    } while (cursor.moveToNext());
                }
                else {
                    flag3 = false;
                }

                finish();
            }
        });

        fab9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab5.startAnimation(anim4);

                fab6.startAnimation(anim2);
                fab8.startAnimation(anim2);
                fab9.startAnimation(anim2);

                fab6.setClickable(false);
                fab8.setClickable(false);
                fab9.setClickable(false);

                isOpen = false;

                final AlertDialog.Builder builder = new AlertDialog.Builder(Main6Activity.this);
                View view = getLayoutInflater().inflate(R.layout.custom_layout_hash_tag,null);

                final EditText et = (EditText)view.findViewById(R.id.hashtagET);
                final Button b1 = (Button)view.findViewById(R.id.BtnDone);
                final Button b2 = (Button)view.findViewById(R.id.BtnCancel);

                builder.setView(view);
                final AlertDialog dialog = builder.create();

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hashTag = et.getText().toString();
                        HashtagRefresher("#"+hashTag);

                        if(hashtaglist.size()>0){
                            hashtagEdit.setVisibility(View.VISIBLE);
                            hashtagEdit.setClickable(true);
                        }else{
                            hashtagEdit.setVisibility(View.INVISIBLE);
                            hashtagEdit.setClickable(false);
                        }

                        dialog.cancel();
                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.setCancelable(true);
                dialog.show();
            }
        });

        hashtagEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main6Activity.this);
                View view = getLayoutInflater().inflate(R.layout.custom_layout_hash_tag_edit,null);

                builder.setView(view);
                String[] hashtagarr = new String[hashtaglist.size()];
                hashtagarr = hashtaglist.toArray(hashtagarr);

                final ListView lv = (ListView)view.findViewById(R.id.lv);
                final ArrayAdapter arrayAdapter = new ArrayAdapter(Main6Activity.this,android.R.layout.simple_list_item_1,hashtagarr);
                lv.setAdapter(arrayAdapter);

                final AlertDialog dialog = builder.create();
                dialog.show();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        String[] hashtagarr1 = new String[hashtaglist.size()];
                        hashtagarr1 = hashtaglist.toArray(hashtagarr1);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Main6Activity.this);
                        View view1 = getLayoutInflater().inflate(R.layout.custom_layout_hash_tag_edit_two,null);

                        builder1.setView(view1);

                        final EditText editText = (EditText)view1.findViewById(R.id.hashtagETedit);
                        Button button1 = (Button)view1.findViewById(R.id.BtnDoneEdit);
                        Button button2 = (Button)view1.findViewById(R.id.BtnCancelEdit);
                        Button button3 = (Button)view1.findViewById(R.id.BtnDeleteEdit);

                        editText.setText(hashtagarr1[position]);

                        final AlertDialog alertDialog = builder1.create();

                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hashtaglist.remove(position);

                                String edit = editText.getText().toString();
                                if (edit.equals("")){
                                    if(hashtaglist.size()>0){
                                        hashtagEdit.setVisibility(View.VISIBLE);
                                        hashtagEdit.setClickable(true);
                                    }else{
                                        hashtagEdit.setVisibility(View.INVISIBLE);
                                        hashtagEdit.setClickable(false);
                                    }
                                }else {
                                    hashtaglist.add(position,edit);
                                }

                                String[] hashtagarr1 = new String[hashtaglist.size()];
                                hashtagarr1 = hashtaglist.toArray(hashtagarr1);
                                ArrayAdapter arrayAdapter1 = new ArrayAdapter(Main6Activity.this,android.R.layout.simple_list_item_1,hashtagarr1);;
                                lv.setAdapter(arrayAdapter1);

                                hashtagView.setData(hashtaglist);

                                if(hashtaglist.size()>0){
                                    hashtagEdit.setVisibility(View.VISIBLE);
                                    hashtagEdit.setClickable(true);
                                }else{
                                    hashtagEdit.setVisibility(View.INVISIBLE);
                                    hashtagEdit.setClickable(false);
                                }

                                alertDialog.dismiss();
                                dialog.dismiss();
                            }
                        });

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(hashtaglist.size()>0){
                                    hashtagEdit.setVisibility(View.VISIBLE);
                                    hashtagEdit.setClickable(true);
                                }else{
                                    hashtagEdit.setVisibility(View.INVISIBLE);
                                    hashtagEdit.setClickable(false);
                                }
                                alertDialog.dismiss();
                                dialog.dismiss();
                            }
                        });

                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hashtaglist.remove(position);
                                hashtagView.setData(hashtaglist);

                                String[] hashtagarr1 = new String[hashtaglist.size()];
                                hashtagarr1 = hashtaglist.toArray(hashtagarr1);
                                ArrayAdapter arrayAdapter1 = new ArrayAdapter(Main6Activity.this,android.R.layout.simple_list_item_1,hashtagarr1);;
                                lv.setAdapter(arrayAdapter1);

                                if(hashtaglist.size()>0){
                                    hashtagEdit.setVisibility(View.VISIBLE);
                                    hashtagEdit.setClickable(true);
                                }else{
                                    hashtagEdit.setVisibility(View.INVISIBLE);
                                    hashtagEdit.setClickable(false);
                                }

                                alertDialog.dismiss();
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });

            }
        });
    }


    public void flipperImagesBitmap(Bitmap image){
        numberOfPictures++;
        ImageView iv = new ImageView(this);
        iv = new ImageView(getApplicationContext());
        iv.setImageBitmap(image);
        iv.setAdjustViewBounds(true);
        iv.setMaxHeight(500);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        vFlipper.addView(iv);

        vFlipper.setInAnimation(this,android.R.anim.fade_in);
        vFlipper.setOutAnimation(this,android.R.anim.fade_out);
    }

    public void flipperImageBitmapFromSqlite(){
        vFlipper.removeAllViews();

        Cursor cur = dataBase.viewAllDataInTable_4();

        if(cur.getCount() <= 1) {
            vFlipper.stopFlipping();
        }
        if(cur.getCount() >= 2){
            vFlipper.startFlipping();
        }

        cur.moveToFirst();
        if(cur.getCount() <= 0){}
        else{
            do {
                byte[] bytes = cur.getBlob(1);
                Bitmap bitmap = Main2Activity.getImage(bytes);
                flipperImagesBitmap(bitmap);
            }
            while (cur.moveToNext());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == Main2Activity.PICK_IMAGE) {
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(bufferedInputStream,null,options);
                options.inSampleSize = calculateInSampleSize(options, 500,500);
                // We close the input stream because we cant go back in a input stream, hence to use the variable again we have to close it
                inputStream.close();

                inputStream = this.getContentResolver().openInputStream(data.getData());
                bufferedInputStream = new BufferedInputStream(inputStream);
                options.inJustDecodeBounds = false;

                Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream,null,options);

                inputStream.close();
                if(bitmap == null){
                    Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show();
                }
                saveToDataBase(bitmap);
                flipperImageBitmapFromSqlite();

                if(vFlipper.getChildCount() >1){
                    vFlipper.startFlipping();
                }else{
                    vFlipper.stopFlipping();
                }

            } catch (Exception e) {
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


    public void HashtagRefresher(String hashtag){
        hashtaglist.add(hashtag);
        hashtagView.setData(hashtaglist);
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void saveToDataBase(Bitmap image) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, bStream);
        byte[] byteArray = bStream.toByteArray();
        dataBase.addEntry(byteArray,"true");
    }

    @Override
    protected void onResume() {
        super.onResume();
        flipperImageBitmapFromSqlite();
    }

    public Bitmap getScaledBitmap(byte[] bytes){
        try{
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream,null,options);
            options.inSampleSize = calculateInSampleSize(options, 500,500);
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
}