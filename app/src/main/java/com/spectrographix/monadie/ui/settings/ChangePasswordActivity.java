package com.spectrographix.monadie.ui.settings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.home.LoginActivity;
import com.spectrographix.monadie.ui.home.MainActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    FrameLayout parentLayout;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;

    ImageView backButton;
    Button submitNewPassword;
    SessionManager sessionManager;
    EditText currentPasswordField,newPasswordField,repeatPasswordField;
    String oldPassword,newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_change_password);
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
                ChangePasswordActivity.super.onBackPressed();
            }
        });

        title.setText("Change Password");
    }

    public void initialise()
    {
        utility = new Utility();
        sessionManager = new SessionManager(this);

        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);
        submitNewPassword = (Button) findViewById(R.id.submitNewPassword);
        currentPasswordField = (EditText) findViewById(R.id.currentPasswordField);
        newPasswordField = (EditText) findViewById(R.id.newPasswordField);
        repeatPasswordField = (EditText) findViewById(R.id.repeatPasswordField);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

    }

    private void start()
    {
        initiateActionListeners();
    }

    private void initiateActionListeners() {

            submitNewPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate()) {
                        oldPassword = currentPasswordField.getText().toString();
                        newPassword = newPasswordField.getText().toString();


                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_CHANGE_PASSWORD,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            //converting response to json object
                                            JSONObject obj = new JSONObject(response);
                                            //if no error in response
                                            if (obj.getString("status").equals("success")) {
                                                Toast.makeText(getApplicationContext(),"Password changed successfully", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(ChangePasswordActivity.this,SettingsActivity.class));
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("email", sessionManager.getUserEmail());
                                params.put("current_password", oldPassword);
                                params.put("new_password", newPassword);
                                return params;
                            }
                        };

                        AppController.getInstance().addToRequestQueue(stringRequest);
                    }
                }
            });
        }

    private boolean validate()
    {
        if (currentPasswordField.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please provide the current password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPasswordField.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please provide the new password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (repeatPasswordField.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please re-enter the password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(currentPasswordField.getText().toString().length()<8)
        {
            Toast.makeText(this,"Enter atleast 8 characters for current password !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(newPasswordField.getText().toString().length()<8)
        {
            Toast.makeText(this,"Enter atleast 8 characters for new password !",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}