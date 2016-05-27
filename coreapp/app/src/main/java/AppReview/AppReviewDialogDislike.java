package AppReview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import acosta.co.nz.coreapp.R;

/**
 * Created by alancosta on 8/24/15.
 */
@SuppressLint("NewApi")
public class AppReviewDialogDislike extends DialogFragment {

    private static final String TAG = "AppReviewDialogDislike";

    private static final int MIN_ACCEPTABLE_TEXT_SIZE = 5;
    private static final int BLINK_ANIMATION_DURATION = 1500;

    private static Dialog mDialog;

    private EditText userInputText;
    private TextView userSendReview;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.app_review_disliked, container, false);

        mDialog = getDialog();
        setupDialogProperties(mDialog);

        rootView.findViewById(R.id.zoom_rating_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppReviewManager.getInstance(view.getContext()).onDontAskAgain();
                mDialog.dismiss();
            }
        });

        userSendReview = (TextView) rootView.findViewById(R.id.zoom_rating_send_review);
        userSendReview.setVisibility(View.INVISIBLE);
        userSendReview.setClickable(false);
        userSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserReview(userInputText.getText().toString(), v.getContext());
            }
        });

        rootView.findViewById(R.id.zoom_rating_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppReviewManager.getInstance(view.getContext()).onDontAskAgain();
                new AppReviewDialogDismiss().styleThanks().show(getFragmentManager(), TAG);
                mDialog.dismiss();
            }
        });

        userInputText = (EditText) rootView.findViewById(R.id.zoom_rating_review_input);
        userInputText.setImeOptions(EditorInfo.IME_ACTION_SEND);
        userInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String userReview = userInputText.getText().toString();
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {

                    return sendUserReview(userReview, v.getContext());
                }

                return false;
            }
        });

        userInputText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= MIN_ACCEPTABLE_TEXT_SIZE) {
                    userSendReview.setVisibility(View.VISIBLE);
                    userSendReview.setClickable(true);
                } else {
                    userSendReview.setVisibility(View.INVISIBLE);
                    userSendReview.setClickable(false);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        super.onViewCreated(view, savedInstanceState);
    }

    private void setupDialogProperties(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    private boolean sendUserReview(String review, Context mContext) {

        if (review.length() <= MIN_ACCEPTABLE_TEXT_SIZE) {

            if (userInputText == null) {
                userInputText = (EditText) getDialog().findViewById(R.id.zoom_rating_review_input);
            }
            customBlickAnimation(userInputText);

            return true;

        } else {


            //TODO: SEND USER REVIEW TO SERVER

            AppReviewManager.getInstance(mContext).onDontAskAgain();
            new AppReviewDialogDismiss().styleWeWillWork().show(getFragmentManager(), TAG);
            mDialog.dismiss();
        }

        return false;

    }

    private void customBlickAnimation(final EditText userInputText) {

        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(BLINK_ANIMATION_DURATION);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.REVERSE);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                userInputText.setHint(getResources().getString(R.string.zoom_rating_panel_dislike_tell_us_more));
                userInputText.setHintTextColor(getResources().getColor(R.color.zoom_rating_blink_animation));
                setBackground(userInputText, R.drawable.app_review_texteditor_error_border);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                userInputText.setHint("");
                userInputText.setHintTextColor(getResources().getColor(R.color.zoom_rating_text));
                setBackground(userInputText, R.drawable.app_review_texteditor_border);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        userInputText.startAnimation(animation);

    }

    private void setBackground(View view, @DrawableRes int drawable) {

        view.setBackground(ContextCompat.getDrawable(getContext(),drawable));


//        final int sdk = android.os.Build.VERSION.SDK_INT;
//        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            view.setBackgroundDrawable(getResources().getDrawable(drawable));
//        } else {
//            view.setBackground(getResources().getDrawable(drawable));
//        }
    }


}
