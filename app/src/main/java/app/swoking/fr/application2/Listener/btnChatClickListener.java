package app.swoking.fr.application2.Listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import app.swoking.fr.application2.User;
import app.swoking.fr.application2.Activity.ChatActivity;

public class btnChatClickListener implements View.OnClickListener {

    private User user;
    private User actualUser;
    private Context context;

    public btnChatClickListener(User user, User actualUser, Context manager) {
        this.user = user;
        this.actualUser = actualUser;
        this.context = manager;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(context, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("actualUser", actualUser);
        intent.putExtra("user", user);
        context.startActivity(intent);

    }
}
