package com.example.ducvu212.demomvvm.screen.edit;

import com.example.ducvu212.demomvvm.data.model.ItemSticker;

/**
 * Created by CuD HniM on 18/10/10.
 */
public interface OnUpdateUI {
    void updateContrast(int progress);

    void updateBrightness(int progress);

    void OnDoneClickListener(String type, String name);

    void OnDrawClickListener();

    void OnChangeColorListener(int color);

    void OnUndoListener();

    void OnRedoListener();

    void OnClearListener();

    void OnDrawCompleteListener();

    void OnCropListener();

    void OnStickerItemClickListener(ItemSticker itemSticker);

    void OnStickerDoneClickListener();

    void OnStickerClearClickListener();
}
