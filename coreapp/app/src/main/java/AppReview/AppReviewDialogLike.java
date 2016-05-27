package AppReview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 8/24/15.
 */
public class AppReviewDialogLike extends DialogFragment {

	private static final String TAG = "AppReviewDialogLike";

	private static TextView ratingOpenStore;
	private static TextView ratingOpenStoreName;
	private static View ratingDismiss;

	private static Dialog mDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.rating_panel_liked, container, false);

		mDialog = getDialog();
		setupDialogProperties(mDialog);

		ratingDismiss = (View) rootView.findViewById(R.id.zoom_rating_dismiss);
		ratingOpenStore = (TextView) rootView.findViewById(R.id.zoom_rating_open_store);
		ratingOpenStoreName = (TextView) rootView.findViewById(R.id.zoom_rating_store_name);

		onTextClick(ratingDismiss, new Action() {
			@Override
			public void onClickAction(Context mContext) {

				
				AppReviewManager.getInstance(mContext).onDontAskAgain();
				new AppReviewDialogDismiss().styleThanks().show(getFragmentManager(), TAG);
				mDialog.dismiss();
			}
		});

		onTextClick(ratingOpenStore, new Action() {
			@Override
			public void onClickAction(Context mContext) {


				AppReviewManager.getInstance(mContext).onDontAskAgain();
				AppReviewManager.getInstance(mContext).onOpenPlayStore();
				mDialog.dismiss();
			}
		});
		
		onTextClick(ratingOpenStoreName, new Action() {
			@Override
			public void onClickAction(Context mContext) {

				AppReviewManager.getInstance(mContext).onDontAskAgain();
				AppReviewManager.getInstance(mContext).onOpenPlayStore();
				mDialog.dismiss();
			}
		});

		return rootView;
	}

	private void setupDialogProperties(Dialog dialog) {
		dialog.setCanceledOnTouchOutside(false);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	// Use a interface method to implement click to ensure easy implementation
	// of visual effects to view
	private void onTextClick(View view, final Action onClick) {
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClick.onClickAction(v.getContext());

				if (mDialog != null) {
					mDialog.dismiss();
				}

			}
		});

	}

	private interface Action {
		void onClickAction(Context mContext);
	}
}
