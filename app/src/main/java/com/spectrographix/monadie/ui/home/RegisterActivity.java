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
 * Created by user productNameView on 2/3/2018.
 */

public class RegisterActivity extends AppCompatActivity{

    ImageView backButton;
    TextView title;

    SessionManager sessionManager;
    Utility utility;

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;

    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_register);
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

        firstName = (EditText) findViewById(R.id.userFirstName);
        lastName = (EditText) findViewById(R.id.userLastName);
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        register = (Button) findViewById(R.id.register);
    }

    private void start()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.super.onBackPressed();
            }
        });

        title.setText("Register");
        register();
    }

    public void register()
    {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate())
                {
                    final String firstNameString = firstName.getText().toString();
                    final String lastNameString = lastName.getText().toString();
                    final String emailString = email.getText().toString();
                    final String passwordString = password.getText().toString();

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
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(), "User already exists ! Please use another productPriceView", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
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
                            params.put("first_name", firstNameString);
                            params.put("last_name", lastNameString);
                            params.put("email", emailString);
                            params.put("password", passwordString);
                            params.put("register", " ");

                            return params;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(stringRequest);

                }
            }
        });
    }

    private boolean Validate()
    {
        if(firstName.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter first name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(lastName.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter last name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if(!utility.isValidEmail((productPriceView.getText().toString())))
        {
            Toast.makeText(this,"Please enter valid productPriceView address !",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if(password.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().length()<8)
        {
            Toast.makeText(this,"Entere atleast 8 characters for password !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(confirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please confirm your password",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            Toast.makeText(this,"Passwords does not match !",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}