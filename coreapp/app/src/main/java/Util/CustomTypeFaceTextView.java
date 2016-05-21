package Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zoom.zoomandroid.R;
import com.zoom.zoomandroid.R.styleable;
import com.zoom.zoomandroid.util.TypeFaceCache;

@SuppressLint("NewApi")
public class CustomTypeFaceTextView extends TextView {

	public CustomTypeFaceTextView(Context context) {
		this(context, null);
	}
	public CustomTypeFaceTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public CustomTypeFaceTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

        
		final TypedArray array = context.obtainStyledAttributes(attrs, styleable.CustomTypeFaceTextView);
        if (array != null) {
        	
        	
        	 if (array != null) {
             	
             	final int arrayIndex = array.getIndexCount();
             	for (int i = 0; i < arrayIndex ; ++i)
             	{
             	    int attr = array.getIndex(i);
             	    switch (attr)
             	    {
             	        case R.styleable.CustomTypeFaceTextView_typeface:
             	        	
             	        	final String typefaceAssetPath = array.getString(R.styleable.CustomTypeFaceTextView_typeface);
             	        	if (isInEditMode()) {
                         		if (typefaceAssetPath.toLowerCase().contains("bold")) {
                         			setTypeface(null, Typeface.BOLD);
                         		}
                         		break;
             	        	}
                            Typeface typeface = TypeFaceCache.getTypeFace(getContext(), typefaceAssetPath);
                            setTypeface(typeface);
                         
             	            break;
             	        case R.styleable.CustomTypeFaceTextView_setLetterSpacing:
             	            float letterSpacing = array.getFloat(attr, 0);
             	            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
             	    			setLetterSpacing(letterSpacing);
             	    		}
             	            break;
             
             	    }
             	}

                 array.recycle();
             }
        	
        }
	}

}
