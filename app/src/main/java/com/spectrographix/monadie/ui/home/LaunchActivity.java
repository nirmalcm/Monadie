package com.spectrographix.monadie.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.ui.mygroups.MyGroupActivity;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class LaunchActivity extends AppCompatActivity {

    SessionManager sessionManager;

    FrameLayout parentLayout;

    ImageView backButton;
    TextView title;

    Button signUp;
    Button signIn;

    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_launch);
        setCustomToolbar();
        initialise();
        start();
    }

    private void setCustomToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchActivity.super.onBackPressed();
            }
        });

        title.setText("Monadie");
    }

    public void initialise()
    {
        sessionManager = new SessionManager(this);
        signUp = (Button) findViewById(R.id.signUp);
        signIn = (Button) findViewById(R.id.signIn);
        skip = (TextView) findViewById(R.id.id_skip);
    }

    private void start()
    {
        setCommonProperties();
        //AppController.getInstance().getDbManager().dumpDatabase();
        initiateActionListeners();
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void setCommonProperties() {
        parentLayout = (FrameLayout) findViewById(R.id.id_parentLaunchActivity);
        //Utility.setFontSize(parentLayout,15);
    }

    private void initiateActionListeners() {

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this,RegisterActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setIsSkipped(true);
                startActivity(new Intent(LaunchActivity.this,MainActivity.class));
            }
        });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed()
    {
        if (exit)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
        }
        else
        {
            Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}