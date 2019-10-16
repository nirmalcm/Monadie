package com.spectrographix.monadie.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.utility.Utility;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class TestActivity extends AppCompatActivity {

    FrameLayout parentLayout;

    TextView order;
    TextView name;
    TextView company;
    TextView riyal;
    TextView auctioneer;
    TextView returnAndExchange;
    TextView returnPolicy;
    TextView exchangePolicy;
    TextView delivered;
    TextView date;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_order_details);
        initialise();
        start();
    }

    public void initialise()
    {
        utility = new Utility();

        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

        order =(TextView) findViewById(R.id.order);
        name =(TextView) findViewById(R.id.name);
        company =(TextView) findViewById(R.id.company);
        riyal =(TextView) findViewById(R.id.riyal);
        auctioneer =(TextView) findViewById(R.id.auctioneer);
        returnAndExchange =(TextView) findViewById(R.id.returnAndExchange);
        returnPolicy=(TextView) findViewById(R.id.returnPolicy);
        exchangePolicy =(TextView) findViewById(R.id.exchangePolicy);
        delivered =(TextView) findViewById(R.id.delivered);
        date =(TextView) findViewById(R.id.date);

    }

    private void start()
    {
        //utility.applyFonts(parentLayout,font_maven);

        order.setTypeface(font_maven);
        name.setTypeface(font_maven,Typeface.BOLD);
        company.setTypeface(font_Quattrocento);
        riyal.setTypeface(font_maven,Typeface.BOLD);
        auctioneer.setTypeface(font_maven,Typeface.BOLD);
        returnAndExchange.setTypeface(font_maven,Typeface.BOLD);
        returnPolicy.setTypeface(font_Quattrocento);
        exchangePolicy.setTypeface(font_Quattrocento);
        delivered.setTypeface(font_maven,Typeface.BOLD);
        date.setTypeface(font_Quattrocento);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);
        initiateActionListeners();
    }

    private void initiateActionListeners() {

    }
}