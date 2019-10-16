package com.spectrographix.monadie.ui.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.PermissionManager;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_ALL;
import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_READ_EXTERNAL_STORAGE;
import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE;

/**
 * Created by user productNameView on 2/3/2018.
 */

public class EditProfileActivity extends AppCompatActivity {

    FrameLayout parentLayout;


    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;
    private int INTENT_SELECT_FILE = 1;

    ImageView backButton,setPhoto,userImage;
    PermissionManager permissionManager;
    boolean isUserPhotoAdded = false;
    String userPhotoEncodedString;
    EditText userName,firstName,lastName;
    SessionManager sessionManager;
    Button submit;
    String photoLink = "http://139.59.3.107/mongo/projects/monadie/user_photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_edit_profile);
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
                EditProfileActivity.super.onBackPressed();
            }
        });

        title.setText("Edit Profile");
    }

    public void initialise()
    {
        utility = new Utility();
        permissionManager = new PermissionManager(this);

        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

        backButton = (ImageView) findViewById(R.id.backButton);
        userImage = (ImageView) findViewById(R.id.userImage);
        setPhoto = (ImageView) findViewById(R.id.setPhoto);
        sessionManager = new SessionManager(this);
        userName = (EditText) findViewById(R.id.userName);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        submit = (Button) findViewById(R.id.submit);
        populateUsersList();
    }

    private void start()
    {
        initiateActionListeners();
        /*Picasso.with(EditProfileActivity.this)
                .load(photoLink+sessionManager.getUserEmail())
                .into(userImage);*/

    }

    private void initiateActionListeners() {
        setPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissionManager.hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE))
                    galleryIntent();
                else
                    permissionManager.checkSinglePermission(PERMISSION_READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        userName.setText(sessionManager.getUserEmail());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();


            }
        });
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), INTENT_SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            //productImageName = utility.getRealPathFromURI2(this,data.getData());
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                String bitmapEncoded = Base64.encodeToString(b, Base64.DEFAULT);

                userPhotoEncodedString = bitmapEncoded;
                isUserPhotoAdded = true;
                /*Picasso.with(EditProfileActivity.this)
                        .load(photoLink+sessionManager.getUserEmail())
                        .into(userImage);*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userImage.setImageBitmap(bm);
        userImage.setBackgroundResource(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    //code for deny
                }
                break;
            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                break;
            case PERMISSION_ALL:
                break;

        }
    }

    private void populateUsersList()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_DETAILS_BY_EMAIL,
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
                                firstName.setText(userFirstName);
                                String userLastName = obj.getString("last_name");
                                lastName.setText(userLastName);
                                String userEmail = obj.getString("email");

                                String userPassword = obj.getString("password");
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
                        //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", sessionManager.getUserEmail());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void updateDetails()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_UPDATE_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONObject idObject = obj.getJSONObject("_id");

                            String userId = idObject.getString("$oid");

                            String userFirstName = obj.getString("first_name");
                            firstName.setText(userFirstName);
                            String userLastName = obj.getString("last_name");
                            lastName.setText(userLastName);
                            String userEmail = obj.getString("email");
                            if (!userEmail.equals(sessionManager.getUserEmail()))
                            {
                                sessionManager.setUserEmail(userEmail);
                            }

                            Picasso.with(EditProfileActivity.this)
                                    .load(photoLink+userName.getText().toString() + ".png")
                                    .into(userImage);

                            String userPassword = obj.getString("password");
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditProfileActivity.this,SettingsActivity.class));

                            }

                            catch (JSONException e) {
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
                params.put("firstName", firstName.getText().toString());
                params.put("lastName", lastName.getText().toString());
                params.put("old_email",sessionManager.getUserEmail());
                params.put("new_email",userName.getText().toString());
                if (isUserPhotoAdded)
                {
                 params.put("filename", userName.getText().toString() + ".png");
                 params.put("attachment", userPhotoEncodedString);
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    }