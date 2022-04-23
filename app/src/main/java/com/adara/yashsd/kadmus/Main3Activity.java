package com.adara.yashsd.kadmus;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    DataBase dataBase;

    ArrayList<ImageItem> imageItemArrayList = new ArrayList<>();

    TextView idPic;

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        dataBase = new DataBase(this);

        idPic = (TextView) findViewById(R.id.idPic);

        Cursor cur = dataBase.viewAllDataInTable_4();
        cur.moveToFirst();

        do {
            byte[] bytes = cur.getBlob(1);
            Bitmap bitmap = getScaledBitmap(bytes);
            imageItemArrayList.add(new ImageItem(bitmap,Integer.toString(cur.getInt(0))));
        }
        while (cur.moveToNext());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mAdapter = new ImageAdapter(imageItemArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openItem(position);
            }
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public Bitmap getScaledBitmap(byte[] bytes){
        try{
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bufferedInputStream,null,options);
            options.inSampleSize = calculateInSampleSize(options, 200,200);
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
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public void removeItem(int position){
        ImageItem imageItem = imageItemArrayList.get(position);
        dataBase.deleteDataInTable_4(imageItem.getId());
        imageItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void openItem(int position){
        ImageItem imageItem = imageItemArrayList.get(position);
        String id = imageItem.getId();
        Intent i = new Intent(Main3Activity.this,Main4Activity.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}
