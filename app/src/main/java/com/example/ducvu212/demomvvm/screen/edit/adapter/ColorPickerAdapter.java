package com.example.ducvu212.demomvvm.screen.edit.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemColorPicker;
import com.example.ducvu212.demomvvm.databinding.ItemColorPickerBinding;
import com.example.ducvu212.demomvvm.screen.edit.HandleItemEditClick;
import com.example.ducvu212.demomvvm.screen.edit.OnUpdateUIListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuD HniM on 18/10/11.
 */
public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ColorHolder> {

    private Context mContext;
    private List<ItemColorPicker> mPickerList;
    private OnUpdateUIListener mOnUpdateUIListener;

    public ColorPickerAdapter(Context context, OnUpdateUIListener updateUI) {
        mContext = context;
        mPickerList = new ArrayList<>();
        mOnUpdateUIListener = updateUI;
    }

    public void setPickerList(List<ItemColorPicker> pickerList) {
        mPickerList.addAll(pickerList);
    }

    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemColorPickerBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_color_picker, parent, false);
        return new ColorHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorHolder holder, int position) {
        holder.binding(mContext, mPickerList.get(position), mOnUpdateUIListener);
    }

    @Override
    public int getItemCount() {
        return mPickerList == null ? 0 : mPickerList.size();
    }

    static class ColorHolder extends RecyclerView.ViewHolder {

        private ItemColorPickerBinding mBinding;

        ColorHolder(ItemColorPickerBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        void binding(Context context, ItemColorPicker itemColorPicker, OnUpdateUIListener onUpdateUIListener) {
            mBinding.imageColor.setImageDrawable(context.getDrawable(itemColorPicker.getId()));
            mBinding.setColor(itemColorPicker);
            mBinding.setListener(new HandleItemEditClick(onUpdateUIListener));
            mBinding.executePendingBindings();
        }
    }
}
