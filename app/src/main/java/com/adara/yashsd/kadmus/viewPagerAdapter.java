package com.adara.yashsd.kadmus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class viewPagerAdapter extends PagerAdapter{

    Activity activity;
    Bitmap[] images;
    LayoutInflater inflater;
    String[] ids;

    public viewPagerAdapter(Activity activity, Bitmap[] images,String[] ids) {
        this.activity = activity;
        this.images = images;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpage_item,container,false);

        ZoomableImageView image = (ZoomableImageView) itemView.findViewById(R.id.image);
        TextView idPic = (TextView)itemView.findViewById(R.id.idPic);

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) displayMetrics.heightPixels;
        int width = (int) displayMetrics.widthPixels;
        image.setMaxHeight(height);
        image.setMaxWidth(width);*/

        // this code commented below is the part of the project that requires the dependency

        /*try{
            Picasso.with(activity.getApplicationContext())
                    .load(images[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(image);
        }catch (Exception e){

        }*/

        image.setImageBitmap(images[position]);
        //image.setScaleType(ImageView.ScaleType.CENTER);
        container.addView(itemView);
        idPic.setText(ids[position]);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
