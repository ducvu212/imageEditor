package com.example.ducvu212.demomvvm.screen.editor;

/**
 * Created by CuD HniM on 18/10/25.
 */
public class HandleOnClickListener {

    private OnEditClickListener mListener;

    HandleOnClickListener(OnEditClickListener listener) {
        mListener = listener;
    }

    public void OnClearCropListener() {
        mListener.OnClearCrop();
    }

    public void OnDoneCropListener() {
        mListener.OnDoneCrop(true);
    }
}
