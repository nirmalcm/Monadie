package com.spectrographix.monadie.database;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.utility.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static android.content.ContentValues.TAG;

public class DbManager {

    private HashMap<String, DbTableValues> mTables = new HashMap<String, DbTableValues>();

    public boolean dumpDatabase()
    {
        loadAllDatabaseTables();
        return true;
    }

    public boolean loadAllDatabaseTables()
    {
        executeScript(Url.USERS, new String[]{"id", "first_name", "last_name", "email", "password"});
        executeScript(Url.PRODUCTS,   new String[]{"id", "product_name", "price", "description", "image", "category_id", "product_owner"});
        executeScript(Url.CATEGORIES, new String[]{"id", "category_name", "parent_id" , "category_image"});
        executeScript(Url.AUCTIONS,   new String[]{"id", "product_id", "base_price", "bid_price", "start_time", "end_time", "bidded_user"});
        executeScript(Url.BIDDING_DETAILS, new String[]{"id", "auction_id", "bidding_price", "bidding_user"});

        return true;
    }

    public String getAttributeValue(final String scriptName, String attr) {
        DbTableValues values = mTables.get(scriptName);
        if (values != null)
        {
            return values.getAttributeValue(attr);
        }
        return null;
    }
    public Vector<String> getAttributeValues(final String scriptName, String attr) {
        DbTableValues values = mTables.get(scriptName);
        if (values != null)
        {
            return values.getAttributeValues(attr);
        }
        return null;
    }

    public boolean executeScript(final String scriptName, final String[] attrs)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, scriptName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DbTableValues responseVals = new DbTableValues();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                for (String attr : attrs) {
                                    String val = obj.getString(attr);
                                    responseVals.addAttributeValue(attr, val);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mTables.put(scriptName, responseVals);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest);
        return false;
    }

    public String executeLoginScript(final String emailString, final String passwordString) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_LOGIN_OR_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String returnVal = null;
                        try {
                            JSONObject obj = new JSONObject(response);
                            returnVal =  obj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            returnVal = "Invalid username or password";
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailString);
                params.put("password", passwordString);
                params.put("login", " ");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
        return null;
    }

    public String executeLoginScriptOld(final String emailString, final String passwd) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_LOGIN_OR_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String returnVal = null;
                        try {
                            JSONObject obj = new JSONObject(response);
                            returnVal =  obj.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            returnVal = "Invalid username or password";
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        return null;
    }

    public boolean executeRegisterScript(String scriptName) {
        return false;
    }

    public boolean executeScriptOld(final String scriptName, final String[] attrs)
    {
        JsonArrayRequest categoriesRequest = new JsonArrayRequest(scriptName,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DbTableValues responseVals = new DbTableValues();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                for (String attr : attrs) {
                                    String val = obj.getString(attr);
                                    responseVals.addAttributeValue(attr, val);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        mTables.put(scriptName, responseVals);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });
        return false;
    }
}
