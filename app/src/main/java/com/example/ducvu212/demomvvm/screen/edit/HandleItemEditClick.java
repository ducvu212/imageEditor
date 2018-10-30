package com.example.ducvu212.demomvvm.screen.edit;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemColorPicker;
import com.example.ducvu212.demomvvm.data.model.ItemEdit;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;
import com.example.ducvu212.demomvvm.databinding.FragmentEditBinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ducvu212.demomvvm.screen.editor.EditActivity.sName;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class HandleItemEditClick {

    public static final String TITTLE_BRIGHTNESS = "Brightness";
    private static final String TITTLE_CONTRAST = "Contrast";
    private static String sType;
    private static int sValue;
    private SeekBar mSeekBar;
    private OnUpdateUIListener mOnUpdateUIListener;
    private FrameLayout mFrameLayout;
    private ConstraintLayout mConstraintLayout;
    private FragmentEditBinding mBinding;

    public HandleItemEditClick(FragmentEditBinding binding, OnUpdateUIListener onUpdateUIListener) {
        mBinding = binding;
        mSeekBar = binding.seekBar;
        mFrameLayout = binding.frameSeekbar;
        mConstraintLayout = binding.contrainsColor;
        mOnUpdateUIListener = onUpdateUIListener;
    }

    public HandleItemEditClick(OnUpdateUIListener onUpdateUIListener) {
        mOnUpdateUIListener = onUpdateUIListener;
    }

    public void OnEditItemClickListener(ItemEdit itemEdit) {
        switch (itemEdit.getImageView()) {
            case R.drawable.ic_brightnes:
                sType = TITTLE_BRIGHTNESS;
                mSeekBar.setProgress(0);
                mSeekBar.setMin(-255);
                mOnUpdateUIListener.updateContrast(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(VISIBLE);
                setupEdit(TITTLE_BRIGHTNESS);
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                break;
            case R.drawable.ic_contrast:
                sType = TITTLE_CONTRAST;
                mSeekBar.setMin(0);
                mSeekBar.setProgress(0);
                mOnUpdateUIListener.updateBrightness(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(VISIBLE);
                setupEdit(TITTLE_CONTRAST);
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                break;
            case R.drawable.ic_crop:
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                OnCropAction(true);
                break;
            case R.drawable.ic_draw:
                mConstraintLayout.setVisibility(VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
                mBinding.recyclerEdit.setVisibility(GONE);
                mOnUpdateUIListener.OnDrawClick();
                break;
            case R.drawable.ic_add:
                mConstraintLayout.setVisibility(GONE);
                mBinding.constraintSticker.setVisibility(VISIBLE);
                mBinding.recyclerEdit.setVisibility(GONE);
                mBinding.frameSeekbar.setVisibility(GONE);
                break;
        }
    }

    private void setupEdit(String type) {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sValue = progress;
                if (type.equals(TITTLE_CONTRAST)) {
                    mOnUpdateUIListener.updateContrast(sValue);
                } else {
                    mOnUpdateUIListener.updateBrightness(sValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (type.equals(TITTLE_CONTRAST)) {
                    mOnUpdateUIListener.updateContrast(sValue);
                } else {
                    mOnUpdateUIListener.updateBrightness(sValue);
                }
            }
        });
    }

    public void OnDoneClickListener() {
        mOnUpdateUIListener.updateContrast(sValue);
        mFrameLayout.setVisibility(GONE);
        mOnUpdateUIListener.OnDoneClick(sType, sName);
    }

    public void OnClearClickListener() {
        mOnUpdateUIListener.updateContrast(0);
        mFrameLayout.setVisibility(GONE);
        mSeekBar.setProgress(0);
    }

    public void OnColorClickListener(ItemColorPicker itemColorPicker) {
        mOnUpdateUIListener.OnChangeColor(itemColorPicker.getColor());
    }

    public void OnUndoAction() {
        mOnUpdateUIListener.OnUndo();
    }

    public void OnRedoAction() {
        mOnUpdateUIListener.OnRedo();
    }

    public void OnClearColorClickListener() {
        mOnUpdateUIListener.OnClear();
        mConstraintLayout.setVisibility(View.GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
    }

    public void OnDrawCompleteAction() {
        mConstraintLayout.setVisibility(GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
        mOnUpdateUIListener.OnDrawComplete();
    }

    private void OnCropAction(boolean isCrop) {
        mOnUpdateUIListener.OnCrop(isCrop);
        mBinding.frameSeekbar.setVisibility(GONE);
    }

    public void OnStickerClickListener(ItemSticker itemSticker) {
        mOnUpdateUIListener.OnStickerItemClick(itemSticker);
    }

    public void OnStickerDoneClickListener() {
        mBinding.constraintSticker.setVisibility(GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
        mOnUpdateUIListener.OnStickerDoneClick();
    }

    public void OnStickerClearClickListener() {
        mBinding.constraintSticker.setVisibility(GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
        mOnUpdateUIListener.OnStickerClearClick();
    }
}
