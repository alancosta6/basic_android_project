package Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class TypeFaceCache {
	 private static Map<String, Typeface> mTypefaces = new HashMap<String, Typeface>();
	 
	 public static Typeface getTypeFace(Context context, String typefaceAssetPath) {
		 if (typefaceAssetPath != null) {
             Typeface typeface = null;

             if (mTypefaces.containsKey(typefaceAssetPath)) {
                 typeface = mTypefaces.get(typefaceAssetPath);
             } else {
                 AssetManager assets = context.getAssets();
                 typeface = Typeface.createFromAsset(assets, typefaceAssetPath);
                 mTypefaces.put(typefaceAssetPath, typeface);
             }

             return typeface;
         }
		 return null;
	 }
}
