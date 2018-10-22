package com.example.ducvu212.demomvvm.screen.edit;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemColorPicker;
import com.example.ducvu212.demomvvm.data.model.ItemEdit;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.databinding.FragmentEditBinding;

import static android.view.View.GONE;
import static com.example.ducvu212.demomvvm.screen.editor.EditActivity.mName;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class HandleItemEditClick {

    public static final String TITTLE_BRIGHTNESS = "Brightness";
    private static final String TITTLE_CONTRAST = "Contrast";
    private static int mValue;
    private static String mType;
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
                mSeekBar.setProgress(0);
                mOnUpdateUI.updateContrast(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                setupEdit(TITTLE_BRIGHTNESS);
                mType = TITTLE_BRIGHTNESS;
                mBinding.recyclerEdit.setVisibility(View.VISIBLE);
                break;
            case R.drawable.ic_contrast:
                mSeekBar.setProgress(0);
                mOnUpdateUI.updateBrightness(0);
                mConstraintLayout.setVisibility(GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                setupEdit(TITTLE_CONTRAST);
                mType = TITTLE_CONTRAST;
                mBinding.recyclerEdit.setVisibility(View.VISIBLE);
                break;
            case R.drawable.ic_crop:
                mBinding.recyclerEdit.setVisibility(View.VISIBLE);

                break;
            case R.drawable.ic_draw:
                mConstraintLayout.setVisibility(View.VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
                mBinding.recyclerEdit.setVisibility(GONE);
                mOnUpdateUI.OnDrawClickListener();
                break;
            case R.drawable.ic_add:

                break;
        }
    }

    private void setupEdit(String type) {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mValue = progress;
                if (type.equals(TITTLE_CONTRAST)) {
                    mOnUpdateUI.updateContrast(mValue);
                } else {
                    mOnUpdateUI.updateBrightness(mValue);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (type.equals(TITTLE_CONTRAST)) {
                    mOnUpdateUI.updateContrast(mValue);
                } else {
                    mOnUpdateUI.updateBrightness(mValue);
                }
            }
        });
    }

    public void OnDoneClickListener() {
        mOnUpdateUI.updateContrast(mValue);
        mFrameLayout.setVisibility(GONE);
        mOnUpdateUI.OnDoneClickListener(mType, mName);
        //To do for save Image
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
        mOnUpdateUI.OnUndoAction();
    }

    public void OnRedoAction() {
        mOnUpdateUI.OnRedoAction();
    }

    public void OnClearColorClickListener() {
        mOnUpdateUI.OnClearAction();
        mConstraintLayout.setVisibility(View.GONE);
        mBinding.recyclerEdit.setVisibility(View.VISIBLE);
    }

    public void OnDrawCompleteAction() {
        mOnUpdateUI.OnDrawCompleteAction();
    }
}
