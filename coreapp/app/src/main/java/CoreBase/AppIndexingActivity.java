package CoreBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import CoreBase.CoreBaseDTO.AppIndexingDTO;
import Util.StringUtil;


public class AppIndexingActivity extends AppCompatActivity {

	private static final String TAG = AppIndexingActivity.class.getName();

	private static final Uri BASE_APP_URI = Uri.parse("android-app://package.name/");

	private GoogleApiClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.APP_INDEX_API).build();
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return super.onCreateView(parent, name, context, attrs);
		} else {
			return super.onCreateView(name, context, attrs);
		}

	}

	@Override
	protected void onStart() {

		super.onStart();

		if (mClient != null) {
			mClient.connect();
		}
	}

	@Override
	protected void onStop() {

		super.onStop();

		if (mClient != null) {
			mClient.disconnect();
		}
	}

	protected void addAppIndex(AppIndexingDTO indexingDTO) {


            if (mClient != null && indexingDTO != null) {

                final String indexDeepLink = indexingDTO.getDeepLink();
                final String indexTitle = indexingDTO.getTitle();
                final String indexWebUrl = indexingDTO.getWebURL();

                if (!StringUtil.isEmpty(indexTitle) && !StringUtil.isEmpty(indexDeepLink)
                        && !StringUtil.isEmpty(indexWebUrl)) {

                    final Uri APP_WEB_URL = Uri.parse(indexWebUrl);
                    final Uri APP_URI = BASE_APP_URI.buildUpon()
                            .appendPath(indexDeepLink.replace("http://", "zoom://") + "/").build();
                    Action viewAction = Action.newAction(Action.TYPE_VIEW, indexTitle, APP_WEB_URL, APP_URI);

                    Log.e(TAG, APP_URI.getPath());

                    PendingResult<Status> result = AppIndex.AppIndexApi.start(mClient, viewAction);

                    result.setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "App Indexing API: Recorded view successfully." + indexTitle);
                            } else {
                                Log.e(TAG, "App Indexing API: There was an error recording view." + status.toString());
                            }
                        }
                    });

                }
            }
        }

}
