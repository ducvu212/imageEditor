package com.example.ducvu212.demomvvm.screen.editor;

import android.graphics.Bitmap;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;

/**
 * Created by CuD HniM on 18/10/10.
 */
public interface OnEditClickListener {
    void OnUpdateContrast(int progress);

    void OnUpdateBrightness(int progress);

    void OnDone(String type, String name);

    void OnDraw();

    void OnChangeColor(int color);

    void OnUndo();

    void OnRedo();

    void OnClear();

    void OnDrawComplete();

    void OnFilter(Bitmap bitmap);

    void OnCrop();

    void OnSticker(ItemSticker itemSticker);

    void OnStickerDone();

    void OnStickerClear();

    void OnDoneCrop(boolean isCrop);

    void OnClearCrop();

    void OnDoneFilter();
}
