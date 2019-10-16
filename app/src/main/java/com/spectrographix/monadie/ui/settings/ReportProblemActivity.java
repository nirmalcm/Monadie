package com.spectrographix.monadie.ui.settings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.modules.javamail.AsyncMailSender;
import com.spectrographix.monadie.utility.Utility;


/**
 * Created by user productNameView on 2/3/2018.
 */

public class ReportProblemActivity extends AppCompatActivity {

    FrameLayout parentLayout;

    Typeface font_circular;
    Typeface font_maven;
    Typeface font_Quattrocento;

    Utility utility;

    ImageView backButton;
    EditText topic,content;
    Button reportButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_report_problem);
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
                ReportProblemActivity.super.onBackPressed();
            }
        });

        title.setText("Report A Problem");
    }

    public void initialise()
    {
        utility = new Utility();

        parentLayout = (FrameLayout) findViewById(R.id.parentLayoutOrderDetails);
        font_circular = Typeface.createFromAsset(getAssets(),"fonts/circular_std_medium.otf");
        font_maven = Typeface.createFromAsset(getAssets(),"fonts/maven_pro_medium.ttf");
        font_Quattrocento = Typeface.createFromAsset(getAssets(),"fonts/quattrocento_sans_regular.ttf");

        backButton = (ImageView) findViewById(R.id.backButton);
        topic = (EditText) findViewById(R.id.topic);
        content = (EditText) findViewById(R.id.content);
        reportButton = (Button) findViewById(R.id.reportButton);
    }

    private void start()
    {
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check())
                {
                    String messageToCompany = content.getText().toString();
                    String subjectToCompany = topic.getText().toString();
                    AsyncMailSender toCompany = new AsyncMailSender(messageToCompany,subjectToCompany);
                    toCompany.SendEmail();
                    Toast.makeText(getApplicationContext(),"Problem has reported successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReportProblemActivity.this,SettingsActivity.class));

                }
            }
        });
    }
    private boolean check()
    {
       if (topic.getText().toString().isEmpty())
       {
           Toast.makeText(getApplicationContext(),"Please enter the topic", Toast.LENGTH_LONG).show();
           return false;
       }
       if (content.getText().toString().isEmpty())
       {
           Toast.makeText(getApplicationContext(),"Please enter the content", Toast.LENGTH_LONG).show();
           return false;
       }
       return true;
    }

}