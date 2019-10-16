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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Group;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.classes.User;
import com.spectrographix.monadie.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyGroupActivity extends AppCompatActivity {

    private Group group;

    private RecyclerView usersRecycler;
    private MyGroupActivityUsersListAdapter usersAdapter;
    ArrayList<User> usersList;

    FrameLayout addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroups_activity_my_group);
        recieveIntent();
        setCustomToolbar();
        initialise();
        start();
    }

    private void recieveIntent() {
        group = (Group)getIntent().getSerializableExtra("KEY_GROUP");
    }

    private void setCustomToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        ImageView backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        TextView title = (TextView) findViewById(R.id.commonToolbarTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyGroupActivity.super.onBackPressed();
            }
        });

        title.setText(group.getGroupName());
    }

    private void initialise() {
        usersRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);
        addMember = (FrameLayout) findViewById(R.id.id_addMember);

        usersList = new ArrayList<>();
    }

    private void start() {
        initiateMembersRecycler();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyGroupActivity.this,AddMembersActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void initiateMembersRecycler()
    {
        usersRecycler.setLayoutManager(new GridLayoutManager(this,1));

        populateUsersList();

        usersAdapter = new MyGroupActivityUsersListAdapter(this, usersList);
        usersAdapter.notifyDataSetChanged();

        usersRecycler.setAdapter(usersAdapter);
        usersRecycler.setItemAnimator(new DefaultItemAnimator());

        usersRecycler.setNestedScrollingEnabled(false);
    }

    private void populateUsersList()
    {
        usersList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_GROUPS_GET_GROUP_MEMBERS,
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
                            populateUsersStatus();
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
                params.put("group_id", group.getGroupId());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void populateUsersStatus()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_GROUPS_GET_GROUP_MEMBERS_WITH_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i =0;i<jsonArray.length();i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                String userStatus = obj.getString(usersList.get(i).getUserEmail());
                                usersList.get(i).setUserStatus(userStatus);
                            }
                            usersAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
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
                params.put("group_id", group.getGroupId());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
