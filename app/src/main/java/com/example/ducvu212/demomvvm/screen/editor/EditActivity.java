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
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.ActivityEditorBinding;
import com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.common.StringUtils;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator.mBrightnessCM;
import static com.example.ducvu212.demomvvm.screen.edit.HandleItemEditClick.TITTLE_BRIGHTNESS;

public class EditActivity extends AppCompatActivity implements OnEditClickListener {

    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static String mPath;
    public static String mName;
    public static Bitmap sBitmap;
    private ActivityEditorBinding mBinding;
    private ImageRandom mImageRandom;
    private int mProgress;
    private Exception mException;
    private ProgressDialog mProgressDialog;
    private EditorViewModel mEditorViewModel;

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
        ImageDatabase database = ImageDatabase.getInstance(this);
        mProgressDialog = new ProgressDialog(this);
        mEditorViewModel = new EditorViewModel(
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO())));
        mEditorViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mEditorViewModel);
        setupTabAdapter();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            mImageRandom = getIntent().getParcelableExtra(EXTRA_IMAGE);
        }
        mPath = mImageRandom.getPath();
        mName = mImageRandom.getImageId();
        try {
            sBitmap = mEditorViewModel.convertBitmap(mPath);
        } catch (ExecutionException e) {
            e.printStackTrace();
            DisplayUtils.makeToast(this, e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            DisplayUtils.makeToast(this, e.toString());
        }
        Picasso.get()
                .load(mPath)
                .placeholder(R.drawable.placeholder)
                .into(mBinding.imageViewContentEdit);
    }

    private void setupTabAdapter() {
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new EditAdapter(manager);
        mBinding.viewPagger.setAdapter(adapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPagger);
        mBinding.viewPagger.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
        mBinding.tabLayout.setTabsFromPagerAdapter(adapter);
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
        mProgressDialog.setMessage(getString(R.string.save_dialog));
        saveToInternalStorage(name, type);
    }

    @Override
    public void OnDrawClickListener() {
        mBinding.imageViewContentEdit.setZoomEnable(false);
        mBinding.drawView.setDrawingEnabled(true);
    }

    @Override
    public void OnChangeColorClickListener(int color) {
        mBinding.drawView.setPaintColor(color);
    }

    @Override
    public void OnUndoAction() {
        mBinding.drawView.onClickUndo();
    }

    @Override
    public void OnRedoAction() {
        mBinding.drawView.onClickRedo();
    }

    @Override
    public void OnClearAction() {
        mBinding.drawView.clearCanvas();
    }

    @Override
    public void OnDrawCompleteAction() {
        saveImage(mBinding.drawView.getBitmapMaster());
    }

    private void saveToInternalStorage(String name, String type) {
        BitmapDrawable draw = (BitmapDrawable) mBinding.imageViewContentEdit.getDrawable();
        Bitmap bitmap = draw.getBitmap();
        Bitmap newBitmap = changeBitmapContrastBrightness(bitmap, type);
        saveImage(newBitmap);
    }

    private void saveImage(Bitmap newBitmap) {
        mProgressDialog.setMessage(getString(R.string.save_dialog));
        File newFile = new File(StringUtils.buildPath(mName));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showError(e);
        } catch (IOException e) {
            e.printStackTrace();
            showError(e);
        }
        if (mException == null) {
            DisplayUtils.makeToast(this, getString(R.string.save_success));
        }
    }

    private void showError(Exception e) {
        mException = e;
        mProgressDialog.dismiss();
        DisplayUtils.makeToast(this, mException.getMessage());
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
