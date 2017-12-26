/*
© Budkov Sergey, Maksakova Maria, Markovnikov Nikita 2014
All Rights Reserved
 */
package translator.BudkovMaksakovaMarkovnikov.ru.translator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private int mGalleryItemBackground;
    private Context mContext;
    private Drawable[] mImage;

    public ImageAdapter(Context сontext, Drawable[] mImage) {
        mContext = сontext;
        this.mImage = mImage;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mImage.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mImage[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ImageView view = new ImageView(mContext);
        view.setImageDrawable(mImage[position]);
        view.setPadding(20, 20, 20, 20);
        view.setLayoutParams(new Gallery.LayoutParams(250, 250));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setBackgroundResource(mGalleryItemBackground);

        return view;
    }

    public void setmGalleryItemBackground(int mGalleryItemBackground) {
        this.mGalleryItemBackground = mGalleryItemBackground;
    }
}
