package com.spectrographix.monadie.ui.settings;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.utility.Utility;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class VerificationActivity extends AppCompatActivity {

    FrameLayout parentLayout;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_verification);
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
                VerificationActivity.super.onBackPressed();
            }
        });

        title.setText("Edit Profile");
    }

    public void initialise()
    {
        utility = new Utility();

        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");
    }

    private void start()
    {
        initiateActionListeners();
    }

    private void initiateActionListeners() {
    }
}