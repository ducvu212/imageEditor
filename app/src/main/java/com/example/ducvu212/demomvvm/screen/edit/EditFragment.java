package com.example.ducvu212.demomvvm.screen.edit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class EditFragment extends Fragment implements OnUpdateUI {

    private FragmentEditBinding mBinding;
    private OnEditClickListener mOnEditClickListener;
    private List<ItemColorPicker> mPickerList;
    private List<ItemSticker> mStickerList;

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
        mBinding.recyclerViewColorPicker.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.recyclerViewColorPicker.setAdapter(adapter);
    }

    private void intiViewModel() {
        ImageDatabase database = ImageDatabase.getInstance(getContext());
        ImageRepository imageRepository =
                ImageRepository.getsInstance(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance(database.mImageDAO()));
        EditViewModel viewModel = new EditViewModel(getContext(), imageRepository, mBinding, this);
        viewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(viewModel);
        mBinding.setListener(new HandleItemEditClick(imageRepository, mBinding, this));
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
    public void OnDoneClickListener(String type, String name) {
        mOnEditClickListener.OnDoneClickListener(type, name);
    }

    @Override
    public void OnDrawClickListener() {
        mOnEditClickListener.OnDrawClickListener();
    }

    @Override
    public void OnChangeColorListener(int color) {
        mOnEditClickListener.OnChangeColorClickListener(color);
    }

    @Override
    public void OnUndoListener() {
        mOnEditClickListener.OnUndoListener();
    }

    @Override
    public void OnRedoListener() {
        mOnEditClickListener.OnRedoListener();
    }

    @Override
    public void OnClearListener() {
        mOnEditClickListener.OnClearListener();
    }

    @Override
    public void OnDrawCompleteListener() {
        mOnEditClickListener.OnDrawCompleteListener();
    }

    @Override
    public void OnCropListener() {
        mOnEditClickListener.OnCropListener();
    }

    @Override
    public void OnStickerItemClickListener(ItemSticker itemSticker) {
        mOnEditClickListener.OnStickerItemClickListener(itemSticker);
    }

    @Override
    public void OnStickerDoneClickListener() {
        mOnEditClickListener.OnStickerDoneClickListener();
    }

    @Override
    public void OnStickerClearClickListener() {
        mOnEditClickListener.OnStickerClearClickListener();
    }
}
