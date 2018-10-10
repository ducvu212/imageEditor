package com.example.ducvu212.demomvvm.screen.editor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.databinding.ActivityEditorBinding;
import com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.ducvu212.demomvvm.data.source.remote.Download.IMAGE_DIRECTORY;
import static com.example.ducvu212.demomvvm.data.source.remote.Download.IMAGE_FILE_EXTENSION;
import static com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator.mBrightnessCM;
import static com.example.ducvu212.demomvvm.screen.edit.HandleItemEditClick.TITTLE_BRIGHTNESS;

public class EditActivity extends AppCompatActivity implements OnEditClickListener {

    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static String mPath;
    public static String mName;
    private EditorViewModel mEditorViewModel;
    private ActivityEditorBinding mBinding;
    private ImageRandom mImageRandom;
    private int mProgress;
    private Exception mException;

    public static Intent getProfileIntent(Context context, ImageRandom imageRandom) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(EXTRA_IMAGE, imageRandom);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inits();
    }

    private void inits() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new EditAdapter(manager);
        mEditorViewModel = new EditorViewModel();
        mEditorViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mEditorViewModel);
        mBinding.viewPagger.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPagger);
        mBinding.viewPagger.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.setTabsFromPagerAdapter(adapter);
        if (getIntent() != null) {
            mImageRandom = getIntent().getParcelableExtra(EXTRA_IMAGE);
        }
        mPath = mImageRandom.getPath();
        mName = mImageRandom.getImageId();
        Picasso.get()
                .load(mPath)
                .placeholder(R.drawable.placeholder)
                .into(mBinding.imageViewContentEdit);
    }

    @Override
    public void OnUpdateContrast(int progress) {
        mBinding.imageViewContentEdit.setColorFilter(ColorFilterGenerator.adjustContrast(progress));
        mProgress = progress;
    }

    @Override
    public void OnUpdateBrightness(int progress) {
        mBinding.imageViewContentEdit.setColorFilter(
                ColorFilterGenerator.adjustBrightness(progress));
        mProgress = progress;
    }

    @Override
    public void OnDoneClickListener(String type, String name) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.save_dialog));
        saveToInternalStorage(progressDialog, name, type);
    }

    private void saveToInternalStorage(ProgressDialog progressDialog, String name, String type) {
        BitmapDrawable draw = (BitmapDrawable) mBinding.imageViewContentEdit.getDrawable();
        Bitmap bitmap = draw.getBitmap();
        Bitmap newBitmap = changeBitmapContrastBrightness(bitmap, type);
        StringBuilder builder = new StringBuilder();
        builder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                .append(IMAGE_DIRECTORY)
                .append(name)
                .append(IMAGE_FILE_EXTENSION);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(builder.toString());
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                mException = e;
            }
        }
        if (mException != null) {
            progressDialog.dismiss();
            DisplayUtils.makeToast(this, mException.getMessage());
        } else {
            progressDialog.dismiss();
            DisplayUtils.makeToast(this, getString(R.string.save_success));
        }
    }

    public Bitmap changeBitmapContrastBrightness(Bitmap bmp, String type) {
        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();
        if (type.equals(TITTLE_BRIGHTNESS)) {
            paint.setColorFilter(new ColorMatrixColorFilter(mBrightnessCM));
        } else {
            paint.setColorFilter(ColorFilterGenerator.adjustContrast(mProgress));
        }
        canvas.drawBitmap(bmp, 0, 0, paint);
        return ret;
    }
}
