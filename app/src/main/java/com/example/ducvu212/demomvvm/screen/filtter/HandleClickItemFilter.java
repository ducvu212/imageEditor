package com.example.ducvu212.demomvvm.screen.filtter;

import com.example.ducvu212.demomvvm.data.model.ItemFilter;

public class HandleClickItemFilter {
    private OnUpdateUIFilter mOnUpdateUIFilter;

    public HandleClickItemFilter(OnUpdateUIFilter onUpdateUIFilter) {
        mOnUpdateUIFilter = onUpdateUIFilter;
    }

    public void updateFilter(ItemFilter itemFilter) {
        itemFilter.setCountClick(itemFilter.getCountClick() + 1);
        mOnUpdateUIFilter.OnUpdateBitmapFilter(itemFilter.getFilter(), itemFilter.getCountClick());
    }

    public void saveFilter() {
        mOnUpdateUIFilter.OnSaveFilter();
    }
}
