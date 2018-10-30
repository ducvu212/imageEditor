package com.example.ducvu212.demomvvm.screen.filtter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentFillterBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.screen.editor.OnEditClickListener;
import com.example.ducvu212.demomvvm.screen.library.LibraryFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends BaseFragment implements OnUpdateUIFilter {

    public static final String ARGUMENT_FILTER = "ARGUMENT_FILTER";
    public static final String TAG = LibraryFragment.class.getSimpleName();
    private FragmentActivity mContext;
    private FilterViewModel mViewModel;
    private FragmentFillterBinding mBinding;
    private Bitmap mPath;
    private OnEditClickListener mOnEditClickListener;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
        mOnEditClickListener = (OnEditClickListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fillter, container, false);
        initBinding();
        return mBinding.getRoot();
    }

    private void initBinding() {
        if (getArguments() != null) {
            mPath = getArguments().getParcelable(ARGUMENT_FILTER);
        }
        ImageDatabase database = ImageDatabase.getInstance(mContext);
        mViewModel = new FilterViewModel(new ImageRepository(ImageRemoteDataSource.getsInstance(),
                ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext)), mPath, this);
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mViewModel);
        mBinding.setListener(new HandleClickItemFilter(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
        if (((AppCompatActivity) mContext).getSupportActionBar() != null) {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle("Library");
        }
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }


    @Override
    public void OnUpdateBitmapFilter(Bitmap bitmap, int count) {
        mOnEditClickListener.OnFilter(bitmap);
    }

    @Override
    public void OnSaveFilter() {
        mOnEditClickListener.OnDoneFilter();
    }
}
