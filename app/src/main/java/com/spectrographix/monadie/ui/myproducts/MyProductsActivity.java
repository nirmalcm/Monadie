package com.spectrographix.monadie.ui.myproducts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Product;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class MyProductsActivity extends AppCompatActivity {

    ImageView backButton;
    TextView title;

    SessionManager sessionManager;
    Utility utility;

    private SwipeRefreshLayout swipeRefreshLayout;

    FrameLayout parentMainActivity;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    private RecyclerView liveAuctionsRecycler;
    private MyProductsActivityProductsListAdapter liveAuctionsAdapter;
    List<Product> liveAuctionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myproducts_activity_my_products);
        initialise();
        start();
    }

    private void initialise()
    {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        sessionManager = new SessionManager(getApplicationContext());
        //sessionManager.CheckLogin();

        utility = new Utility();

        parentMainActivity = (FrameLayout) findViewById(R.id.parentMainActivity);
        liveAuctionsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        liveAuctionsList = new ArrayList<>();

        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");
    }

    private void start()
    {
        title.setText("My Products");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProductsActivity.super.onBackPressed();
            }
        });
        refresh();

        utility.applyFonts(parentMainActivity,font_maven);

        initiateLiveAuctionsRecycler();
    }


    private void refresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                liveAuctionsList.clear();
                liveAuctionsList = Dummy.getDummyProducts();
                liveAuctionsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
    }

    private void initiateLiveAuctionsRecycler()
    {
        liveAuctionsRecycler.setLayoutManager(new GridLayoutManager(this,2));

        liveAuctionsList = Dummy.getDummyProducts();
        liveAuctionsAdapter = new MyProductsActivityProductsListAdapter(this, liveAuctionsList);
        liveAuctionsAdapter.notifyDataSetChanged();

        liveAuctionsRecycler.setAdapter(liveAuctionsAdapter);
        liveAuctionsRecycler.setItemAnimator(new DefaultItemAnimator());

        liveAuctionsRecycler.setNestedScrollingEnabled(false);
    }

}
