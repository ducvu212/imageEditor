package com.example.ducvu212.demomvvm.screen.editor;

import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;

/**
 * Created by CuD HniM on 18/10/10.
 */
public interface OnEditClickListener {
    void OnUpdateContrast(int progress);

    void OnUpdateBrightness(int progress);

    void OnDoneClickListener(String type, String name);

    void OnDrawClickListener();

    void OnChangeColorClickListener(int color);

    void OnUndoListener();

    void OnRedoListener();

    void OnClearListener();

    void OnDrawCompleteListener();

    void OnFilter(Bitmap bitmap);

    void OnCropListener();

    void OnStickerItemClickListener(ItemSticker itemSticker);

    void OnStickerDoneClickListener();

    void OnStickerClearClickListener();

    void OnDoneCropListener();

    void OnClearCropListener();

    void OnDoneFilterListener();
}
