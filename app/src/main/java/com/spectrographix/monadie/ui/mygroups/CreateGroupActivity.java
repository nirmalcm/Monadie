package com.spectrographix.monadie.ui.mygroups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateGroupActivity extends AppCompatActivity {

    SessionManager sessionManager;

    TextInputLayout groupNamelayout;
    EditText groupName;
    TextView numberOfMembers;
    int mMembersCount = 0;
    CardView addMembers;

    String members ="[]";
    String membersCount = "0";
    FrameLayout done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroups_activity_add_group);
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
                CreateGroupActivity.super.onBackPressed();
            }
        });

        title.setText("Create Group");
    }

    private void initialise() {
        sessionManager = new SessionManager(this);

        groupNamelayout = (TextInputLayout) findViewById(R.id.id_groupNameLayout);
        groupName = (EditText) findViewById(R.id.id_groupName);

        numberOfMembers = (TextView) findViewById(R.id.id_numberOfMembers);
        addMembers = (CardView) findViewById(R.id.id_addMembers);

        done = (FrameLayout) findViewById(R.id.id_done);
    }

    private void start() {
        groupName.addTextChangedListener(new MyTextWatcher(groupName));

        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateGroupActivity.this,AddMembersActivity.class);
                startActivityForResult(intent,1);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                {
                    createGroupUsingVolley();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                members=data.getStringExtra("members");
                membersCount = data.getStringExtra("membersCount");

                numberOfMembers.setText(membersCount + " members added !");

                mMembersCount = Integer.valueOf(membersCount);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void createGroupUsingVolley()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_GROUPS_CREATE_GROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (obj.getString("status").equals("success")) {
                                Toast.makeText(getApplicationContext(), "Group created !", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(CreateGroupActivity.this,MyGroupsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Operation Failed !", Toast.LENGTH_SHORT).show();
                            }

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
                params.put("group_name", groupName.getText().toString());
                params.put("group_admin",sessionManager.getUserEmail());
                params.put("group_members", members);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private boolean validate()
    {
        if (!validateGroupName()) {
            return false;
        }
        if(mMembersCount < 2)
        {
            Toast.makeText(this,"Please add atleast 2 group members !",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateGroupName() {
        if (groupName.getText().toString().trim().isEmpty()) {
            groupNamelayout.setError("Please enter a group name");
            requestFocus(groupName);
            return false;
        } else {
            groupNamelayout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.id_groupName:
                    validateGroupName();
                    break;
            }
        }
    }
}