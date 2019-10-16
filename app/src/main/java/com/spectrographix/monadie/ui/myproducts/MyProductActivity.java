package com.spectrographix.monadie.ui.myproducts;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Group;
import com.spectrographix.monadie.classes.Product;
import com.squareup.picasso.Picasso;

public class MyProductActivity extends AppCompatActivity {

    ImageView backButton;
    TextView title;

    private Product product;

    ImageView productImageView;
    TextView productNameView;
    TextView productPriceView;
    TextView productDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myproducts_activity_my_product);
        initialise();
        start();
    }

    private void initialise() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);

        backButton = (ImageView) findViewById(R.id.commonToolbarBackButton);
        title = (TextView) findViewById(R.id.commonToolbarTitle);

        productImageView = (ImageView) findViewById(R.id.productsImage);
        productNameView = (TextView) findViewById(R.id.productsName);
        productPriceView = (TextView) findViewById(R.id.productsPrice);
        productDescriptionView = (TextView) findViewById(R.id.productsDescription);
    }

    private void start() {
        recieveIntent();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyProductActivity.super.onBackPressed();
            }
        });

        title.setText("My Product");

        Picasso.with(this)
                .load(product.getProductImage())
                .into(productImageView);
        productNameView.setText(product.getProductName());
        productPriceView.setText("SR "+product.getProductPrice());
        productDescriptionView.setText(product.getProductDescription());
    }

    private void recieveIntent() {
        product = (Product) getIntent().getSerializableExtra("KEY_SELECTED_PRODUCT");
        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("ID"))
        {
            productId = bundle.getString("ID");
        }*/
    }
}
