package com.spectrographix.monadie.ui.mygroups;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Product;

import java.util.ArrayList;

public class AddFavoritesActivity extends AppCompatActivity {

    ImageView backButton;
    TextView toolbarTitle;

    private RecyclerView liveAuctionsRecycler;
    private AddFavoritesActivityUsersListAdapter liveAuctionsAdapter;
    ArrayList<Product> liveAuctionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorites_activity_add_favorites);
        initialise();
        start();
    }

    private void initialise() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);

        toolbarTitle = (TextView) findViewById(R.id.commonToolbarTitle);

        liveAuctionsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);
    }

    private void start() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFavoritesActivity.super.onBackPressed();
            }
        });

        toolbarTitle.setText("Add Favorites");
        initiateLiveAuctionsRecycler();
    }

    private void initiateLiveAuctionsRecycler()
    {
        liveAuctionsRecycler.setLayoutManager(new GridLayoutManager(this,1));

        liveAuctionsAdapter = new AddFavoritesActivityUsersListAdapter( this, Dummy.getDummyUsers());
        liveAuctionsAdapter.notifyDataSetChanged();

        liveAuctionsRecycler.setAdapter(liveAuctionsAdapter);
        liveAuctionsRecycler.setItemAnimator(new DefaultItemAnimator());

        liveAuctionsRecycler.setNestedScrollingEnabled(false);
    }
}