package com.example.ducvu212.demomvvm.screen.edit;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class BindingEdit {

    private Context mContext;

    public BindingEdit(Context context) {
        mContext = context;
    }

    @BindingAdapter({ "loadImg" })
    public static void loadImage(ImageView imageView, int drawable) {
        imageView.setImageResource(drawable);
    }
}
