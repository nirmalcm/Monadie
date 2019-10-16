package com.spectrographix.monadie.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Category;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CategoriesActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener
{
    SessionManager sessionManager;

    ViewPagerAdapter mViewPagerAdapter;

    private List<Category> mCategories = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();

	public ViewPager mViewPager;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_categories);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout._common_toolbar_style_a);
        sessionManager= new SessionManager(getApplicationContext());

        mCategories = new ArrayList<>();
        mFragments = new ArrayList<>();
        mFragmentTitles = new ArrayList<>();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        populateHorizontalTabs("0");

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        Category selectedCategory = mCategories.get(position);
        populateHorizontalTabs("0");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager, List<Fragment> fragmentList, List<String> titleList) {
            super(manager);
            mFragmentList = fragmentList;
            mFragmentTitleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private ArrayList<String> getDummyMainCategories()
    {
        return new ArrayList<>();
    }

    private ArrayList<String> getDummySubCategories(final String parent_category_id)
    {
        return new ArrayList<>();
    }

    private ArrayList<String> populateHorizontalTabs(final String parent_category_id)
    {
        final ArrayList<String> subCategoryListOnSpinner = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_CATEGORIES_GET_PARENT_CATEGORIES,
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
                                mCategories.add(category);
                                mFragmentTitles.add(categoryName);
                            }

                            for (int i = 0; i< mFragmentTitles.size();i++)
                            {
                                /*CategoriesFragment categoriesFragment = new CategoriesFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("parent_category_id",mCategories.get(i).getCategoryId().toString());
                                categoriesFragment.setArguments(bundle);

                                mFragments.add(categoriesFragment);*/
                            }

                            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
                            mViewPager.setAdapter(mViewPagerAdapter);

                            mViewPager.setOffscreenPageLimit(mFragments.size());
                            tabLayout.setupWithViewPager(mViewPager,true);

                            for (int i = 0; i< mFragmentTitles.size();i++)
                            {
                                if(i==0)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__motors);
                                else if(i==1)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__real_estate);
                                else if(i==2)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__jewellery);
                                else if(i==3)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__watches);
                                else if(i==4)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__furnitures);
                                else if(i==5)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__appliances);
                                else if(i==6)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__electronics);
                                else if(i==7)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__antiques);
                                else if(i==8)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__animals);
                                else if(i==9)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__fashion);
                                else if(i==10)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__sports);
                                else if(i==11)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__medical);
                                else if(i==12)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__toys);
                                else if(i==13)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__books);
                                else if(i==14)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__fineart);
                                else if(i==15)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__pets_supply);
                                else if(i==16)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__school_supplies);
                                else if(i==17)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__beauty_and_grooming);
                                else if(i==18)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__decoration);
                                else if(i==19)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__baby);
                                else if(i==20)
                                    tabLayout.getTabAt(i).setIcon(R.drawable.__home_improvement);
                                else
                                    tabLayout.getTabAt(i).setIcon(R.drawable.groupchat);
                            }
                            // Tab ViewPager setting
                            //mViewPagerAdapter.notifyDataSetChanged();
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
}