package com.spectrographix.monadie.ui.myauctions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Auction;
import com.spectrographix.monadie.classes.Category;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.home.MainActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.PermissionManager;
import com.spectrographix.monadie.utility.SessionManager;
import com.spectrographix.monadie.utility.Utility;


import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_ALL;
import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_CAMERA;
import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_READ_EXTERNAL_STORAGE;
import static com.spectrographix.monadie.utility.PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE;

public class CreateAuctionOneActivity extends AppCompatActivity {

    private int INTENT_REQUEST_CAMERA = 0, INTENT_SELECT_FILE = 1;

    SessionManager sessionManager;
    PermissionManager permissionManager;
    Utility utility;

    ImageView productImage;
    EditText productName;
    EditText productDescription;
    EditText productPrice;
    String productMainCategoryId;
    String productSubCategoryId;
    String productSubSubCategoryId;

    ImageView pickPhoto;
    ImageView takePhoto;

    String productImageName;

    Spinner productMainCategorySpinner;
    ArrayAdapter<String> spinnerArrayAdapterMainCategory;
    ArrayList<Category> categoryListMainCategory;

    Spinner productSubCategorySpinner;
    ArrayAdapter<String> spinnerArrayAdapterSubCategory;
    ArrayList<Category> categoryListSubCategory;

    boolean isProductPhotoAdded = false;
    String productPhotoEncodedString;
    Intent productPhotoEncodedIntent;
    String dataType = "NONE";

    EditText basePriceField;
    int base_price;
    String start_time;
    String end_time;

    Calendar currentCalendar;
    private int year, month, day, hour, minute;
    Calendar startCalendar;
    String startDateAndTimeString;
    Calendar endCalendar;
    String endDateAndTimeString;

    TextView startTime;
    TextView endTime;

    //TextView setStartTime;
    //TextView setEndTime;

    FrameLayout addToAuction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myauctions_activity_create_auction_one);
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
                CreateAuctionOneActivity.super.onBackPressed();
            }
        });

        title.setText("Create Auction");
    }

    private void initialise() {
        sessionManager = new SessionManager(this);
        permissionManager = new PermissionManager(this);
        utility = new Utility();

        productImage = (ImageView) findViewById(R.id.productImage);

        pickPhoto = (ImageView) findViewById(R.id.choosePhoto);
        takePhoto = (ImageView) findViewById(R.id.takePhoto);

        productName = (EditText) findViewById(R.id.productName);
        productDescription = (EditText) findViewById(R.id.productDescription);
        productPrice = (EditText) findViewById(R.id.productPrice);

        productMainCategorySpinner = (Spinner) findViewById(R.id.productMainCategorySpinner);
        categoryListMainCategory = new ArrayList<>();

        productSubCategorySpinner = (Spinner) findViewById(R.id.productSubCategorySpinner);
        categoryListSubCategory = new ArrayList<>();

        basePriceField = (EditText) findViewById(R.id.basePriceField);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        addToAuction = (FrameLayout) findViewById(R.id.addToAuction);

        //setStartTime = (TextView) findViewById(R.id.setStartTime);
        //setEndTime = (TextView) findViewById(R.id.setEndTime);
    }

    private void start() {

        setProductPhoto();
        setProductMainCategory();

        prepareCalendarToday();
        setAuctionStartDateAndTime();
        setAuctionEndDateAndTime();

        initiateAuction();
    }

    public void setProductPhoto()
    {
        pickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permissionManager.hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE))
                    galleryIntent();
                else
                    permissionManager.checkSinglePermission(PERMISSION_READ_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permissionManager.hasPermissions(Manifest.permission.CAMERA))
                    cameraIntent();
                else
                    permissionManager.checkSinglePermission(PERMISSION_CAMERA,Manifest.permission.CAMERA);
            }
        });


    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), INTENT_SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, INTENT_REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == INTENT_REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        //productImageName = utility.getRealPathFromURI2(this,data.getData());
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

            byte[] b = bytes.toByteArray();
            String bitmapEncoded = Base64.encodeToString(b, Base64.DEFAULT);

            productPhotoEncodedIntent = data;
            dataType = "CAMERA";

            productPhotoEncodedString = bitmapEncoded;
            isProductPhotoAdded = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        productImage.setImageBitmap(thumbnail);
        productImage.setBackgroundResource(0);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                String bitmapEncoded = Base64.encodeToString(b, Base64.DEFAULT);

                productPhotoEncodedIntent = data;
                dataType = "GALLERY";
                productPhotoEncodedString = bitmapEncoded;
                isProductPhotoAdded = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productImage.setImageBitmap(bm);
        productImage.setBackgroundResource(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    //code for deny
                }
                break;
            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                break;
            case PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    //code for deny
                }
                break;
            case PERMISSION_ALL:
                break;

        }
    }

    public void setProductMainCategory()
    {
        ArrayList<String> categoryListOnSpinner = getMainCategoryListOnSpinner();

        spinnerArrayAdapterMainCategory = new ArrayAdapter<String>(CreateAuctionOneActivity.this,
                android.R.layout.simple_spinner_item, categoryListOnSpinner);
        spinnerArrayAdapterMainCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        productMainCategorySpinner.setPrompt("Choose a main category");
        productMainCategorySpinner.setAdapter(spinnerArrayAdapterMainCategory);
        productMainCategorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryListSubCategory.clear();
                productMainCategoryId = String.valueOf(categoryListMainCategory.get(position).getCategoryId());
                setProductSubCategory(productMainCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setProductSubCategory(String parentCategoryId)
    {
        ArrayList<String> mainCategoryListOnSpinner = getSubCategoryListOnSpinner(parentCategoryId);

        spinnerArrayAdapterSubCategory = new ArrayAdapter<String>(CreateAuctionOneActivity.this,
                android.R.layout.simple_spinner_item, mainCategoryListOnSpinner);
        spinnerArrayAdapterSubCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        productSubCategorySpinner.setPrompt("Choose a sub category");
        productSubCategorySpinner.setAdapter(spinnerArrayAdapterSubCategory);
        productSubCategorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categoryListSubCategory.isEmpty())
                {
                    productSubCategoryId = String.valueOf(categoryListSubCategory.get(position).getCategoryId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public ArrayList<String> getMainCategoryListOnSpinner()
    {
        final ArrayList<String> mainCategoryListOnSpinner = new ArrayList<>();
        JsonArrayRequest categoriesRequest = new JsonArrayRequest(Url.MONGO_CATEGORIES_GET_PARENT_CATEGORIES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                JSONObject idObject = obj.getJSONObject("_id");

                                String categoryId = idObject.getString("$oid");
                                String categoryName = obj.getString("name");
                                String categoryImage = obj.getString("image");
                                String categoryParent = obj.getString("parent");

                                Category category = new Category(categoryId,categoryName,categoryImage,categoryParent);
                                categoryListMainCategory.add(category);
                                mainCategoryListOnSpinner.add(categoryName);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        spinnerArrayAdapterMainCategory.notifyDataSetChanged();
                        //setProductSubCategory();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(categoriesRequest);

        return mainCategoryListOnSpinner;
    }

    private ArrayList<String> getSubCategoryListOnSpinner(final String parentCategoryId)
    {
        final ArrayList<String> subCategoryListOnSpinner = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_CATEGORIES_GET_CHILD_CATEGORIES_BY_PARENT_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject idObject = obj.getJSONObject("_id");

                                String categoryId = idObject.getString("$oid");
                                String categoryName = obj.getString("name");
                                String categoryImage = obj.getString("image");
                                String categoryParent = obj.getString("parent");

                                Category category = new Category(categoryId,categoryName,categoryImage,categoryParent);
                                categoryListSubCategory.add(category);
                                subCategoryListOnSpinner.add(categoryName);
                            }
                            spinnerArrayAdapterSubCategory.notifyDataSetChanged();
                            //setProductSubSubCategory();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("parent_id", parentCategoryId);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);

        return subCategoryListOnSpinner;
    }

    private void setAuctionEndDateAndTime() {
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEndDateAndTime();
            }
        });

    }

    private void setAuctionStartDateAndTime() {
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartDateAndTime();
            }
        });

    }

    private void prepareCalendarToday() {
        currentCalendar = Calendar.getInstance();
        year = currentCalendar.get(Calendar.YEAR);
        month = currentCalendar.get(Calendar.MONTH);
        day = currentCalendar.get(Calendar.DAY_OF_MONTH);
        hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        minute = currentCalendar.get(Calendar.MINUTE);

    }


    private void getStartDateAndTime()
    {
        startCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAuctionOneActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        startCalendar.set(Calendar.YEAR, year);
                        startCalendar.set(Calendar.MONTH, monthOfYear);
                        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateStartCalendarLabel();
                    }
                }, year, month, day);
        datePickerDialog.show();

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateAuctionOneActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startCalendar.set(Calendar.MINUTE, minute);
                        updateStartCalendarLabel();
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void getEndDateAndTime()
    {
        endCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAuctionOneActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        endCalendar.set(Calendar.YEAR, year);
                        endCalendar.set(Calendar.MONTH, monthOfYear);
                        endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateEndCalendarLabel();
                    }
                }, year, month, day);
        datePickerDialog.show();

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateAuctionOneActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        endCalendar.set(Calendar.MINUTE, minute);
                        updateEndCalendarLabel();
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void updateStartCalendarLabel() {
        startDateAndTimeString = new SimpleDateFormat(utility.DATE_TIME_FORMAT_MULTILINE).format(startCalendar.getTime());
        startTime.setText(startDateAndTimeString);
        start_time = utility.convertSimpleDateTimeFormatStringToTimeStampString(startDateAndTimeString,utility.DATE_TIME_FORMAT_MULTILINE);
    }

    private void updateEndCalendarLabel() {
        endDateAndTimeString = new SimpleDateFormat(utility.DATE_TIME_FORMAT_MULTILINE).format(endCalendar.getTime());
        endTime.setText(endDateAndTimeString);
        end_time = utility.convertSimpleDateTimeFormatStringToTimeStampString(endDateAndTimeString,utility.DATE_TIME_FORMAT_MULTILINE);
    }

    private boolean validate()
    {
        if(productName.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter your Product Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(productDescription.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please enter your Product Description",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(productPrice.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please provide your Product Price",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (categoryListMainCategory.isEmpty())
        {
            //Toast.makeText(this,"Server error ! Unable to fetch categories from server",Toast.LENGTH_SHORT).show();
            ///return false;
        }
        if (categoryListSubCategory.isEmpty())
        {
            //Toast.makeText(this,"No sub category available to add product !",Toast.LENGTH_SHORT).show();
            //return false;
        }
        if (!isProductPhotoAdded)
        {
            Toast.makeText(this,"Please provide a product photo !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (basePriceField.getText().toString().isEmpty())
        {
            Toast.makeText(this,"Please provide a base price !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(String.valueOf(base_price).isEmpty())
        {
            Toast.makeText(this,"Error : Not able to get base productPrice",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(start_time==null || start_time.isEmpty())
        {
            Toast.makeText(this,"Please set a start date and time",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(end_time == null || end_time.isEmpty())
        {
            Toast.makeText(this,"Please set an end date and time",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initiateAuction() {
        addToAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    final String productNameString = productName.getText().toString();
                    final String priceString = productPrice.getText().toString();
                    final String descriptionString = productDescription.getText().toString();
                    final String categoryString = productSubSubCategoryId;
                    final String attachmentString = productPhotoEncodedString;
                    final String filenameString = utility.getCurrentDateTimeInString("ddMMyyyy_HHmmss") + "_" + ".png";

                    Intent intent = new Intent(CreateAuctionOneActivity.this,CreateAuctionTwoActivity.class);
                    Auction auction = new Auction(productNameString,
                                                    priceString,
                                                    descriptionString,
                                                    "0",
                                                    "",
                                                    filenameString,
                                                    sessionManager.getUserId(),
                                                    basePriceField.getText().toString(),
                                                    start_time,
                                                    end_time);
                    //sessionManager.setImageString(attachmentString);
                    intent.putExtra("AUCTION",(Serializable)auction);
                    intent.putExtra("DATA",productPhotoEncodedIntent);
                    intent.putExtra("DATA_TYPE",dataType);

                    /*Bundle bundle = new Bundle();
                    bundle.putString("product_name", productNameString);
                    bundle.putString("price", priceString);
                    bundle.putString("description", descriptionString);
                    bundle.putString("category", "0");
                    //bundle.putString("attachment", attachmentString);
                    bundle.putString("filename", filenameString);
                    bundle.putString("product_owner", sessionManager.getUserId());
                    bundle.putString("base_price", basePriceField.getText().toString());
                    bundle.putString("start_time", start_time);
                    bundle.putString("end_time", end_time);
                    intent.putExtras(bundle);*/

                    startActivity(intent);
                }
            }
        });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAuctionOneActivity.this);
        builder.setTitle("Exit and add later ?");
        //builder.setMessage("You can add this product to auction later !");
        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                startActivity(new Intent(CreateAuctionOneActivity.this,MainActivity.class));
                finish();
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
}
