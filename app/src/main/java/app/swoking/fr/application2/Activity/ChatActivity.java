package app.swoking.fr.application2.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Date;

import app.swoking.fr.application2.R;
import app.swoking.fr.application2.User;
import app.swoking.fr.application2.Message;
import app.swoking.fr.application2.MessageSource;

public class ChatActivity extends ActionBarActivity implements View.OnClickListener,MessageSource.MessagesCallbacks {

    public static final String USER_EXTRA = "USER";
    public static final String TAG        = "ChatActivity";

    private ArrayList<Message>             mMessages;
    private MessagesAdapter                mAdapter;
    private User                           resiverUser;
    private User                           senderUser;
    private ListView                       mListView;
    private Date                           mLastMessageDate = new Date();
    private String                         mConvoId;
    private MessageSource.MessagesListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        Firebase.setAndroidContext(this);

        resiverUser = (User)getIntent().getSerializableExtra("user");
        senderUser  = (User)getIntent().getSerializableExtra("actualUser");

        Log.d(">>>>MMMMM", senderUser.getName());


        mListView = (ListView)findViewById(R.id.message_list);
        mMessages = new ArrayList<>();
        mAdapter  = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);

        setTitle(resiverUser.getUsername());
        if (getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);

        //String[] ids = {String.valueOf(senderUser.getId()), String.valueOf(resiverUser.getId())};
        //Arrays.sort(ids);
        if(senderUser.getId() < resiverUser.getId()){
            mConvoId = senderUser.getId() + "-" + resiverUser.getId();
        } else {
            mConvoId = resiverUser.getId() + "-" + senderUser.getId();
        }

        mListener = MessageSource.addMessagesListener(mConvoId, this);
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageSource.stop(mListener);
    }

    @Override
    public void onClick(View view) {
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String   newMessage     = newMessageView.getText().toString();

        newMessageView.setText("");
        Message msg = new Message();
        msg.setmDate(new Date());
        msg.setMtxt(newMessage);
        msg.setmSender(senderUser.getUsername());

        MessageSource.saveMessage(msg, mConvoId, senderUser.getUsername());
    }

    private class MessagesAdapter extends ArrayAdapter<Message> {
        MessagesAdapter(ArrayList<Message> messages){
            super(ChatActivity.this, R.layout.itemchatmsg, R.id.msg, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            if (!message.getMtxt().isEmpty()) {
                TextView nameView = (TextView) convertView.findViewById(R.id.msg);
                nameView.setText(message.getMtxt());
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nameView.getLayoutParams();
                int sdk = Build.VERSION.SDK_INT;
                if (message.getmSender().equals(senderUser.getUsername())) {
                    if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                        nameView.setBackground(getDrawable(R.drawable.bubble_right_green));
                    } else {
                        nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_right_green));
                    }
                    layoutParams.gravity = Gravity.RIGHT;
                } else {
                    if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                        nameView.setBackground(getDrawable(R.drawable.bubble_left_gray));
                    } else {
                        nameView.setBackgroundDrawable(getDrawable(R.drawable.bubble_left_gray));
                    }
                    layoutParams.gravity = Gravity.LEFT;
                }
                nameView.setLayoutParams(layoutParams);
            }
            return convertView;
        }
    }
}
