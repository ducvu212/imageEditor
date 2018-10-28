package com.example.ducvu212.demomvvm.screen.filtter;

import android.widget.SeekBar;
import com.example.ducvu212.demomvvm.data.model.ItemFilter;

public class HandleClickItemFilter {
    private OnUpdateUIFilter mOnUpdateUIFilter;
    private SeekBar mSeekBar;
    private int mProgress;

    public HandleClickItemFilter(OnUpdateUIFilter onUpdateUIFilter, SeekBar seekBar) {
        mOnUpdateUIFilter = onUpdateUIFilter;
        mSeekBar = seekBar;
    }

    public void updateFilter(ItemFilter itemFilter) {
        itemFilter.setCountClick(itemFilter.getCountClick() + 1);
        mOnUpdateUIFilter.OnUpdateBitmapBilter(itemFilter.getFilter(), itemFilter.getCountClick());
    }
}
