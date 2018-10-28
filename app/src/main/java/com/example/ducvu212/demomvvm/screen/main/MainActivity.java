package com.example.ducvu212.demomvvm.screen.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ducvu212.demomvvm.R;
import com.example.ducvu212.demomvvm.data.model.ImageType;
import com.example.ducvu212.demomvvm.databinding.ActivityMainBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseActivity;
import com.example.ducvu212.demomvvm.screen.home.BindingHome;
import com.example.ducvu212.demomvvm.screen.home.HomeFragment;
import com.example.ducvu212.demomvvm.screen.library.LibraryFragment;
import com.example.ducvu212.demomvvm.screen.search.SearchFragment;
import com.example.ducvu212.demomvvm.utils.common.DisplayUtils;
import com.example.ducvu212.demomvvm.utils.common.FragmentTransactionUtils;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;
import java.io.File;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSION = 111;
    private ActivityMainBinding mMainBinding;
    private MainViewModel mMainViewModel;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager.OnBackStackChangedListener mBackStackListener;

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainViewModel.onStart();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void init() {
        initBinding();
        initNavigation();
    }

    private void initBinding() {
        mMainViewModel = new MainViewModel();
        mMainViewModel.setSchedulerProvider(SchedulerProvider.getInstance());
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setViewModel(mMainViewModel);
        mMainBinding.executePendingBindings();
    }

    private void initNavigation() {
        Toolbar toolbar = mMainBinding.appbar.toolbar;
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = mMainBinding.drawerLayout;
        mToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mToggle.setToolbarNavigationClickListener(view -> onBackPressed());
        mBackStackListener = () -> {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            mToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
        };
        getSupportFragmentManager().addOnBackStackChangedListener(mBackStackListener);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView navigationView = mMainBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPer();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(this, R.color.color_material_blue_400));
        }
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mToggle.isDrawerIndicatorEnabled() && mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (id) {
            case R.id.action_search:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        displaySelectedScreen(menuItem.getItemId());
        return true;
    }

    public void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        String tag = null;
        switch (itemId) {
            case android.R.id.home:
                break;

            case R.id.nav_home:
                fragment = HomeFragment.newInstance();
                tag = HomeFragment.TAG;
                BindingHome.setmType(ImageType.REMOTE);
                break;
            case R.id.nav_offline:
                fragment = LibraryFragment.newInstance();
                tag = LibraryFragment.TAG;
                BindingHome.setmType(ImageType.LOCAL);
                break;
            case R.id.nav_search:
                fragment = SearchFragment.newInstance();
                tag = SearchFragment.TAG;
                BindingHome.setmType(ImageType.REMOTE);
                break;
            case R.id.nav_about_us:
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentTransactionUtils.addFragment(getSupportFragmentManager(), fragment,
                    R.id.frame_main, tag, false);
        }
        DrawerLayout drawer = mMainBinding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        mMainViewModel.onStop();
        deleteCache(this);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void checkPer() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            DisplayUtils.makeToast(this, getString(R.string.permission_error));
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
    }
}
