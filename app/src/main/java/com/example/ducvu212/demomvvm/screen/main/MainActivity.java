package com.example.ducvu212.demomvvm.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import com.example.ducvu212.demomvvm.databinding.ActivityMainBinding;
import com.example.ducvu212.demomvvm.screen.base.BaseActivity;
import com.example.ducvu212.demomvvm.screen.home.HomeFragment;
import com.example.ducvu212.demomvvm.utils.rx.SchedulerProvider;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding mMainBinding;
    private MainViewModel mMainViewModel;
    private ActionBarDrawerToggle mToggle;
    private FragmentManager.OnBackStackChangedListener mBackStackListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(
                    ContextCompat.getColor(this, R.color.color_material_blue_400));
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mMainViewModel.onStop();
        super.onStop();
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
    public void onBackPressed() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
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
        switch (itemId) {
            case android.R.id.home:
                break;

            case R.id.nav_home:
                fragment = HomeFragment.newInstance();
                break;
            case R.id.nav_offline:
                break;
            case R.id.nav_search:
                break;
            case R.id.nav_about_us:
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_main, fragment).commit();
        }
        DrawerLayout drawer = mMainBinding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
