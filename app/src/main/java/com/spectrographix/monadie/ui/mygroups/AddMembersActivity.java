package com.spectrographix.monadie.ui.mygroups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Auction;
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Product;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.classes.User;
import com.spectrographix.monadie.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddMembersActivity extends AppCompatActivity {

    ImageView backButton;

    private RecyclerView usersRecyclerView;
    private AddMembersActivityUsersListAdapter usersAdapter;
    ArrayList<User> usersList;

    FrameLayout done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroups_activity_add_members);
        initialise();
        start();
    }

    private void initialise() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);

        usersRecyclerView = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);

        usersList = new ArrayList<>();

        done = (FrameLayout) findViewById(R.id.id_done);
    }

    private void start() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMembersActivity.super.onBackPressed();
            }
        });

        initiateLiveAuctionsRecycler();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> members = new ArrayList<>();

                for (User user : usersList) {
                    if (user.isSelected()) {
                        members.add(new String(user.getUserId()));
                    }
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("members",members.toString());
                returnIntent.putExtra("membersCount",String.valueOf(members.size()));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void initiateLiveAuctionsRecycler()
    {
        usersRecyclerView.setLayoutManager(new GridLayoutManager(this,1));

        populateUsersList();
        usersAdapter = new AddMembersActivityUsersListAdapter(this,usersList);
        usersAdapter.notifyDataSetChanged();

        usersRecyclerView.setAdapter(usersAdapter);
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());

        usersRecyclerView.setNestedScrollingEnabled(false);
    }

    private void populateUsersList()
    {
        usersList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_USERS_GET_ALL_USERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i =0;i<jsonArray.length();i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                JSONObject idObject = obj.getJSONObject("_id");

                                String userId = idObject.getString("$oid");

                                String userFirstName = obj.getString("first_name");
                                String userLastName = obj.getString("last_name");
                                String userEmail = obj.getString("email");
                                String userPassword = obj.getString("password");

                                User user = new User(userId,userFirstName,userLastName,userEmail,userPassword);
                                usersList.add(user);
                            }
                            usersAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Operation Failed !", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}