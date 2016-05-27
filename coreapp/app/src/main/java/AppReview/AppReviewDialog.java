package AppReview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 8/24/15.
 */
public class AppReviewDialog extends DialogFragment  {

	private static final String TAG = "AppReviewDialog";

	private static View ratingLike;
	private static View ratingDislike;
	private static View ratingNotSure;
	private static View ratingDoNotDisturb;

	private static Dialog mDialog;
	private static Context mContext;
	private static FragmentManager mFragmentManager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.rating_panel, container, false);

		mDialog = getDialog();
		mContext = getActivity();

		setupDialogProperties(mDialog);

		// Dialog views for action;
		ratingLike = (View) rootView.findViewById(R.id.zoom_rating_like);
		ratingDislike = (View) rootView.findViewById(R.id.zoom_rating_dislike);
		ratingNotSure = (View) rootView.findViewById(R.id.zoom_rating_not_sure);
		ratingDoNotDisturb = (View) rootView.findViewById(R.id.zoom_rating_do_not_disturb);

		mFragmentManager = getFragmentManager();
		

		

		onTextClick(ratingLike, new Action() {
			@Override
			public void onClickAction(Context mContext) {
				new AppReviewDialogLike().show(mFragmentManager, TAG);
				

			}
		});
		 

		onTextClick(ratingDislike, new Action() {
			@Override
			public void onClickAction(Context mContext) {

				AppReviewDialogDislike dialogUnlike = new AppReviewDialogDislike();
				dialogUnlike.setStyle(STYLE_NO_FRAME, android.R.style.Theme);
				dialogUnlike.show(mFragmentManager, TAG);
				


			}
		});

		onTextClick(ratingNotSure, new Action() {
			@Override
			public void onClickAction(Context mContext) {
				
				// Business rule if the user choses this option show the dialog
				// in 30 days from current timestamp
				AppReviewManager.getInstance(mContext).onDontKnowYet(30);


			}
		});

		onTextClick(ratingDoNotDisturb, new Action() {
			@Override
			public void onClickAction(Context mContext) {
				AppReviewManager.getInstance(mContext).onDontAskAgain();
				

			}
		});

		return rootView;

	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		
		if(mContext != null)
		{
			Log.d(TAG, "onCancel");
			// Business rule if the user choses this option show the dialog
			// in 30 days from current timestamp
			
			AppReviewManager.getInstance(mContext).onDontKnowYet(30);

		}
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
