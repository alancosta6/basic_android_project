package login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONObject;

import java.util.Arrays;

import Util.StringUtil;
import acosta.co.nz.coreapp.R;
import corebase.BaseNavigationDrawerActivity;
import login.LoginDTO.SocialLoginDTO;


public abstract class LoginSocialManagerActivity extends BaseNavigationDrawerActivity {

    private static final String TAG = LoginSocialManagerActivity.class.getSimpleName();


    private static final String SERVER_CLIENT_ID = "184282505876.apps.googleusercontent.com";
    private static final String FACEBOOK_APP_ID = "1518767344839301";


    private final int REQUEST_CODE_PICK_ACCOUNT = 1000;

    public CallbackManager facebookCallbackManager;
    public GoogleApiClient mGoogleApiClient;

    protected abstract void onSocialLoginSuccess(SocialLoginDTO loginDTO);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookInitialize();
        googleInitialize();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (facebookCallbackManager != null) {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == REQUEST_CODE_PICK_ACCOUNT && resultCode != Activity.RESULT_CANCELED) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                if (acct != null) {
                    String idToken = acct.getIdToken();
                    String email = acct.getEmail();
                    if (!StringUtil.isEmpty(idToken) && !StringUtil.isEmpty(email)) {

                        Uri picUri = acct.getPhotoUrl();
                        String googlePictureURL = picUri != null ? picUri.toString() : StringUtil.EMPTY_STRING;

                        SocialLoginDTO socialLoginDTO = new SocialLoginDTO();
                        socialLoginDTO.setSocialNetworkUserEmail(email);
                        socialLoginDTO.setSocialNetworkUserID(acct.getId());
                        socialLoginDTO.setSocialNetworkUserName(acct.getDisplayName());
                        socialLoginDTO.setSocialNetworkPictureUrl(googlePictureURL);
                        socialLoginDTO.setSocialNetworkLoginToken(idToken);
                        socialLoginDTO.setSocialLoginSource(SocialLoginDTO.Source.GOOGLE);
                        onSocialLoginSuccess(socialLoginDTO);

                    } else {
                        showSnackBar(getString(R.string.google_login_failure));
                    }

                } else {
                    showSnackBar(getString(R.string.google_login_failure));
                }

            } else {
                showSnackBar(getString(R.string.google_login_failure));
            }
        }

    }

    /*********
     * START FACEBOOK LOGIN WITH FACEBOOK SDK LOGIN MANAGER
     */

    private void facebookInitialize() {


        if (StringUtil.isEmpty(FACEBOOK_APP_ID)) {
            Log.e(TAG, "You must add a Facebook appID to user facebook login");
            return;
        }
        FacebookSdk.setApplicationId(FACEBOOK_APP_ID);
        FacebookSdk.sdkInitialize(this);

    }

    public void doFacebookAccountLogin(final Activity parent) {

        if (StringUtil.isEmpty(FACEBOOK_APP_ID)) {
            Log.e(TAG, "You must add a Facebook appID to user facebook login");
            return;
        }

        facebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(parent, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                Log.d(TAG, loginResult.toString());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                try {
                                    Log.d(TAG, jsonObject.toString());

                                    String userMainEmail = jsonObject.optString("email");

                                    if (!StringUtil.isEmpty(userMainEmail)) {

                                        String facebookUID = jsonObject.optString("id");
                                        String name = jsonObject.optString("name");
                                        String picture = jsonObject.optJSONObject("picture").getJSONObject("data").optString("url");

                                        SocialLoginDTO socialLoginDTO = new SocialLoginDTO();
                                        socialLoginDTO.setSocialNetworkUserEmail(userMainEmail);
                                        socialLoginDTO.setSocialNetworkUserID(facebookUID);
                                        socialLoginDTO.setSocialNetworkUserName(name);
                                        socialLoginDTO.setSocialNetworkPictureUrl(picture);
                                        socialLoginDTO.setSocialNetworkLoginToken(loginResult.getAccessToken().getToken());
                                        socialLoginDTO.setSocialLoginSource(SocialLoginDTO.Source.FACEBOOK);

                                        onSocialLoginSuccess(socialLoginDTO);

                                    } else {
                                        Log.e(TAG, "USER HAS NO EMAIL");
                                        showSnackBar(getString(R.string.facebook_login_failure));
                                    }


                                } catch (Exception ex) {
                                    Log.e(TAG, ex.getMessage() + "-");
                                    showSnackBar(getString(R.string.facebook_login_failure));
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,link,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "Facebook cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "Facebook error " + exception.getMessage());
                showSnackBar(getString(R.string.facebook_login_failure));
            }
        });

    }


    /*********
     * START GOOGLE LOGIN WITH DEVICE ACCOUNT MANAGER
     */

    private void googleInitialize() {


        if (StringUtil.isEmpty(SERVER_CLIENT_ID)) {
            Log.e(TAG, "You must add a Google server client ID like: 18428231205876.apps.googleusercontent.com");
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.EMAIL))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestIdToken(SERVER_CLIENT_ID)
                .requestServerAuthCode(SERVER_CLIENT_ID)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    public void doGoogleAccountLogin() {

        if (StringUtil.isEmpty(SERVER_CLIENT_ID)) {
            Log.e(TAG, "You must add a Google server client ID like: 18428231205876.apps.googleusercontent.com");
            return;
        }

        mGoogleApiClient.connect();

        showLoadingFullScreen();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {

                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, REQUEST_CODE_PICK_ACCOUNT);
                    hideLoadingFullScreen();
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Google API Client Connection Suspended");
                showSnackBar(getString(R.string.google_login_failure));
                hideLoadingFullScreen();
            }

        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        onSaveInstanceStateOverride(outState);
        super.onSaveInstanceState(outState);
    }

    protected abstract void onSaveInstanceStateOverride(Bundle outState);


}
