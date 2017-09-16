package corebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alancosta on 9/7/17.
 */

public class BaseNavigationDrawerFragment extends Fragment {


    public BaseNavigationDrawerActivity mParentActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mParentActivity = (BaseNavigationDrawerActivity) getActivity();

        return null;

    }


}
