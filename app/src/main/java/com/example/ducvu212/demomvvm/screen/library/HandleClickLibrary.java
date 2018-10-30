package com.example.ducvu212.demomvvm.screen.library;

import android.support.v4.app.FragmentManager;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.screen.album.AlbumFragment;
import com.example.ducvu212.demomvvm.utils.common.FragmentTransactionUtils;

public class HandleClickLibrary {

    private FragmentManager mManager;

    HandleClickLibrary(FragmentManager manager) {
        mManager = manager;
    }

    public void OnItemAlbumClickListener(Album album) {
        FragmentTransactionUtils.addFragment(mManager,
                AlbumFragment.newInstance(album.getTitle()),
                R.id.frame_main, AlbumFragment.TAG, true);
    }
}
