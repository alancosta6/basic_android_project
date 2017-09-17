package corebase;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import Util.StringUtil;
import Util.TrackerUtil;
import Util.ViewUtil;
import acosta.co.nz.coreapp.R;
import login.LoginActivity;

/**
 * Created by alancosta on 5/19/16.
 */
public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final int SNACK_BAR_DURATION_MILLIS = 5000;
    private static final int FULL_SCREEN_LOADING_INDICATOR_FADE_IN_MILLIS = 1000;
    private static final String TAG = BaseNavigationDrawerActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private Toolbar mToolbar;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private LinearLayout mFullScreenProgress;

    protected abstract Fragment superMainFragment();

    protected abstract boolean superHideToolbar();

    protected abstract boolean superLockDrawer();

    protected abstract void superFabControl(FloatingActionButton mFab);

    protected abstract boolean superEnableSwipeRefresh();

    protected abstract void superOnRefreshCalled();

    protected abstract String superScreenName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TrackerUtil.getInstance().trackScreen(this, superScreenName());

        ViewUtil.enableTranslucentStatusBar(this);
        setContentView(R.layout.core_base_activity);
        setUpViewElements();

        buildToolbar();
        setUpNavigationDrawer();
        superFabControl(mFab);
        buildSwipeRefreshLayout();
        handleNavigationDrawerHeader();

        // update the main content by replacing fragments
        if (superMainFragment() != null) {
            replaceFragment(superMainFragment());
        }

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP |
                    ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setUpViewElements() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.core_base_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.core_base_appbar_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.core_base_coordinator_layout);
        mNavigationView = (NavigationView) findViewById(R.id.core_base_navigation_view);
        mFab = (FloatingActionButton) findViewById(R.id.core_base_fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.core_base_swipe_refresh);
        mFullScreenProgress = (LinearLayout) findViewById(R.id.core_base_full_screen_loader);
        mProgressBar = (ProgressBar) findViewById(R.id.core_base_full_screen_loader_progressbar);

        int progressBarColor = ContextCompat.getColor(this, R.color.base_nav_drawer_full_screen_progressbar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.MULTIPLY);

    }


    private void buildToolbar() {

        if (mToolbar == null) {
            finish();
            return;
        }

        hideOrShowToolbar(superHideToolbar());

        int toolbarColor = ContextCompat.getColor(this, R.color.color_primary);
        mToolbar.setBackgroundColor(toolbarColor);
        ViewUtil.setStatusBarColor(this, toolbarColor);
        setSupportActionBar(mToolbar);
    }

    public void hideOrShowToolbar(boolean isShow) {

        if (mToolbar == null) {
            return;
        }

        if (isShow) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }


    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.core_base_frame_layout, fragment).commit();
    }


    public void addFragmentWithStack(Fragment fragment, String backStackTag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(backStackTag);
        fragmentTransaction.add(R.id.core_base_frame_layout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Log.d(TAG, item.getTitle().toString());

        //TODO: HANDLE MENU ITEM CLICKS

        return true;
    }


    private void handleNavigationDrawerHeader() {

        int headerCount = mNavigationView.getHeaderCount();
        if (headerCount > 0) {

            View headerView = mNavigationView.getHeaderView(0);
            ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BaseNavigationDrawerActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            });

        }


    }


    private void setUpNavigationDrawer() {

        if (mDrawerLayout == null || mToolbar == null) {
            return;
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                supportInvalidateOptionsMenu(); // calls
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                supportInvalidateOptionsMenu();
            }
        };

        // Defer code dependent on restoration of previous instance state.
        if (true) {

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

        } else {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    onBackPressed();

                }
            });
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (superLockDrawer()) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    private void buildSwipeRefreshLayout() {

        if (mSwipeRefreshLayout == null) {
            return;
        }

        if (mSwipeRefreshLayout.isRefreshing()) {
            dismissLoadingIndicator();
        }

        mSwipeRefreshLayout.setEnabled(superEnableSwipeRefresh());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh called");
                superOnRefreshCalled();
                showLoadingIndicator();
            }
        });
    }


    public void showLoadingFullScreen() {

        if(mFullScreenProgress == null) {
            finish();
            return;
        }

        mFullScreenProgress.bringToFront();
        mFullScreenProgress.setVisibility(View.VISIBLE);
        mFullScreenProgress.setAlpha(0.0f);
        mFullScreenProgress.animate().alpha(1.0f).setDuration(FULL_SCREEN_LOADING_INDICATOR_FADE_IN_MILLIS);

    }

    public void hideLoadingFullScreen() {

        if(mFullScreenProgress == null) {
            finish();
            return;
        }
        mFullScreenProgress.setVisibility(View.GONE);
    }


    public void showLoadingIndicator() {
        if (mSwipeRefreshLayout == null) {
            finish();
            return;
        }
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void dismissLoadingIndicator() {
        if (mSwipeRefreshLayout == null) {
            finish();
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void showSnackBar(String message) {
        showSnackBar(message, null, null);
    }

    public void showSnackBar(String message, String action, View.OnClickListener onClickListener) {

        if (coordinatorLayout == null) {
            return;
        }

        if (!StringUtil.isEmpty(action) && onClickListener != null) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, message, SNACK_BAR_DURATION_MILLIS).setAction(action, onClickListener);
            snackbar.show();
        } else if (!StringUtil.isEmpty(message)) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, message, SNACK_BAR_DURATION_MILLIS);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START, true);
        } else {
            super.onBackPressed();
        }
    }
}
