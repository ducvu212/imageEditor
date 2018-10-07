package com.example.ducvu212.demomvvm.screen.details;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.example.ducvu212.demomvvm.R;

/**
 * Created by CuD HniM on 18/10/07.
 */
public class BindingDetails {

    @BindingAdapter({ "loadLike" })
    public static void loadLike(ImageView imageView, boolean isLike) {
        if (isLike) {
            imageView.setImageResource(R.drawable.ic_like);
        } else {
            imageView.setImageResource(R.drawable.ic_un_like);
        }
    }

    @BindingAdapter({ "loadDownload" })
    public static void loadDownloaded(ImageView imageView, boolean isDownloaded) {
        if (isDownloaded) {
            imageView.setImageResource(R.drawable.ic_downloaded);
        } else {
            imageView.setImageResource(R.drawable.ic_download);
        }
    }
}
