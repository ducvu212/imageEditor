package com.example.ducvu212.demomvvm.screen.library;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.Album;
import com.example.ducvu212.demomvvm.databinding.ItemLibraryBinding;
import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder> {
    private List<Album> mAlbums;
    private FragmentManager mManager;

    public LibraryAdapter(FragmentManager manager) {
        mManager = manager;
        mAlbums = new ArrayList<>();
    }

    public void setAlbums(List<Album> albums) {
        mAlbums.addAll(albums);
    }

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLibraryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_library, viewGroup, false);
        return new LibraryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder libraryHolder, int i) {
        libraryHolder.binding(mAlbums.get(i), mManager);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    static class LibraryHolder extends RecyclerView.ViewHolder {
        private ItemLibraryBinding mBinding;

        public LibraryHolder(@NonNull ItemLibraryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void binding(Album album, FragmentManager manager) {
            mBinding.setAlbum(album);
            mBinding.setListener(new HandleClickLibrary(manager));
            mBinding.executePendingBindings();
        }
    }
}
