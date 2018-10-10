package com.example.ducvu212.demomvvm.screen.editor;

/**
 * Created by CuD HniM on 18/10/10.
 */
public interface OnEditClickListener {
    void OnUpdateContrast(int progress);

    void OnUpdateBrightness(int progress);

    void OnDoneClickListener(String type, String name);
}
