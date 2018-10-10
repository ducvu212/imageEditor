package com.example.ducvu212.demomvvm.screen.edit;

/**
 * Created by CuD HniM on 18/10/10.
 */
public interface OnUpdateUI {
    void updateContrast(int progress);

    void updateBrightness(int progress);

    void OnDoneClickListener(String type, String name);
}
