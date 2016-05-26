package CoreBase;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import Util.ViewUtil;
import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 5/19/16.
 */
public abstract class BaseNavigationDrawerActivity extends AppIndexingActivity implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_base_activity);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        mToolbar = (Toolbar) findViewById(R.id.core_base_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);




        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(mToolbar);

        ViewUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        // update the main content by replacing fragments
        if (createMainFragment() != null) {

            addFragment(createMainFragment());
        }


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP |
                    ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        setUpNavigationDrawer(mDrawerLayout,mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);


    }


    protected abstract Fragment createMainFragment();


    public void addFragment(Fragment fragment) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.core_base_frame_layout, fragment).commit();

    }


    public void addFragmentWithStack(Fragment fragment, String backStackTag) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(backStackTag);
        fragmentTransaction.add(R.id.core_base_frame_layout, fragment);

        fragmentTransaction.commit();
    }


    public Toolbar getToolbar() {

        return mToolbar;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        return true;
    }




    public void setUpNavigationDrawer(DrawerLayout drawerLayout, final Toolbar toolbar) {


        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

            drawerLayout.post(new Runnable() {
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
        drawerLayout.addDrawerListener(mDrawerToggle);


    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT)){

            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {

            super.onBackPressed();
        }
    }
}
