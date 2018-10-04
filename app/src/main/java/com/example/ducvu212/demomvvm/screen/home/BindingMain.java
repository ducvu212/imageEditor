package com.example.ducvu212.demomvvm.screen.home;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.example.ducvu212.demomvvm.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

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
        createCreator(url).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                createCreator(url).into(imageView);
            }
        });
    }

    private static RequestCreator createCreator(String url) {
        return Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(mWidth, (int) (mWidth * mRatio))
                .centerCrop();
    }
}
