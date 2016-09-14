package app.swoking.fr.application2.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.swoking.fr.application2.Adapter.SlidingMenuAdapter;
import app.swoking.fr.application2.Fragment.Fragment_PersonalProfil;
import app.swoking.fr.application2.Fragment.Fragment_option;
import app.swoking.fr.application2.Fragment.Fragment_research;
import app.swoking.fr.application2.Model.ItemSlideMenu;
import app.swoking.fr.application2.R;
import app.swoking.fr.application2.Request.AllUserRequest;
import app.swoking.fr.application2.User;

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu>   listSlideing;
    private SlidingMenuAdapter    adapter;
    private ListView              listViewSliding;
    private DrawerLayout          drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private User                  actualUser;
    private User[]                userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actualUser = (User)getIntent().getSerializableExtra("actualUser");

        // init component
        listViewSliding = (ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSlideing = new ArrayList<>();
        //Add itemchatmsg for sliding list
        listSlideing.add(new ItemSlideMenu(R.drawable.ic_action_search, "Rechercher"));
        listSlideing.add(new ItemSlideMenu(R.drawable.ic_action_profil, "Profil"));
        listSlideing.add(new ItemSlideMenu(R.drawable.ic_action_setting, "Option"));
        adapter = new SlidingMenuAdapter(this, listSlideing);
        listViewSliding.setAdapter(adapter);

        //Display icon to open / close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set title
        setTitle(listSlideing.get(0).getTitle());
        //itemchatmsg selected
        listViewSliding.setItemChecked(0, true);
        //Close menu
        drawerLayout.closeDrawer(listViewSliding);

        //Hande on itemchatmsg click

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Set title
                setTitle(listSlideing.get(i).getTitle());
                //itemchatmsg selected
                listViewSliding.setItemChecked(i, true);
                //replace fragment
                replaceFragment(i);
                //close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace fragment

    private  void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos){
            case 0 :

                Response.Listener<String> researchResponseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                int nbResult = jsonResponse.getInt("nbResult");
                                userList = new User[nbResult+1];
                                // Créer Tablau d'USER a afficher
                                for (int i =0; i <= nbResult; i++){
                                    JSONObject jsonResponseUser = jsonResponse.getJSONObject(String.valueOf(i));

                                    // GET URLS
                                    JSONArray urlsJSON = jsonResponseUser.getJSONArray("url");
                                    String[] arr = new String[urlsJSON.length()];
                                    for (int k = 0; k < urlsJSON.length(); k++) {
                                        arr[k] = urlsJSON.optString(k);
                                    }

                                    int      tempUserId       = jsonResponseUser.getInt("id");
                                    String   tempUserName     = jsonResponseUser.getString("name");
                                    String   tempUserUsername = jsonResponseUser.getString("username");
                                    int      tempUserAge      = jsonResponseUser.getInt("age");
                                    String   tempUserBio      = jsonResponseUser.getString("bio");
                                    String[] tempUserUrls     = arr;

                                    // Création USER
                                    userList[i] = new User(tempUserId, tempUserName, tempUserUsername, tempUserAge, tempUserBio, tempUserUrls);
                                }


                                //Envoie
                                Fragment fragment = null;
                                fragment = new Fragment_research();
                                Bundle args0 = new Bundle();
                                args0.putInt("WidowsWidth", getWindowManager().getDefaultDisplay().getWidth());
                                args0.putSerializable("UserResult", userList);
                                args0.putSerializable("actualUser", actualUser);
                                fragment.setArguments(args0);

                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.main_ceontent, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AllUserRequest userRequest = new AllUserRequest(researchResponseListener);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(userRequest);



                break;
            case 1 :
                fragment = new Fragment_PersonalProfil();
                Bundle args1 = new Bundle();
                args1.putSerializable("user", actualUser);
                args1.putSerializable("actualUser", actualUser);
                fragment.setArguments(args1);
                break;
            case 2 :
                fragment = new Fragment_option();
                setTitle("Option");
                break;
            default:
                fragment = new Fragment_research();
                break;
        }

        if(null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_ceontent, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

