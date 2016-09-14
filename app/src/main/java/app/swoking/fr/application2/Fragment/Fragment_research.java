package app.swoking.fr.application2.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import app.swoking.fr.application2.R;
import app.swoking.fr.application2.SlideShowView;
import app.swoking.fr.application2.User;
import app.swoking.fr.application2.Listener.ResultClickListener;

public class Fragment_research extends Fragment {

    private GridLayout gl;
    private User[]     userResult;
    private User       actualUser;

    private int i = 0;
    /*private SlideShowView[] slideShowView;
    private Fragment[] fragment;
    private Bundle[] args;
    private FragmentManager[] fragmentManager;
    private FragmentTransaction[] transaction;*/

    public Fragment_research() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_research, container, false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        gl = (GridLayout)rootView.findViewById(R.id.GridResult);
        gl.setColumnCount(3);

        actualUser = (User)getArguments().getSerializable("actualUser");
        userResult = (User[])getArguments().getSerializable("UserResult");

        /*slideShowView   = new SlideShowView[userResult.length];
        fragment        = new Fragment[userResult.length];
        args            = new Bundle[userResult.length];
        fragmentManager = new FragmentManager[userResult.length];
        transaction     = new FragmentTransaction[userResult.length];*/

        for(i = 0; i < userResult.length; i++) {
            SlideShowView slideShowView = new SlideShowView(rootView.getContext());
            gl.addView(slideShowView, displaymetrics.widthPixels / 3, (displaymetrics.widthPixels / 3)*9/16);
            slideShowView.start(userResult[i].getUrls());

            slideShowView.setOnClickListener(new ResultClickListener(userResult[i], actualUser, getFragmentManager()));
        }

        return rootView;
    }

}
