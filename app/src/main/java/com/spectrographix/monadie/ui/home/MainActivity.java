package com.spectrographix.monadie.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.myauctions.MyAuctionsActivity;
import com.spectrographix.monadie.ui.mygroups.MyGroupsActivity;
import com.spectrographix.monadie.ui.myproducts.MyProductsActivity;
import com.spectrographix.monadie.ui.settings.SettingsActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;
    Utility utility;

    boolean isSpecial = true;

    CardView privateAuctions;
    TextView privateText;

    CardView publicAuctions;
    TextView publicText;

    TextView skipNotice;

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
        setContentView(R.layout.home_activity_main);
        initialise();
        start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myauctions) {
            if (sessionManager.isLoggedIn())
                startActivity(new Intent(MainActivity.this, MyAuctionsActivity.class));
            else
                Toast.makeText(getApplicationContext(), "You need to login !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_myproducts) {
            if (sessionManager.isLoggedIn())
                startActivity(new Intent(MainActivity.this, MyProductsActivity.class));
            else
                Toast.makeText(getApplicationContext(), "You need to login !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_groups) {
            if (sessionManager.isLoggedIn())
                startActivity(new Intent(MainActivity.this, MyGroupsActivity.class));
            else
                Toast.makeText(getApplicationContext(), "You need to login !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            if (sessionManager.isLoggedIn())
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            else
                Toast.makeText(getApplicationContext(), "You need to login !", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login_register) {
            if (!sessionManager.isLoggedIn())
            {
                sessionManager.setIsSkipped(false);
                startActivity(new Intent(MainActivity.this, LaunchActivity.class));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initialise()
    {
        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isSkipped())
            sessionManager.CheckLogin();

        skipNotice = findViewById(R.id.id_skipNotice);

        utility = new Utility();

        parentMainActivity = (FrameLayout) findViewById(R.id.parentMainActivity);

        privateAuctions = (CardView) findViewById(R.id.id_private);
        privateText = (TextView) findViewById(R.id.id_privateText);

        publicAuctions = (CardView) findViewById(R.id.id_public);
        publicText = (TextView) findViewById(R.id.id_public_text);

        liveAuctionsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        liveAuctionsList = new ArrayList<>();

        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");
    }

    private void start()
    {
        refresh();

        initiateNavigationDrawyer();

        utility.applyFonts(parentMainActivity,font_maven);

        initiateLiveAuctionsRecycler();

        selectPrivate();

        privateAuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPrivate();
                populatePrivateAuctions();
            }
        });

        publicAuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectForAll();
                populatePublicAuctions();
            }
        });
    }

    private void selectPrivate()
    {
        if (sessionManager.isSkipped())
            skipNotice.setVisibility(View.VISIBLE);

        isSpecial = true;
        privateAuctions.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        privateAuctions.setCardElevation(5);
        privateText.setTextColor(getResources().getColor(R.color.colorWhite));

        publicAuctions.setCardBackgroundColor(getResources().getColor(R.color.colorSwitch));
        publicAuctions.setCardElevation(0);
        publicText.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void selectForAll()
    {
        isSpecial = false;

        skipNotice.setVisibility(View.INVISIBLE);
        publicAuctions.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        publicAuctions.setCardElevation(5);
        publicText.setTextColor(getResources().getColor(R.color.colorWhite));

        privateAuctions.setCardBackgroundColor(getResources().getColor(R.color.colorSwitch));
        privateAuctions.setCardElevation(0);
        privateText.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void initiateNavigationDrawyer()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        FrameLayout skipFrame = (FrameLayout) headerView.findViewById(R.id.id_nav_skipFrame);
        ImageView userImageNav = (ImageView) headerView.findViewById(R.id.id_nav_userImage);
        TextView userNameNav = (TextView) headerView.findViewById(R.id.id_nav_userName);
        TextView userEmailNav = (TextView) headerView.findViewById(R.id.id_nav_userEmail);

        if (sessionManager.isSkipped())
        {
            navigationView.getMenu().findItem(R.id.nav_login_register).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_myauctions).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_myproducts).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_groups).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_settings).setVisible(false);

            skipFrame.setVisibility(View.VISIBLE);
            userImageNav.setVisibility(View.INVISIBLE);
            userNameNav.setVisibility(View.INVISIBLE);
            userEmailNav.setVisibility(View.INVISIBLE);
        }
        else
        {
            navigationView.getMenu().findItem(R.id.nav_login_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_myauctions).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_myproducts).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_groups).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);

            skipFrame.setVisibility(View.INVISIBLE);
            userImageNav.setVisibility(View.VISIBLE);
            userNameNav.setVisibility(View.VISIBLE);
            userEmailNav.setVisibility(View.VISIBLE);

            userNameNav.setText(sessionManager.getUserFullName());
            userEmailNav.setText(sessionManager.getUserEmail());
        }
    }

    private void refresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isSpecial)
                    populatePrivateAuctions();
                else
                    populatePublicAuctions();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isSpecial)
                                            populatePrivateAuctions();
                                        else
                                            populatePublicAuctions();
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

    private void populatePublicAuctions()
    {
        liveAuctionsList.clear();
        liveAuctionsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_AUCTIONS_GET_PUBLIC_AUCTIONS,
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
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void populatePrivateAuctions()
    {
        liveAuctionsList.clear();
        liveAuctionsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_AUCTIONS_GET_PRIVATE_AUCTIONS,
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
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        liveAuctionsAdapter.notifyDataSetChanged();
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