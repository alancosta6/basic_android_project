package CoreBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import acosta.co.nz.coreapp.R;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {


    private static final int UNDEFINED_ADAPTER_POS = -1;

    final int[] menuIcons = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher
    };

    final String[] menuOptsArray = {
            "menu_home",
            "menu_home",
            "menu_home",
            "menu_home"
    };


    /**
     * Remember the position of the selected item.
     */
//	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the
     * user manually expands it. This shared preference tracks this.
     */
//	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ArrayAdapter<String> mDrawerListViewAdapter;
    private View mFragmentContainerView;

    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

//	private static final boolean AUTOLOGIN = true;

    private static final boolean DISABLED_ANON_LOGIN = false;

    private static View rootView = null;


    public NavigationDrawerFragment() {

    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated
        // awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
//		SharedPreferences sp = PreferenceManager
//				.getDefaultSharedPreferences(getActivity());
//		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        mUserLearnedDrawer = true;

        if (savedInstanceState != null) {
//			mCurrentSelectedPosition = savedInstanceState
//					.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of
        // actions in the action bar.
        setHasOptionsMenu(true);
    }


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.core_base_navigation_drawer_fragment, container, false);

        mDrawerListView = (ListView) rootView.findViewById(R.id.navigation_drawer_items);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectItem(position);
            }
        });





        mDrawerListViewAdapter = new ArrayAdapter<String>(getActivity(), R.layout.core_base_navigation_drawer_item, R.id.menu_item_label, menuOptsArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ImageView menu_item_icon = (ImageView) v.findViewById(R.id.menu_item_icon);
                menu_item_icon.setImageResource(menuIcons[position]);

                if (mDrawerListView.getCheckedItemPosition() > UNDEFINED_ADAPTER_POS && mDrawerListView.getCheckedItemPosition() == position + 1) {

//					ViewUtil.setBackground(v, ViewUtil.getDrawable(v.getContext(), R.drawable.ripple_rectangle_gray_tabbed));
                } else {
//					ViewUtil.setBackground(v, ViewUtil.getDrawable(v.getContext(), R.drawable.ripple_rectangle_white));
                }


                return v;
            }
        };

        mDrawerListView.setAdapter(mDrawerListViewAdapter);


        return rootView;
    }


    public void highlightMenuIndex(int position) {

        mDrawerListView.clearChoices();
        mDrawerListViewAdapter.notifyDataSetChanged();

        switch (position) {
//		case Defs.MENU_ITEM_HOME:
//			mDrawerListView.setItemChecked(Defs.MENU_ITEM_HOME,true);
//			break;
//		case Defs.MENU_ITEM_CATEGORIES:
//			mDrawerListView.setItemChecked(Defs.MENU_ITEM_CATEGORIES,true);
//			break;
//		case Defs.MENU_ITEM_BEST_DEALS:
//			mDrawerListView.setItemChecked(Defs.MENU_ITEM_BEST_DEALS,true);
//			break;
//		case Defs.MENU_ITEM_FAVORITES:
//			View menu_myProducts = headerView.findViewById(R.id.menu_my_products);
//			ViewUtil.setBackground(menu_myProducts, ViewUtil.getDrawable(headerView.getContext(), R.drawable.ripple_rectangle_gray_tabbed));
//			break;
//		case Defs.MENU_ITEM_ALERTS:
//			View menu_myAlerts = headerView.findViewById(R.id.menu_my_alerts);
//			ViewUtil.setBackground(menu_myAlerts, ViewUtil.getDrawable(headerView.getContext(), R.drawable.ripple_rectangle_gray_tabbed));
//			break;
//		case Defs.MENU_ITEM_ABOUT_WARRANTY:
//			TextView about_zoom = (TextView) footerView.findViewById(R.id.about_zoom);
//			ViewUtil.setBackground(about_zoom, ViewUtil.getDrawable(rootView.getContext(), R.drawable.ripple_rectangle_gray_tabbed));
//			break;
//		case Defs.MENU_ITEM_ABOUT_APP:
//			TextView about_app = (TextView) footerView.findViewById(R.id.about_app);
//			ViewUtil.setBackground(about_app, ViewUtil.getDrawable(rootView.getContext(), R.drawable.ripple_rectangle_gray_tabbed));
//			break;


            default:
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        highlightMenuIndex(mCallbacks.viewIndexInNavigationDrawer());
    }


    public boolean isDrawerOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation
     * drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                }

//                getActivity().supportInvalidateOptionsMenu();
            }
        };


        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        if (!mCallbacks.isHomeAsUp()) {

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

        } else {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.setToolbarNavigationClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    getActivity().onBackPressed();

                }
            });
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void selectItem(int position) {

        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    public void closeDrawerIfPossible() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (NavigationDrawerCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar.
        // See also
        // showGlobalContextActionBar, which controls the top-left area of the
        // action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
//			inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            //NavUtils.navigateUpFromSameTask(this);
//			Toast.makeText(getActivity(), "up/home", Toast.LENGTH_SHORT).show();
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to
     * show the global app 'context', rather than just what's in the current
     * screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setTitle(R.string.app_name); //TODO
        actionBar.setTitle("");
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }


    /**
     * Callbacks interface that all activities using this fragment must
     * implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);

        int viewIndexInNavigationDrawer();

        boolean isHomeAsUp();
    }


    public void lockDrawerClosed() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


}
