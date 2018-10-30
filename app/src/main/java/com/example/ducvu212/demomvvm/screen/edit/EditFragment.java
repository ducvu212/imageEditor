package com.example.ducvu212.demomvvm.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemColorPicker;
import com.example.ducvu212.demomvvm.data.model.ItemSticker;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageDatabase;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentEditBinding;
import com.example.ducvu212.demomvvm.screen.edit.adapter.ColorPickerAdapter;
import com.example.ducvu212.demomvvm.screen.edit.adapter.StickerAdapter;
import com.example.ducvu212.demomvvm.screen.editor.OnEditClickListener;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements OnUpdateUIListener {

    private static final int NUMBER_SPAN_COUNT = 4;
    private FragmentActivity mContext;
    private FragmentEditBinding mBinding;
    private OnEditClickListener mOnEditClickListener;
    private List<ItemColorPicker> mPickerList;
    private List<ItemSticker> mStickerList;

    public EditFragment() {

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false);
        initBinding();
        return mBinding.getRoot();
    }

    private void initBinding() {
        mPickerList = new ArrayList<>();
        mStickerList = new ArrayList<>();
        initSticker();
        intiViewModel();
        initColorPicker();
    }

    private void initSticker() {
        initListSticker();
        StickerAdapter adapter = new StickerAdapter(this);
        adapter.setStickerList(mStickerList);
        adapter.notifyDataSetChanged();
        mBinding.recyclerviewSticker.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.recyclerviewSticker.setAdapter(adapter);
    }

    private void initListSticker() {
        mStickerList.add(new ItemSticker(R.drawable.sticker_1, 1));
        mStickerList.add(new ItemSticker(R.drawable.sticker_2, 2));
        mStickerList.add(new ItemSticker(R.drawable.sticker_3, 3));
        mStickerList.add(new ItemSticker(R.drawable.sticker_4, 4));
        mStickerList.add(new ItemSticker(R.drawable.sticker_5, 5));
        mStickerList.add(new ItemSticker(R.drawable.sticker_6, 6));
        mStickerList.add(new ItemSticker(R.drawable.sticker_7, 7));
        mStickerList.add(new ItemSticker(R.drawable.sticker_8, 8));
    }

    private void initColorPicker() {
        intitListColorPicker();
        ColorPickerAdapter adapter = new ColorPickerAdapter(getContext(), this);
        adapter.setPickerList(mPickerList);
        adapter.notifyDataSetChanged();
        mBinding.recyclerViewColorPicker.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_SPAN_COUNT));
        mBinding.recyclerViewColorPicker.setAdapter(adapter);
    }

    private void intiViewModel() {
        ImageDatabase database = ImageDatabase.getInstance(getContext());
        ImageRepository imageRepository =
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO(), mContext));
        EditViewModel viewModel = new EditViewModel(getContext(), imageRepository, mBinding, this);
        viewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(viewModel);
        mBinding.setListener(new HandleItemEditClick(mBinding, this));
    }

    private void intitListColorPicker() {
        mPickerList.add(new ItemColorPicker(Color.WHITE, R.drawable.ic_white));
        mPickerList.add(new ItemColorPicker(Color.BLACK, R.drawable.ic_black));
        mPickerList.add(new ItemColorPicker(Color.BLUE, R.drawable.ic_blue));
        mPickerList.add(new ItemColorPicker(Color.YELLOW, R.drawable.ic_yellow));
        mPickerList.add(new ItemColorPicker(Color.RED, R.drawable.ic_red));
        mPickerList.add(new ItemColorPicker(Color.CYAN, R.drawable.ic_puple));
        mPickerList.add(new ItemColorPicker(Color.GRAY, R.drawable.ic_gray));
        mPickerList.add(new ItemColorPicker(Color.GREEN, R.drawable.ic_greeen));
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
    public void OnDoneClick(String type, String name) {
        mOnEditClickListener.OnDone(type, name);
    }

    @Override
    public void OnDrawClick() {
        mOnEditClickListener.OnDraw();
    }

    @Override
    public void OnChangeColor(int color) {
        mOnEditClickListener.OnChangeColor(color);
    }

    @Override
    public void OnUndo() {
        mOnEditClickListener.OnUndo();
    }

    @Override
    public void OnRedo() {
        mOnEditClickListener.OnRedo();
    }

    @Override
    public void OnClear() {
        mOnEditClickListener.OnClear();
    }

    @Override
    public void OnDrawComplete() {
        mOnEditClickListener.OnDrawComplete();
    }

    @Override
    public void OnCrop(boolean isCrop) {
        mOnEditClickListener.OnCrop();
    }

    @Override
    public void OnStickerItemClick(ItemSticker itemSticker) {
        mOnEditClickListener.OnSticker(itemSticker);
    }

    @Override
    public void OnStickerDoneClick() {
        mOnEditClickListener.OnStickerDone();
    }

    @Override
    public void OnStickerClearClick() {
        mOnEditClickListener.OnStickerClear();
    }
}
