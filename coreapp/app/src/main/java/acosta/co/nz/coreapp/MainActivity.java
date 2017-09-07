package acosta.co.nz.coreapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import AppReview.AppReviewManager;
import CoreBase.BaseNavigationDrawerActivity;

public class MainActivity extends BaseNavigationDrawerActivity {

    @Override
    protected Fragment superMainFragment() {
        return new mainFragment();
    }

    @Override
    protected boolean superHideToolbar() {
        return false;
    }

    @Override
    protected boolean superLockDrawer() {
        return false;
    }

    @Override
    protected void superFabControl(FloatingActionButton mFab) {

    }

    @Override
    protected boolean superEnableSwipeRefresh() {
        return true;
    }

    @Override
    protected void superOnRefreshCalled() {


        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                dismissLoadingIndicator();
            }
        }, 5000);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppReviewManager.getInstance(this).start(getSupportFragmentManager());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
