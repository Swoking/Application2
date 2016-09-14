package app.swoking.fr.application2.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import app.swoking.fr.application2.R;
import app.swoking.fr.application2.Request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAge;
    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private Button   bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");

        etAge      = (EditText) findViewById(R.id.register_etAge);
        etName     = (EditText) findViewById(R.id.register_etName);
        etUsername = (EditText) findViewById(R.id.register_etUsername);
        etPassword = (EditText) findViewById(R.id.register_etPassword);
        bRegister  = (Button)   findViewById(R.id.register_bRegister);

        bRegister.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch(v.getId()) {
                case R.id.register_bRegister:
                    final String name     = etName.getText().toString();
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();
                    final int    age      = Integer.valueOf(etAge.getText().toString());

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                Log.e("RegisterResponseERROR", "Register response are not JSON format.");
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                    break;
            }
        }
    };
}
