package com.example.ducvu212.demomvvm.screen.edit;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemColorPicker;
import com.example.ducvu212.demomvvm.data.model.ItemEdit;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.databinding.FragmentEditBinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ducvu212.demomvvm.screen.editor.EditActivity.sName;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class HandleItemEditClick {

    public static final String TITTLE_BRIGHTNESS = "Brightness";
    public static final String TITTLE_CONTRAST = "Contrast";
    public static String sType;
    private static int sValue;
    private SeekBar mSeekBar;
    private OnUpdateUI mOnUpdateUI;
    private FrameLayout mFrameLayout;
    private ConstraintLayout mConstraintLayout;
    private ImageRepository mImageRepository;
    private FragmentEditBinding mBinding;

    public HandleItemEditClick(ImageRepository repository, FragmentEditBinding binding,
            OnUpdateUI onUpdateUI) {
        mBinding = binding;
        mSeekBar = binding.seekBar;
        mFrameLayout = binding.frameSeekbar;
        mConstraintLayout = binding.contrainsColor;
        mOnUpdateUI = onUpdateUI;
        mImageRepository = repository;
    }

    public HandleItemEditClick(OnUpdateUI onUpdateUI) {
        mOnUpdateUI = onUpdateUI;
    }

    public void OnEditItemClickListener(ItemEdit itemEdit) {
        switch (itemEdit.getImageView()) {
            case R.drawable.ic_brightnes:
                sType = TITTLE_BRIGHTNESS;
                mSeekBar.setProgress(0);
                mSeekBar.setMin(-255);
                mOnUpdateUI.updateContrast(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(VISIBLE);
                setupEdit(TITTLE_BRIGHTNESS);
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                break;
            case R.drawable.ic_contrast:
                sType = TITTLE_CONTRAST;
                mSeekBar.setMin(0);
                mSeekBar.setProgress(0);
                mOnUpdateUI.updateBrightness(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(VISIBLE);
                setupEdit(TITTLE_CONTRAST);
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                break;
            case R.drawable.ic_crop:
                mBinding.recyclerEdit.setVisibility(VISIBLE);
                OnCropAction();
                break;
            case R.drawable.ic_draw:
                mConstraintLayout.setVisibility(VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
                mBinding.recyclerEdit.setVisibility(GONE);
                mOnUpdateUI.OnDrawClickListener();
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
                    mOnUpdateUI.updateContrast(sValue);
                } else {
                    mOnUpdateUI.updateBrightness(sValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (type.equals(TITTLE_CONTRAST)) {
                    mOnUpdateUI.updateContrast(sValue);
                } else {
                    mOnUpdateUI.updateBrightness(sValue);
                }
            }
        });
    }

    public void OnDoneClickListener() {
        mOnUpdateUI.updateContrast(sValue);
        mFrameLayout.setVisibility(GONE);
        mOnUpdateUI.OnDoneClickListener(sType, sName);
    }

    public void OnClearClickListener() {
        mOnUpdateUI.updateContrast(0);
        mFrameLayout.setVisibility(GONE);
        mSeekBar.setProgress(0);
    }

    public void OnColorClickListener(ItemColorPicker itemColorPicker) {
        mOnUpdateUI.OnChangeColorListener(itemColorPicker.getColor());
    }

    public void OnUndoAction() {
        mOnUpdateUI.OnUndoListener();
    }

    public void OnRedoAction() {
        mOnUpdateUI.OnRedoListener();
    }

    public void OnClearColorClickListener() {
        mOnUpdateUI.OnClearListener();
        mConstraintLayout.setVisibility(View.GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
    }

    public void OnDrawCompleteAction() {
        mOnUpdateUI.OnDrawCompleteListener();
    }

    private void OnCropAction() {
        mOnUpdateUI.OnCropListener();
    }

    public void OnStickerClickListener(ItemSticker itemSticker) {
        mOnUpdateUI.OnStickerItemClickListener(itemSticker);
    }

    public void OnStickerDoneClickListener() {
        mOnUpdateUI.OnStickerDoneClickListener();
    }

    public void OnStickerClearClickListener() {
        mBinding.constraintSticker.setVisibility(GONE);
        mBinding.recyclerEdit.setVisibility(VISIBLE);
        mOnUpdateUI.OnStickerClearClickListener();
    }
}
