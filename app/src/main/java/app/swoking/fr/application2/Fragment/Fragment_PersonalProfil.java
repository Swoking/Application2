package app.swoking.fr.application2.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import app.swoking.fr.application2.Listener.btnChatClickListener;
import app.swoking.fr.application2.R;
import app.swoking.fr.application2.User;
import app.swoking.fr.application2.SlideShowView;

public class Fragment_PersonalProfil extends Fragment {

    private User          user;
    private User          actualUser;
    private Button        btnChat;
    private TextView      txtBio;
    private SlideShowView slideShow;

    public Fragment_PersonalProfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_profil, container, false);

        final AdView adView = (AdView) rootView.findViewById(R.id.banner);
        adView.loadAd(new AdRequest.Builder().build());

        user       = (User)getArguments().getSerializable("user");
        actualUser = (User)getArguments().getSerializable("actualUser");
        slideShow  = (SlideShowView) rootView.findViewById(R.id.slideShow);
        txtBio     = (TextView) rootView.findViewById(R.id.txtBio);
        btnChat    = (Button) rootView.findViewById(R.id.btnChat);

        if (actualUser.getId() == user.getId()){
            btnChat.setVisibility(View.INVISIBLE);
        }

        btnChat.setOnClickListener(new btnChatClickListener(user, actualUser, getActivity().getApplicationContext()));

        getActivity().setTitle(user.getUsername() + " Profil");
        slideShow.start(user.getUrls());
        txtBio.setText(user.getBio());

        return rootView;
    }

}
