package com.example.ducvu212.demomvvm.screen.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemViewPager;
import com.example.ducvu212.demomvvm.databinding.FragmentImageDetailsBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.screen.home.BindingHome;

/**
 * ImageDetails Screen.
 */
public class ImageDetailsFragment extends BaseFragment {

    public static final String TAG = ImageDetailsFragment.class.getSimpleName();
    private static final String ARGUMENT_IMAGE = "ARGUMENT_IMAGE";
    private FragmentActivity mContext;
    private ImageDetailsViewModel mViewModel;
    private FragmentImageDetailsBinding mBinding;
    private ActionBar mActionBar;

    public static ImageDetailsFragment newInstance(ItemViewPager itemViewPager) {
        ImageDetailsFragment fragment = new ImageDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_IMAGE, itemViewPager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ImageDetailsViewModel();
        mActionBar = ((AppCompatActivity) mContext).getSupportActionBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_details, container,
                false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        if (getArguments() != null) {
            ItemViewPager itemViewPager = getArguments().getParcelable(ARGUMENT_IMAGE);
            mBinding.setItem(itemViewPager);
            String urlImage = itemViewPager.getPath();
            BindingHome.loadImage(mBinding.imageViewContent, urlImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
