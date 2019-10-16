package com.spectrographix.monadie.ui.myauctions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Auction;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.home.MainActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateAuctionTwoActivity extends AppCompatActivity {

    SessionManager sessionManager;

    Intent productPhotoEncodedIntent;
    String dataType;

    Auction auction;
    String productName;
    String price;
    String description;
    String category;
    String attachment;
    String fileName;
    String productOwner;
    String basePrice;
    String start_time;
    String end_time;

    TextView numberOfGroups;
    String groups ="[]";
    String groupsCount = "0";
    int mGroupsCount = 0;

    FrameLayout addAuctionGroups;
    FrameLayout addTime;

    FrameLayout submitAuction;

    Spinner auctionTime;
    TextView auctionTimeText;

    String auctionTimeString = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myauctions_activity_create_auction_two);
        recieveIntent();
        setCustomToolbar();
        initialise();
        start();
    }

    private void recieveIntent() {
        auction = (Auction)getIntent().getSerializableExtra("AUCTION");
        productPhotoEncodedIntent = (Intent) getIntent().getParcelableExtra("DATA");
        dataType = getIntent().getStringExtra("DATA_TYPE");
    }

    private void setCustomToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        ImageView backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        TextView title = (TextView) findViewById(R.id.commonToolbarTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAuctionTwoActivity.super.onBackPressed();
            }
        });

        title.setText("Create Auction");
    }

    private void initialise() {
        sessionManager = new SessionManager(this);

        addAuctionGroups = (FrameLayout) findViewById(R.id.addGroup);
        addTime = (FrameLayout) findViewById(R.id.addTime);

        numberOfGroups = (TextView) findViewById(R.id.id_numberOfGroups);

        auctionTime = (Spinner) findViewById(R.id.id_auctionTime);
        auctionTimeText = (TextView) findViewById(R.id.id_auctionTimeText);

        submitAuction = (FrameLayout) findViewById(R.id.submitAuction);
    }

    private void start() {

        productName = auction.getAuctionProductName();
        price = auction.getAuctionProductPrice();
        description = auction.getAuctionProductDescription();
        category = auction.getAuctionProductCategory();
        //attachment = sessionManager.getImageString();
        fileName = auction.getAuctionProductImageFileName();
        productOwner = auction.getAuctionProductOwner();
        basePrice = auction.getBasePrice();
        start_time = auction.getStartTime();
        end_time = auction.getEndTime();

        if (dataType.equals("CAMERA"))
            onCaptureImageResult(productPhotoEncodedIntent);
        else
            onSelectFromGalleryResult(productPhotoEncodedIntent);

        addAuctionGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAuctionTwoActivity.this,AddAuctionGroupsActivity.class);
                startActivityForResult(intent,1);
            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        getAuctionTime();

        submitAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    createAuctionUsingVolley();
            }
        });
    }

    public void getAuctionTime()
    {
        final ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("-- Choose an auction time --");
        spinnerList.add("24 Hours");
        spinnerList.add("48 hours");
        spinnerList.add("72 Hours");
        spinnerList.add("96 hours");
        spinnerList.add("120 Hours");

        ArrayAdapter spinnerArrayAdapterMainCategory = new ArrayAdapter<String>(CreateAuctionTwoActivity.this, R.layout.myauctions_activity_create_auction_two_item_spinner, spinnerList);
        spinnerArrayAdapterMainCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        auctionTime.setPrompt("--Choose an auction time--");
        auctionTime.setAdapter(spinnerArrayAdapterMainCategory);
        auctionTime.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView)view).setText(null);
                auctionTimeString = spinnerList.get(position);
                if (position == 0)
                {
                    auctionTimeString = "-- choose an auction time --";
                }

                auctionTimeText.setText(auctionTimeString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                groups=data.getStringExtra("groups");
                groupsCount = data.getStringExtra("groupsCount");

                numberOfGroups.setText(groupsCount + " groups added !");

                mGroupsCount= Integer.valueOf(groupsCount);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        //productImageName = utility.getRealPathFromURI2(this,data.getData());
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

            byte[] b = bytes.toByteArray();
            String bitmapEncoded = Base64.encodeToString(b, Base64.DEFAULT);

            attachment = bitmapEncoded;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                attachment = bitmapEncoded;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate()
    {
        if(mGroupsCount < 1)
        {
            Toast.makeText(this,"Please add atleast 1 group !",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createAuctionUsingVolley()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_AUCTIONS_CREATE_AUCTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (obj.getString("success").equals("Created")) {
                                Toast.makeText(getApplicationContext(), "Created Auction", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(CreateAuctionTwoActivity.this,MainActivity.class));
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
                params.put("product_name", productName);
                params.put("price", price);
                params.put("description", description);
                params.put("category", "0");
                params.put("attachment", attachment);
                params.put("filename", fileName);
                params.put("product_owner", productOwner);
                params.put("base_price", basePrice);
                params.put("start_time", start_time);
                params.put("end_time", end_time);
                params.put("groups", groups);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}