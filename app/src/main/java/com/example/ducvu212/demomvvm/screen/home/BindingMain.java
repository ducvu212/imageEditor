package com.example.ducvu212.demomvvm.screen.home;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.example.ducvu212.demomvvm.R;
import com.squareup.picasso.Picasso;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class BindingMain {

    private static int mWidth;
    private static float mRatio;

    public BindingMain(int width, float ratio) {
        mWidth = width;
        mRatio = ratio;
    }

    @BindingAdapter({ "loadImg" })
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(mWidth, (int) (mWidth * mRatio))
                .centerCrop()
                .into(imageView);
    }
}
