package com.spectrographix.monadie.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Category;
import com.spectrographix.monadie.classes.Product;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.RecyclerViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CategoriesActivityAvailableFragment extends Fragment {

    View mRootView;
    String parent_category_id_horizontal_tabs = "0";

    private RecyclerView horizontalTabsRecycler;
    private CategoriesActivityHorizontalAdapter horizontalTabsAdapter;
    ArrayList<Category> horizontalTabsList;

    private RecyclerView verticalCardsRecycler;
    private CategoriesActivityVerticalAdapter verticalCardsAdapter;
    ArrayList<Category> verticalCardsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /*@Override
    public void onClick(View view, int position)
    {
        initiateVerticalCardsRecycler();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_activity_categories_fragment_available,container, false);
        mRootView = view;

        horizontalTabsRecycler = (RecyclerView) view.findViewById(R.id.testRecyclerHorizontalTabs);
        verticalCardsRecycler = (RecyclerView) view.findViewById(R.id.testRecyclerVerticalCards);

        horizontalTabsList = new ArrayList<>();
        verticalCardsList = new ArrayList<>();

        parent_category_id_horizontal_tabs = getArguments().getString("parent_category_id");

        initiateHorizontalTabsRecycler();
        initiateVerticalCardsRecycler();

        populateHorizontalTabs(parent_category_id_horizontal_tabs);

        return view;

    }

    private void initiateHorizontalTabsRecycler()
    {
        horizontalTabsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Category selectedHorizontalTab = horizontalTabsList.get(position);
                populateVerticalCards(selectedHorizontalTab.getCategoryId());
            }
        };

        horizontalTabsAdapter = new CategoriesActivityHorizontalAdapter(listener,getActivity(), horizontalTabsList);
        horizontalTabsAdapter.notifyDataSetChanged();

        horizontalTabsRecycler.setAdapter(horizontalTabsAdapter);
        horizontalTabsRecycler.setItemAnimator(new DefaultItemAnimator());

        horizontalTabsRecycler.setNestedScrollingEnabled(false);
    }

    private void initiateVerticalCardsRecycler()
    {
        verticalCardsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));

        //verticalCardsAdapter = new CategoriesActivityVerticalAdapter(getActivity(), verticalCardsList);
        verticalCardsAdapter.notifyDataSetChanged();

        verticalCardsRecycler.setAdapter(verticalCardsAdapter);
        verticalCardsRecycler.setItemAnimator(new DefaultItemAnimator());

        verticalCardsRecycler.setNestedScrollingEnabled(false);
    }

    private ArrayList<String> populateHorizontalTabs(final String parent_category_id)
    {
        horizontalTabsList.clear();

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
                                horizontalTabsList.add(category);
                            }
                            horizontalTabsAdapter.notifyDataSetChanged();

                            Category selectedHorizontalTab = horizontalTabsList.get(0);
                            populateVerticalCards(selectedHorizontalTab.getCategoryId());

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
                params.put("parent_id", String.valueOf(parent_category_id));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);

        return subCategoryListOnSpinner;
    }

    private List<Category> populateVerticalCards(final String parent_category_id)
    {
        verticalCardsList.clear();

        //if everything is fine
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

                                //Category category = new Category(categoryId,categoryName,categoryImage,categoryParent,getProductsListInsideCategorySub(categoryId));
                                //verticalCardsList.add(category);

                            }
                            verticalCardsAdapter.notifyDataSetChanged();
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
                params.put("parent_id",String.valueOf(parent_category_id));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
        return verticalCardsList;
    }

    private List<Product> getProductsListInsideCategorySub(final String category_id)
    {
        final ArrayList<Product> productsInsideCategorySub = new ArrayList<>();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_PRODUCTS_GET_PRODUCTS_BY_CATEGORY_ID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject idObject = obj.getJSONObject("_id");

                                String productId = idObject.getString("$oid");
                                String productName = obj.getString("product_name");
                                String productImage = obj.getString("image");
                                String productDescription = obj.getString("description");
                                String productPrice = obj.getString("price");
                                String productOwner = obj.getString("product_owner");
                                String productCategoryId = obj.getString("category_id");

                                //Product product = new Product(productId,productName,productImage,productDescription,productPrice,productOwner,productCategoryId);
                                //productsInsideCategorySub.add(product);
                            }
                            verticalCardsAdapter.notifyDataSetChanged();
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
                params.put("category_id", category_id);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
        return productsInsideCategorySub;
    }
}