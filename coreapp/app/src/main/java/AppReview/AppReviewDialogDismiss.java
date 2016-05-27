package AppReview;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 8/24/15.
 */
public class AppReviewDialogDismiss extends DialogFragment {

	private static Dialog mDialog;

	private View mRootView;
	private int dialogType;

	private enum DialogType {
		DIALOG_TYPE_THANKS, DIALOG_TYPE_WE_WILL_WORK
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.rating_panel_dismiss, container, false);
		mRootView = rootView;

		mDialog = getDialog();
		setupDialogProperties(mDialog);

		if (dialogType == DialogType.DIALOG_TYPE_THANKS.ordinal()) {
			setViewContentTexts(getString(R.string.zoom_rating_feedback_ok_title),
					getString(R.string.zoom_rating_feedback_ok_msg));
		} else if (dialogType == DialogType.DIALOG_TYPE_WE_WILL_WORK.ordinal()) {
			setViewContentTexts(getString(R.string.zoom_rating_feedback_working_title),
					getString(R.string.zoom_rating_feedback_working_msg));
		}

		rootView.findViewById(R.id.zoom_rating_close).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				mDialog.dismiss();
			}
		});

		return rootView;
	}

	public AppReviewDialogDismiss styleThanks() {

		dialogType = DialogType.DIALOG_TYPE_THANKS.ordinal();
		return this;
	}

	public AppReviewDialogDismiss styleWeWillWork() {

		dialogType = DialogType.DIALOG_TYPE_WE_WILL_WORK.ordinal();
		return this;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		super.show(manager, tag);
	}

	private void setViewContentTexts(String title, String msg) {

		if (mRootView != null) {
			TextView ratingFeedbackTitle = (TextView) mRootView.findViewById(R.id.zoom_rating_feedback_title);
			TextView ratingFeedbackMsg = (TextView) mRootView.findViewById(R.id.zoom_rating_feedback_msg);

			if (ratingFeedbackTitle != null) {

				ratingFeedbackTitle.setText(title);
			}
			if (ratingFeedbackMsg != null) {

				ratingFeedbackMsg.setText(msg);
			}
		} else {
			if (mDialog != null) {
				mDialog.dismiss();
			}
		}

	}

	private void setupDialogProperties(Dialog dialog) {
		dialog.setCanceledOnTouchOutside(false);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}
