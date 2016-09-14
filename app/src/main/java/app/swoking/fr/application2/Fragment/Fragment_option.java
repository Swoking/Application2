package app.swoking.fr.application2.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import app.swoking.fr.application2.R;

public class Fragment_option extends Fragment{

    public Fragment_option() {
    }

    AdView[] adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_option, container, false);

        adView = new AdView[10];
        adView[0] = (AdView) rootView.findViewById(R.id.banner1);
        adView[1] = (AdView) rootView.findViewById(R.id.banner2);
        adView[2] = (AdView) rootView.findViewById(R.id.banner3);
        adView[3] = (AdView) rootView.findViewById(R.id.banner4);
        adView[4] = (AdView) rootView.findViewById(R.id.banner5);
        adView[5] = (AdView) rootView.findViewById(R.id.banner6);
        adView[6] = (AdView) rootView.findViewById(R.id.banner7);
        adView[7] = (AdView) rootView.findViewById(R.id.banner8);
        adView[8] = (AdView) rootView.findViewById(R.id.banner9);
        adView[9] = (AdView) rootView.findViewById(R.id.banner10);

        for (int i = 0; i < adView.length; i++){
            adView[i].loadAd(new AdRequest.Builder().build());
        }

        return rootView;
    }

}
