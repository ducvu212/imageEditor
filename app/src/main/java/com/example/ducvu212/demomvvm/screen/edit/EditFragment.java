package com.example.ducvu212.demomvvm.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentEditBinding;
import com.example.ducvu212.demomvvm.screen.editor.OnEditClickListener;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements OnUpdateUI {

    private EditViewModel mViewModel;
    private FragmentEditBinding mBinding;
    private ImageRepository mImageRepository;
    private OnEditClickListener mOnEditClickListener;

    public EditFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnEditClickListener = (OnEditClickListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false);
        initBinding();
        return mBinding.getRoot();
    }

    private void initBinding() {
        new BindingEdit(getContext());
        ImageDatabase database = ImageDatabase.getInstance(getContext());
        mImageRepository = ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                ImageLocalDataSource.getsInstance(database.mImageDAO()));
        mViewModel = new EditViewModel(getContext(), mImageRepository, mBinding.recyclerEdit,
                mBinding.seekBar, mBinding.frameSeekbar, this);
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mViewModel);
        mBinding.setListener(
                new HandleItemEditClick(mBinding.seekBar, mImageRepository, mBinding.frameSeekbar,
                        this));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void updateContrast(int progress) {
        mOnEditClickListener.OnUpdateContrast(progress);
    }

    @Override
    public void updateBrightness(int progress) {
        mOnEditClickListener.OnUpdateBrightness(progress);
    }

    @Override
    public void OnDoneClickListener(String type, String name) {
        mOnEditClickListener.OnDoneClickListener(type, name);
    }
}
