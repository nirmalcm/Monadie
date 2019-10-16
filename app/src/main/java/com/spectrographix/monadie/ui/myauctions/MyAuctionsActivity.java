package com.spectrographix.monadie.ui.myauctions;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.spectrographix.monadie.classes.Auction;
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.home.MainActivityLiveAuctionsAdapter;
import com.spectrographix.monadie.ui.mygroups.MyGroupActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyAuctionsActivity extends AppCompatActivity{

    FrameLayout createAuction;

    SessionManager sessionManager;
    Utility utility;

    private SwipeRefreshLayout swipeRefreshLayout;

    FrameLayout parentMainActivity;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    private RecyclerView liveAuctionsRecycler;
    private MainActivityLiveAuctionsAdapter liveAuctionsAdapter;
    List<Auction> liveAuctionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myauctions_activity_my_auctions);
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
                MyAuctionsActivity.super.onBackPressed();
            }
        });

        title.setText("My Auctions");
    }

    private void initialise() {
        sessionManager = new SessionManager(getApplicationContext());

        utility = new Utility();

        parentMainActivity = (FrameLayout) findViewById(R.id.parentMainActivity);
        liveAuctionsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        liveAuctionsList = new ArrayList<>();

        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

        createAuction = (FrameLayout) findViewById(R.id.id_createAuction);
    }

    private void start() {
        utility.applyFonts(parentMainActivity,font_maven);

        initiateLiveAuctionsRecycler();

        createAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAuctionsActivity.this,CreateAuctionOneActivity.class));
            }
        });

        refresh();
    }

    private void refresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateCreatedAuctions();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        populateCreatedAuctions();
                                    }
                                }
        );
    }

    private void initiateLiveAuctionsRecycler()
    {
        liveAuctionsRecycler.setLayoutManager(new GridLayoutManager(this,2));

        liveAuctionsAdapter = new MainActivityLiveAuctionsAdapter(this, liveAuctionsList);
        liveAuctionsAdapter.notifyDataSetChanged();

        liveAuctionsRecycler.setAdapter(liveAuctionsAdapter);
        liveAuctionsRecycler.setItemAnimator(new DefaultItemAnimator());

        liveAuctionsRecycler.setNestedScrollingEnabled(false);
    }

    private void populateCreatedAuctions()
    {
        liveAuctionsList.clear();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_AUCTIONS_GET_CREATED_AUCTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i =0;i<jsonArray.length();i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                JSONObject idObject = obj.getJSONObject("_id");

                                String auctionId = idObject.getString("$oid");

                                String auctionProductName = obj.getString("product_name");
                                String auctionProductDescription = obj.getString("product_description");
                                String auctionProductPrice = obj.getString("product_price");
                                String auctionProductOwner = obj.getString("product_owner");
                                String auctionProductImage = obj.getString("product_image");

                                String basePrice = obj.getString("base_price");
                                String startTime = obj.getString("start_time");
                                String endTime = obj.getString("end_time");

                                Auction auction = new Auction(auctionId,auctionProductName,auctionProductDescription,auctionProductPrice,auctionProductOwner,auctionProductImage,basePrice,startTime,endTime);
                                liveAuctionsList.add(auction);
                            }
                            liveAuctionsAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Operation Failed !", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", sessionManager.getUserId());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}