package com.spectrographix.monadie.ui.mygroups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Dummy;
import com.spectrographix.monadie.classes.Product;

import java.util.ArrayList;

public class MyFavoritesActivity extends AppCompatActivity {

    ImageView backButton;
    TextView title;

    private RecyclerView liveAuctionsRecycler;
    private MyFavoritesActivityUsersListAdapter liveAuctionsAdapter;
    ArrayList<Product> liveAuctionsList;

    FrameLayout addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfavorites_activity_my_favorites);
        initialise();
        start();
    }

    private void initialise() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        liveAuctionsRecycler = (RecyclerView) findViewById(R.id.id_liveAuctionsRecyclerView);

        addMember = (FrameLayout) findViewById(R.id.id_addMember);
    }

    private void start() {
        recieveIntent();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFavoritesActivity.super.onBackPressed();
            }
        });

        title.setText("My Favorites");

        initiateLiveAuctionsRecycler();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyFavoritesActivity.this,AddFavoritesActivity.class));
            }
        });
    }

    private void recieveIntent() {
        //group = (Group)getIntent().getSerializableExtra("KEY_GROUP");
        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("ID"))
        {
            productId = bundle.getString("ID");
        }*/
    }

    private void initiateLiveAuctionsRecycler()
    {
        liveAuctionsRecycler.setLayoutManager(new GridLayoutManager(this,1));

        liveAuctionsAdapter = new MyFavoritesActivityUsersListAdapter(this, Dummy.getDummyUsers());
        liveAuctionsAdapter.notifyDataSetChanged();

        liveAuctionsRecycler.setAdapter(liveAuctionsAdapter);
        liveAuctionsRecycler.setItemAnimator(new DefaultItemAnimator());

        liveAuctionsRecycler.setNestedScrollingEnabled(false);
    }
}
