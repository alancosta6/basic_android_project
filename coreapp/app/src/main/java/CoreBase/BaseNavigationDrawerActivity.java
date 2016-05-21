package CoreBase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import Util.ViewUtil;
import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 5/19/16.
 */
public abstract class BaseNavigationDrawerActivity extends AppIndexingActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_base_activity);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.core_base_toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);

        ViewUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));


        // update the main content by replacing fragments
        if (createMainFragment() != null) {

            addFragment(createMainFragment());
        }

//        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflator.inflate(R.layout.view_ab, null);
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT, ViewUtil.gravityLeft() );
//        getSupportActionBar().setCustomView(view, params);


        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP |
                    ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


//        if (!showToolbarTitle()) {
//
//            toolbar.setTitle(StringUtil.EMPTY_STRING);
//        }


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


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

        return toolbar;
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    public int viewIndexInNavigationDrawer() {
        return 0;
    }

    @Override
    public boolean isHomeAsUp() {
        return false;
    }


}
