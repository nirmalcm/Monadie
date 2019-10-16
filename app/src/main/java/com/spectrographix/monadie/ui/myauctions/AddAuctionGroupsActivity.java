package com.spectrographix.monadie.ui.myauctions;

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
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Group;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.classes.User;
import com.spectrographix.monadie.ui.mygroups.MyGroupsActivityGroupListAdapter;
import com.spectrographix.monadie.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AddAuctionGroupsActivity extends AppCompatActivity {

    private RecyclerView groupsRecycler;
    private AddAuctionGroupsActivityGroupsListAdapter groupsAdapter;
    ArrayList<Group> groupsList;

    FrameLayout done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myauctions_activity_add_auction_groups);
        setCustomToolbar();
        initialise();
        start();
    }

    private void setCustomToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        ImageView backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        TextView title = (TextView) findViewById(R.id.commonToolbarTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAuctionGroupsActivity.super.onBackPressed();
            }
        });

        title.setText("Add Auction Groups");
    }

    private void initialise() {
        groupsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);
        done = (FrameLayout) findViewById(R.id.id_done);

        groupsList = new ArrayList<>();
    }

    private void start() {
        initiateGroupsRecycler();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> groups = new ArrayList<>();

                for (Group group : groupsList) {
                    if (group.isSelected()) {
                        groups.add(new String(group.getGroupId()));
                    }
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("groups",groups.toString());
                returnIntent.putExtra("groupsCount",String.valueOf(groups.size()));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void initiateGroupsRecycler()
    {
        groupsRecycler.setLayoutManager(new GridLayoutManager(this,1));

        populateGroupsList();
        groupsAdapter = new AddAuctionGroupsActivityGroupsListAdapter(this, groupsList);
        groupsAdapter.notifyDataSetChanged();

        groupsRecycler.setAdapter(groupsAdapter);
        groupsRecycler.setItemAnimator(new DefaultItemAnimator());

        groupsRecycler.setNestedScrollingEnabled(false);
    }

    private void populateGroupsList()
    {
        groupsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_GROUPS_GET_ALL_GROUPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i =0;i<jsonArray.length();i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                JSONObject idObject = obj.getJSONObject("_id");

                                String groupId = idObject.getString("$oid");

                                String groupName = obj.getString("group_name");
                                String groupAdmin = obj.getString("group_admin");
                                String groupMembers = obj.getString("group_members");

                                Group group = new Group(groupId,groupName,groupAdmin,groupMembers);
                                groupsList.add(group);
                            }
                            groupsAdapter.notifyDataSetChanged();
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
