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
public class BindingHome {

    private static int mWidth;
    private static float mRatio;
    private static float mHeight;
    private static float mRatioView;

    BindingHome(int width, int height, float ratio) {
        mWidth = width;
        mRatio = ratio;
        mHeight = height;
        mRatioView = (float) (0.01 * ((100 * height) / width));
    }

    @BindingAdapter({ "loadImg" })
    public static void loadImage(ImageView imageView, String url) {
        createCreator(mWidth, (int) (mWidth * mRatio), url).networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        createCreator(mWidth, (int) (mWidth * mRatio), url).into(imageView);
                    }
                });
    }

    private static RequestCreator createCreator(int width, int height, String url) {
        return Picasso.get().load(url).placeholder(R.drawable.placeholder).resize(width, height);
    }

    @BindingAdapter({ "loadImgRandom" })
    public static void loadImageRandom(ImageView imageView, String url) {
        createCreator(mWidth, (int) (mWidth * mRatioView), url).networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        createCreator(mWidth, (int) (mWidth * mRatioView), url).centerCrop()
                                .into(imageView);
                    }
                });
    }
}
