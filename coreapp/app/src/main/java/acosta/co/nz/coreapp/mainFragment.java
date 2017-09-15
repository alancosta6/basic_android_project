package acosta.co.nz.coreapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import corebase.BaseFragment;

/**
 * Created by alancosta on 5/21/16.
 */
public class mainFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View root  = inflater.inflate(R.layout.content_main,container,false);

        return root;
    }
}
