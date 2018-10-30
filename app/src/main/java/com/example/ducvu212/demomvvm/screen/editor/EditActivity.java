package com.example.ducvu212.demomvvm.screen.editor;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ImageRandom;
import com.example.ducvu212.demomvvm.data.model.ImageType;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.ActivityEditorBinding;
import com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator;
import com.example.ducvu212.demomvvm.screen.editor.sticker.StickerImageView;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.common.StringUtils;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.ducvu212.demomvvm.screen.edit.ColorFilterGenerator.mBrightnessCM;
import static com.example.ducvu212.demomvvm.screen.edit.HandleItemEditClick.TITTLE_BRIGHTNESS;

public class EditActivity extends AppCompatActivity
        implements OnEditClickListener, View.OnTouchListener {

    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    private static final String EXTRA_TYPE = "EXTRA_TYPE";
    private static final int RESULT_CROP = 111;
    public static String sPath;
    public static String sName;
    public static Bitmap sBitmap;
    private ActivityEditorBinding mBinding;
    private ImageRandom mImageRandom;
    private int mProgress;
    private Exception mException;
    private ProgressDialog mProgressDialog;
    private EditorViewModel mEditorViewModel;
    private int mImageWidth, mImageHeight;
    private Matrix matrix = new Matrix();
    private List<StickerImageView> mStickerImageViewList;
    private Bitmap mCropppedBitmap;
    private boolean mIsCrop;

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

    private static RequestCreator createCreator(String url) {
        return Picasso.get().load(url).placeholder(R.drawable.placeholder);
    }

    private void inits() {
        mStickerImageViewList = new ArrayList<>();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        ImageDatabase database = ImageDatabase.getInstance(this);
        mProgressDialog = new ProgressDialog(this);
        mEditorViewModel = new EditorViewModel(
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO(), this)));
        mEditorViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setListener(new HandleOnClickListener(this));
        mBinding.setViewModel(mEditorViewModel);
        setupTabAdapter();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            mImageRandom = getIntent().getParcelableExtra(EXTRA_IMAGE);
        }
        sPath = mImageRandom.getPath();
        sName = mImageRandom.getImageId();
        if (mImageRandom.getType().equals(ImageType.LOCAL)) {
            sBitmap = mEditorViewModel.convertbitmapGallary(sPath);
            Picasso.get()
                    .load(new File(sPath))
                    .placeholder(R.drawable.placeholder)
                    .into(mBinding.imageViewContentEdit);
        } else {
            try {
                sBitmap = mEditorViewModel.convertBitmap(sPath);
            } catch (ExecutionException e) {
                e.printStackTrace();
                DisplayUtils.makeToast(this, e.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
                DisplayUtils.makeToast(this, e.toString());
            }
            loadImage();
        }
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
        hideButtonCrop(GONE);
        mIsCrop = false;
    }

    @Override
    public void OnUpdateBrightness(int progress) {
        mBinding.imageViewContentEdit.setColorFilter(
                ColorFilterGenerator.adjustBrightness(progress));
        mProgress = progress;
        hideButtonCrop(GONE);
        mIsCrop = false;
    }

    private void loadImage() {
       mBinding.imageViewContentEdit.setImageBitmap(sBitmap);
    }

    @Override
    public void OnDraw() {
        mBinding.imageViewContentEdit.setZoomEnable(false);
        mBinding.drawView.setDrawingEnabled(true);
        hideButtonCrop(GONE);
        mIsCrop = false;
    }

    @Override
    public void OnChangeColor(int color) {
        mBinding.drawView.setPaintColor(color);
    }

    @Override
    public void OnDone(String type, String name) {
        mProgressDialog.setMessage(getString(R.string.save_dialog));
        saveToInternalStorage(type);
    }

    @Override
    public void OnUndo() {
        mBinding.drawView.onClickUndo();
    }

    @Override
    public void OnRedo() {
        mBinding.drawView.onClickRedo();
    }

    @Override
    public void OnClear() {
        mBinding.drawView.clearCanvas();
    }

    @Override
    public void OnDrawComplete() {
        saveImage(getBitmapView(), false);
    }

    @Override
    public void OnCrop() {
        mIsCrop = true;
        hideButtonCrop(View.VISIBLE);
        if (mImageRandom.getType().equals(ImageType.REMOTE)) {
            performCrop(StringUtils.buildPath(sName));
        } else {
            performCrop(mImageRandom.getPath());
        }
    }

    @Override
    public void OnSticker(ItemSticker itemSticker) {
        hideButtonCrop(GONE);
        StickerImageView imageView = new StickerImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(itemSticker.getDrawable()));
        setHidden(false);
        mBinding.constraintEdit.addView(imageView);
        mStickerImageViewList.add(imageView);
        mIsCrop = false;
    }

    private void setHidden(boolean isHidden) {
        for (StickerImageView i : mStickerImageViewList) {
            i.setHidden(isHidden);
        }
    }

    @Override
    public void OnFilter(Bitmap bitmap) {
        mIsCrop = false;
        hideButtonCrop(VISIBLE);
        mBinding.imageViewContentEdit.setImageBitmap(bitmap);
    }

    @Override
    public void OnStickerDone() {
        saveImage(getBitmapView(), false);
    }

    @Override
    public void OnStickerClear() {
        deleteSticker();
        hideButtonCrop(GONE);
    }

    @Override
    public void OnDoneCrop(boolean isCrop) {
        if (mIsCrop) {
            saveImage(mCropppedBitmap, true);
            hideButtonCrop(GONE);
        } else {
            saveFilterToInternalStorage();
        }
    }

    private void hideButtonCrop(int visible) {
        mBinding.imageViewCropClear.setVisibility(visible);
        mBinding.imageViewCropDone.setVisibility(visible);
    }

    @Override
    public void OnClearCrop() {
        loadImage();
    }

    @Override
    public void OnDoneFilter() {
        saveFilterToInternalStorage();
    }

    private void deleteSticker() {
        for (StickerImageView i : mStickerImageViewList) {
            i.deleteSticker();
        }
        mStickerImageViewList.clear();
    }

    private void performCrop(String picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            File f = new File(picUri);
            Uri contentUri = FileProvider.getUriForFile(this,
                    this.getPackageName() + ".com.example.ducvu212.demomvvm.provider", f);
            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", sBitmap.getWidth());
            cropIntent.putExtra("aspectY", sBitmap.getHeight());
            cropIntent.putExtra("outputX", sBitmap.getWidth());
            cropIntent.putExtra("outputY", sBitmap.getHeight());
            cropIntent.putExtra("scale", true);
            //            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            cropIntent.putExtra("return-data", true);
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(cropIntent, RESULT_CROP);
        } catch (ActivityNotFoundException anfe) {
            DisplayUtils.makeToast(this, getString(R.string.crop_error));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mCropppedBitmap = extras.getParcelable("data");
            mBinding.imageViewContentEdit.setImageBitmap(mCropppedBitmap);
        }
    }

    private void saveToInternalStorage(String type) {
        BitmapDrawable draw = (BitmapDrawable) mBinding.imageViewContentEdit.getDrawable();
        Bitmap bitmap = draw.getBitmap();
        Bitmap newBitmap = changeBitmapContrastBrightness(bitmap, type);
        saveImage(newBitmap, false);
    }

    private void saveFilterToInternalStorage() {
        BitmapDrawable draw = (BitmapDrawable) mBinding.imageViewContentEdit.getDrawable();
        Bitmap bitmap = draw.getBitmap();
        saveImage(bitmap, false);
    }

    private void saveImage(Bitmap newBitmap, boolean isCrop) {
        mProgressDialog.setMessage(getString(R.string.save_dialog));
        File newFile;
        if (isCrop) {
            newFile = new File(StringUtils.buildPath(sName));
        } else {
            newFile = new File(StringUtils.buildPath(sName));
        }
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
            sBitmap = newBitmap;
            mBinding.imageViewContentEdit.setImageBitmap(sBitmap);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float scaleFactor = 0.5f;
        float scaleImageCenterX = (mImageWidth * scaleFactor) / 2;
        float scaleImageCenterY = (mImageHeight * scaleFactor) / 2;
        matrix.reset();
        matrix.postScale(scaleFactor, scaleFactor);
        float rotationDegree = 0.f;
        matrix.postRotate(rotationDegree, scaleImageCenterX,
                scaleImageCenterY);
        float focusX = 0.f;
        float focusY = 0.f;
        matrix.postTranslate(focusX - scaleImageCenterX,
                focusY - scaleImageCenterY);
        ImageView view = (ImageView) v;
        view.setImageMatrix(matrix);
        return true;
    }

    public Bitmap getBitmapView() {
        setHidden(true);
        View v = mBinding.constraintEdit;
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = v.getDrawingCache();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, sBitmap.getWidth(),
                mBinding.imageViewContentEdit.getHeight());
        return bitmap;
    }
}
