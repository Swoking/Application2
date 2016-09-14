package app.swoking.fr.application2.Listener;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import app.swoking.fr.application2.Fragment.Fragment_PersonalProfil;
import app.swoking.fr.application2.R;
import app.swoking.fr.application2.User;

public class ResultClickListener implements View.OnClickListener {

    private User user;
    private User actualUser;
    private FragmentManager manager;

    public ResultClickListener(User user, User actualUser, FragmentManager manager) {
        this.user = user;
        this.actualUser = actualUser;
        this.manager = manager;
    }

    @Override
    public void onClick(View view) {

        Fragment fragment = new Fragment_PersonalProfil();
        Bundle args1 = new Bundle();
        args1.putSerializable("user", this.user);
        args1.putSerializable("actualUser", this.actualUser);
        fragment.setArguments(args1);

        FragmentManager fragmentManager = manager;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_ceontent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
