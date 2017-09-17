package login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;

import login.LoginDTO.SocialLoginDTO;

/**
 * Created by alancosta on 9/7/17.
 */

public class LoginActivity extends LoginSocialManagerActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment superMainFragment() {
        return new LoginFragment();
    }

    @Override
    protected boolean superHideToolbar() {
        return true;
    }

    @Override
    protected boolean superLockDrawer() {
        return true;
    }

    @Override
    protected void superFabControl(FloatingActionButton mFab) {

    }

    @Override
    protected boolean superEnableSwipeRefresh() {
        return false;
    }

    @Override
    protected void superOnRefreshCalled() {

    }

    @Override
    protected String superScreenName() {
        return "LoginPage";
    }


    @Override
    protected void onSocialLoginSuccess(SocialLoginDTO loginDTO) {
        Log.d(TAG, loginDTO.toString());


    }

    @Override
    protected void onSaveInstanceStateOverride(Bundle outState) {

    }
}
