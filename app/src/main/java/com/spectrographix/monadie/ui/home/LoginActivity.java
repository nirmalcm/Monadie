package com.spectrographix.monadie.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.spectrographix.monadie.ui.mygroups.MyGroupActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user productName on 2/3/2018.
 */

public class LoginActivity extends AppCompatActivity {

    ImageView backButton;
    TextView title;

    SessionManager sessionManager;
    Utility utility;

    EditText email;
    EditText password;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_login);
        initialise();
        start();
    }

    public void initialise()
    {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        sessionManager = new SessionManager(getApplicationContext());
        utility = new Utility();

        email =(EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);

        login =(Button) findViewById(R.id.login);
    }

    private void start()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.super.onBackPressed();
            }
        });

        title.setText("Login");
        login();
    }

    public void login()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {
                    final String emailString = email.getText().toString();
                    final String passwordString = password.getText().toString();

                    //if everything is fine
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_LOGIN_OR_REGISTER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        //converting response to json object
                                        JSONObject obj = new JSONObject(response);
                                        JSONObject idObject = obj.getJSONObject("_id");

                                        String userId = idObject.getString("$oid");
                                        String userFirstName = obj.getString("first_name");
                                        String userLastName = obj.getString("last_name");
                                        sessionManager.Login(userId,userFirstName,userLastName,emailString,passwordString);

                                        finish();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                            params.put("email", emailString);
                            params.put("password", passwordString);
                            params.put("login", " ");
                            return params;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(stringRequest);
                    ////////////////////////////////////////////////////////////////////////////////////////////////////

                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    /*Vector<String> allEmails = AppController.getInstance().getDbManager().getAttributeValues(Url.USERS,"email");
                    Vector<String> allPasswords = AppController.getInstance().getDbManager().getAttributeValues(Url.USERS,"password");

                    boolean invalidUsername = true;
                    boolean invalidPassword = true;

                    for (int i=0;i<allEmails.size();i++)
                    {
                        if (allEmails.get(i).equals(emailString))
                        {
                            invalidUsername = false;
                            if (allPasswords.get(i).equals(Utility.md5(passwordString)))
                            {
                                invalidPassword = false;
                                finish();
                                sessionManager.Login(emailString,passwordString);
                                startActivity(new Intent(LoginActivity.this, SelectActivity.class));
                            }
                        }
                    }

                    if(invalidUsername == true)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid username !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (invalidPassword == true)
                        {
                            Toast.makeText(getApplicationContext(), "Invalid password !", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                }
            }
        });
    }

    private boolean validate()
    {
        if(email.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(!utility.isValidEmail((email.getText().toString())))
        {
            Toast.makeText(this,"Please enter valid email address !",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if(password.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().length()<8)
        {
            Toast.makeText(this,"Enter atleast 8 characters for password !",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(!utility.isValidPassword(password.getText().toString()))
        {
            //Toast.makeText(this,"Entered characters for password not allowed !",Toast.LENGTH_SHORT).show();
            //return false;
        }*/

        return true;
    }
}