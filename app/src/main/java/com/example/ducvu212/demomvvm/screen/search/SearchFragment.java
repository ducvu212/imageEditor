package com.example.ducvu212.demomvvm.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.RecentSearch;
import com.example.ducvu212.demomvvm.data.repository.ImageRepository;
import com.example.ducvu212.demomvvm.data.source.local.ImageLocalDataSource;
import com.example.ducvu212.demomvvm.data.source.remote.ImageRemoteDataSource;
import com.example.ducvu212.demomvvm.databinding.FragmentSearchBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

public class SearchFragment extends BaseFragment implements SearchView.OnQueryTextListener {

    private static SearchFragment sInstance;
    private FragmentActivity mContext;
    private SearchViewModel mViewModel;
    private FragmentSearchBinding mBinding;

    public static SearchFragment newInstance() {
        if (sInstance == null) {
            sInstance = new SearchFragment();
        }
        return sInstance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        mBinding.searchViewCollection.setOnQueryTextListener(this);
        mBinding.setListener(new HandleClick(getActivity()));
        initBinding();
        return mBinding.getRoot();
    }

    private void initBinding() {
        mViewModel = new SearchViewModel((mContext),
                new ImageRepository(ImageRemoteDataSource.getsInstance(),
                        ImageLocalDataSource.getsInstance()), mContext.getSupportFragmentManager());
        mViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mBinding.setViewModel(mViewModel);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        mViewModel.subcribeCollection(query);
        mViewModel.putRecentSearchToRealm(query);
        mViewModel.subcribeRecentSearch();
        mBinding.recyclerSearch.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            mBinding.groupSearch.setVisibility(View.VISIBLE);
            mBinding.recyclerSearch.setVisibility(View.GONE);
        } else {
            mBinding.groupSearch.setVisibility(View.GONE);
        }
        return true;
    }

    public void clickItem(String item) {
        mBinding.searchViewCollection.setQuery(item, true);
        mViewModel.subcribeCollection(item);
    }

    public void deleteRecentSearch(RecentSearch recentSearch) {
        mViewModel.deleteRecentSearch(recentSearch);
        mViewModel.subcribeRecentSearch();
    }
}
