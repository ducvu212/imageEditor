package com.example.ducvu212.demomvvm.screen.edit;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemEdit;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;

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
    private ImageRepository mImageRepository;

    public HandleItemEditClick(SeekBar seekBar, ImageRepository repository, FrameLayout frameLayout,
            OnUpdateUI onUpdateUI) {
        mSeekBar = seekBar;
        mFrameLayout = frameLayout;
        mOnUpdateUI = onUpdateUI;
        mImageRepository = repository;
    }

    public void OnEditItemClickListener(ItemEdit itemEdit) {
        switch (itemEdit.getImageView()) {
            case R.drawable.ic_brightnes:
                mSeekBar.setProgress(0);
                mOnUpdateUI.updateContrast(0);
                mFrameLayout.setVisibility(View.VISIBLE);
                setupEdit(TITTLE_BRIGHTNESS);
                mType = TITTLE_BRIGHTNESS;
                break;
            case R.drawable.ic_contrast:
                mSeekBar.setProgress(0);
                mOnUpdateUI.updateBrightness(0);
                mFrameLayout.setVisibility(View.VISIBLE);
                setupEdit(TITTLE_CONTRAST);
                mType = TITTLE_CONTRAST;
                break;
            case R.drawable.ic_crop:

                break;
            case R.drawable.ic_draw:

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
}
