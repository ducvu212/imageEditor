package com.example.ducvu212.demomvvm.screen.home;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import com.example.ducvu212.demomvvm.data.model.Image;
import com.example.ducvu212.demomvvm.data.model.ItemViewPager;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.screen.base.BaseViewModel;
import com.example.ducvu212.demomvvm.utils.rx.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class HomeViewModel extends BaseViewModel implements LifecycleOwner {

    private Context mContext;
    private BaseSchedulerProvider mSchedulerProvider;
    private ImageRepository mRepository;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private LifecycleRegistry mLifecycleRegistry;
    private List<ItemViewPager> mItemViewPagers;
    private RandomPagerAdapter mAdapter;
    private List<Image> mImages;
    private ViewPager mPager;

    HomeViewModel(Context context, ViewPager viewPager, ImageRepository repository) {
        mContext = context;
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        mRepository = repository;
        mImages = new ArrayList<>();
        mItemViewPagers = new ArrayList<>();
        mPager = viewPager;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    protected void onStart() {
        getRandomImage();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    void setSchedulerProvider(BaseSchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    private void convertData(List<Image> images) {
        for (Image image : images) {
            mItemViewPagers.add(
                    new ItemViewPager.Builder().mPath(image.getUrls().getFull()).build());
        }
    }

    private void initData() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        convertData(mImages);
        float ratio = (100 * height) / width;
        new BindingMain(width, (float) (ratio * 0.01));
        mAdapter = new RandomPagerAdapter(mItemViewPagers);
        mPager.setAdapter(mAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RandomTimer(), 10000, 5000);
    }

    public RandomPagerAdapter getAdapter() {
        return mAdapter;
    }

    private void getRandomImage() {
        Disposable disposable = mRepository.getRandomImage()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(images -> {
                    mImages = images;
                    initData();
                }, throwable -> {

                });
        mCompositeDisposable.add(disposable);
    }

    private class RandomTimer extends TimerTask {
        private int mIndex = 0;

        @Override
        public void run() {
            ((Activity) mContext).runOnUiThread(() -> {
                mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        if (i == mItemViewPagers.size()) {
                            i = 0;
                        }
                        mIndex = i;
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                if (mIndex == mItemViewPagers.size()) {
                    mIndex = 0;
                }
                mPager.setCurrentItem(mIndex);
                mIndex++;
            });
        }
    }
}
