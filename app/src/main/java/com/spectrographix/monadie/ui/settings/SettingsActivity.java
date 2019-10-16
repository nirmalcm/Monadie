package com.spectrographix.monadie.ui.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    SessionManager sessionManager;
    FrameLayout parentLayout;

    FrameLayout profile;
    FrameLayout verification;
    FrameLayout orders;
    FrameLayout financialInformation;
    FrameLayout changePassword;
    FrameLayout linkedAccounts;
    FrameLayout notificationSettings;
    FrameLayout reportProblem;
    FrameLayout logout;

    LinearLayout ordersClick;

    TextView biddingCurrent;
    TextView biddingHistory;
    TextView ordersList;

    ImageView backButton;

    ImageView changingArrow;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_settings);
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
                SettingsActivity.super.onBackPressed();
            }
        });

        title.setText("Settings");
    }

    public void initialise()
    {
        backButton = (ImageView) findViewById(R.id.backButton);


        sessionManager = new SessionManager(this);
        utility = new Utility();


        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);

        profile = (FrameLayout) findViewById(R.id.profile);
        verification = (FrameLayout) findViewById(R.id.verification);
        financialInformation = (FrameLayout) findViewById(R.id.financialInformation);
        changePassword = (FrameLayout) findViewById(R.id.changePassword);
        linkedAccounts = (FrameLayout) findViewById(R.id.linkedAccounts);
        notificationSettings = (FrameLayout) findViewById(R.id.notificationSettings);
        reportProblem = (FrameLayout) findViewById(R.id.reportProblem);
        logout = (FrameLayout) findViewById(R.id.logout);

        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");
    }

    private void start()
    {
        initiateActionListeners();
    }

    private void initiateActionListeners() {

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,EditProfileActivity.class));
            }
        });

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,VerificationActivity.class));
                Toast.makeText(getApplicationContext(),"Sorry this feature is not implemented", Toast.LENGTH_LONG).show();
            }
        });

        financialInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,FinancialInformationActivity.class));
                Toast.makeText(getApplicationContext(),"Sorry this feature is not implemented", Toast.LENGTH_LONG).show();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,ChangePasswordActivity.class));
            }
        });

        linkedAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,LinkedAccountsActivity.class));
                Toast.makeText(getApplicationContext(),"Sorry this feature is not implemented", Toast.LENGTH_LONG).show();
            }
        });

        notificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,NotificationSettingsActivity.class));
            }
        });

        reportProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,ReportProblemActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure want to logout ?");
                builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        sessionManager.Logout();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }
}