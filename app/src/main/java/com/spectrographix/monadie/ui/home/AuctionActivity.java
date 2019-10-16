package com.spectrographix.monadie.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Auction;
import com.spectrographix.monadie.modules.javamail.AsyncMailSender;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;
import com.squareup.picasso.Picasso;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class AuctionActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Utility utility;

    ImageView backButton;
    TextView title;

    FrameLayout parentMyAuctionActivity;
    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Auction selectedAuction;

    int auctionProductId;
    String auctionProductImageUrl;
    String auctionProductName;
    String auctionProductDescription;
    Double auctionProductPrice;
    String auctionProductOwner;

    TextView remainingTimeView;
    EditText biddingPriceField;
    Button makeAnOfferButton;
    ImageView productImageView;
    TextView productNameView;
    TextView productPriceView;

    Bundle receivedBundle;
    MyCountDownTimer myCountDownTimer;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_auction);
        initialise();
        start();
    }

    public void initialise()
    {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        sessionManager = new SessionManager(this);
        utility = new Utility();

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        parentMyAuctionActivity = (FrameLayout) findViewById(R.id.parentMyAuctionActivity);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        remainingTimeView = (TextView) findViewById(R.id.remainingTime);

        biddingPriceField = (EditText) findViewById(R.id.biddingPrice);
        makeAnOfferButton = (Button) findViewById(R.id.makeAnOffer);

        productImageView = (ImageView) findViewById(R.id.productsImage);
        productNameView = (TextView) findViewById(R.id.productsName);
        productPriceView = (TextView) findViewById(R.id.productsPrice);
    }

    private void start()
    {
        recieveIntent();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuctionActivity.super.onBackPressed();
            }
        });

        title.setText("Live Auction");

        utility.applyFonts(parentMyAuctionActivity,font_circular);

        //remainingTimeView.setVisibility(View.INVISIBLE);

        Picasso.with(this)
                .load(selectedAuction.getAuctionProductImage())
                .into(productImageView);
        productNameView.setText(selectedAuction.getAuctionProductName());
        productPriceView.setText("SR "+selectedAuction.getBasePrice());

        refresh();
        makeAnOffer();
    }

    private void recieveIntent() {

        selectedAuction = (Auction) getIntent().getSerializableExtra("KEY_SELECTED_AUCTION");
        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("ID"))
        {
            productId = bundle.getString("ID");
        }*/
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                long remainingTimeLong = utility.getDifferenceInDateAndTimeInString(utility.getCurrentDateTimeInString(utility.DATE_TIME_FORMAT_TIMER),utility.convertTimeStampStringToSimpleDateTimeFormatString(selectedAuction.getEndTime(),utility.DATE_TIME_FORMAT_TIMER));
                myCountDownTimer = new MyCountDownTimer(remainingTimeLong,1000);
                myCountDownTimer.start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        long remainingTimeLong = utility.getDifferenceInDateAndTimeInString(utility.getCurrentDateTimeInString(utility.DATE_TIME_FORMAT_TIMER),utility.convertTimeStampStringToSimpleDateTimeFormatString(selectedAuction.getEndTime(),utility.DATE_TIME_FORMAT_TIMER));
                                        myCountDownTimer = new MyCountDownTimer(remainingTimeLong,1000);
                                        myCountDownTimer.start();
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
    }

    private void makeAnOffer() {
        biddingPriceField.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(biddingPriceField, InputMethodManager.SHOW_IMPLICIT);
        /*makeAnOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePriceField())
                {
                    biddingPriceField.setText("");
                }
            }
        });*/
    }

    private boolean validatePriceField()
    {
        if (biddingPriceField.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please enter a bid value !", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Double.valueOf(biddingPriceField.getText().toString())<=Double.valueOf(selectedAuction.getBidPrice()))
        {
            Toast.makeText(getApplicationContext(), "Please enter a value greater than the current bid !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        /*if(!sessionManager.getUserEmail().equals(""))
        {
            return true;
        }

        MenuItem addToAuctionMenu = menu.findItem(R.id.AddToAuction);
        MenuItem removeFromAuctionMenu = menu.findItem(R.id.removeFromAuction);
        if(isProductSetForAuction)
        {
            removeFromAuctionMenu.setVisible(true);
            addToAuctionMenu.setVisible(false);
        }
        else
        {
            removeFromAuctionMenu.setVisible(false);
            addToAuctionMenu.setVisible(true);
        }
        //super.onPrepareOptionsMenu(menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.id_groupMenu:
                //Intent addProductToAuction = new Intent(this, MainActivity.class);
                //Bundle bundle = new Bundle();
                //startActivity(addProductToAuction);
                break;
            default:
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }*/

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);
            int durationSeconds = progress;

            String remainingTimeLeftString = String.format("%02d:%02d:%02d", durationSeconds / 3600,
                    (durationSeconds % 3600) / 60, (durationSeconds % 60));
            remainingTimeView.setText("Remaining time : "+remainingTimeLeftString);
        }

        @Override
        public void onFinish() {

            remainingTimeView.setText("AUCTION OVER");
            /*if(selectedAuction !=null)
            {
                if(selectedAuction.getLastBidUser().equals(sessionManager.getUserEmail()))
                {
                    AsyncMailSender asyncMailSender = new AsyncMailSender("Auction won by "+sessionManager.getUserEmail()+" for product "+selectedAuction.getAuctionProductName()+" [product Id : "+selectedAuction.getProductId()+"]");
                    asyncMailSender.SendEmail();

                    Intent toCongratsActivity = new Intent(AuctionActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    toCongratsActivity.putExtras(bundle);
                    startActivity(toCongratsActivity);
                    finish();
                }
                else
                {
                    startActivity(new Intent(AuctionActivity.this,MainActivity.class));
                    finish();
                }
            }*/
        }
    }
}