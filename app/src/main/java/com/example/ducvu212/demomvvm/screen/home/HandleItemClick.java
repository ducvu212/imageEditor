package com.example.ducvu212.demomvvm.screen.home;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ItemViewPager;
import com.example.ducvu212.demomvvm.screen.details.ImageDetailsFragment;
import com.example.ducvu212.demomvvm.utils.common.FragmentTransactionUtils;

/**
 * Created by CuD HniM on 18/10/05.
 */
public class HandleItemClick {
    private Context mContext;
    private FragmentManager mManager;

    HandleItemClick(Context context, FragmentManager manager) {
        mContext = context;
        mManager = manager;
    }

    public void OnItemClickListener(ItemViewPager itemViewPager) {
        FragmentTransactionUtils.addFragment(mManager,
                ImageDetailsFragment.newInstance(itemViewPager), R.id.relative_main,
                ImageDetailsFragment.TAG, true);
    }
}
