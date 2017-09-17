package login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import Util.ViewUtil;
import acosta.co.nz.coreapp.R;
import corebase.BaseNavigationDrawerFragment;

/**
 * Created by alancosta on 9/7/17.
 */


public class LoginFragment extends BaseNavigationDrawerFragment {


    private LoginSocialManagerActivity socialManagerActivity;
    private LinearLayout facebookSigInBtn;
    private LinearLayout googleSigInBtn;
    private ImageView loginBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.login_page_main, container, false);
        ViewUtil.setStatusBarColor(mParentActivity, ContextCompat.getColor(mParentActivity, R.color.colorAccent));


        setUpView(rootView);
        onClickGoogleSignIn();
        onClickFacebookSignIn(mParentActivity);
        onClickBack();

        return rootView;
    }


    private void setUpView(View rootView) {

        socialManagerActivity = (LoginSocialManagerActivity) getActivity();
        facebookSigInBtn = (LinearLayout) rootView.findViewById(R.id.login_page_sigin_facebook);
        googleSigInBtn = (LinearLayout) rootView.findViewById(R.id.login_page_sigin_google);
        loginBack = (ImageView) rootView.findViewById(R.id.login_page_back);
    }


    private void onClickGoogleSignIn() {

        if (googleSigInBtn == null) {
            return;
        }

        googleSigInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                socialManagerActivity.doGoogleAccountLogin();

            }
        });

    }

    private void onClickFacebookSignIn(final Activity activity) {

        if (facebookSigInBtn == null) {
            return;
        }

        facebookSigInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                socialManagerActivity.doFacebookAccountLogin(activity);

            }
        });

    }

    private void onClickBack() {

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mParentActivity.finish();
            }
        });
    }


}
