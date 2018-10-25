package com.example.ducvu212.demomvvm.screen.editor;

/**
 * Created by CuD HniM on 18/10/25.
 */
public class HandleOnClickListener {

    private OnEditClickListener mListener;

    public HandleOnClickListener(OnEditClickListener listener) {
        mListener = listener;
    }

    public void OnClearCropListener() {
        mListener.OnClearCropListener();
    }

    public void OnDoneCropListener() {
        mListener.OnDoneCropListener();
    }
}
