package com.spectrographix.monadie.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Category;

import java.util.List;

public class CategoriesActivityVerticalAdapter extends RecyclerView.Adapter
{
    private List<Category> categoriesList;
    private Context context;

    public CategoriesActivityVerticalAdapter(Context context, List<Category> categoriesList)
    {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fragment_categories_vertical_cards, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        viewHolderSub.font_circular = Typeface.createFromAsset(context.getAssets(),"fonts/circular_std_medium.otf");
        viewHolderSub.font_maven = Typeface.createFromAsset(context.getAssets(),"fonts/maven_pro_medium.ttf");
        viewHolderSub.font_Quattrocento = Typeface.createFromAsset(context.getAssets(),"fonts/quattrocento_sans_regular.ttf");

        viewHolderSub.more.setTypeface(viewHolderSub.font_maven,Typeface.BOLD);
        viewHolderSub.categoryName.setTypeface(viewHolderSub.font_maven);

        viewHolderSub.categoryName.setTag(viewHolderSub.categoryName.getId(), categoriesList.get(position).getCategoryId());
        viewHolderSub.categoryName.setText(categoriesList.get(position).getCategoryName());

        viewHolderSub.categoryItems.setTag(viewHolderSub.categoryItems.getId(), categoriesList.get(position).getCategoryName());

        viewHolderSub.categoryItems.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));

        //viewHolderSub.adapterCategoriesFragmentProductsInsideCards = new AdapterCategoriesFragmentProductsInsideCards(context, categoriesList.get(position).getCategoryProducts());
        //viewHolderSub.adapterCategoriesFragmentProductsInsideCards.notifyDataSetChanged();

        //viewHolderSub.categoryItems.setAdapter(viewHolderSub.adapterCategoriesFragmentProductsInsideCards);
        //viewHolderSub.categoryItems.setItemAnimator(new DefaultItemAnimator());

        viewHolderSub.categoryItems.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount()
    {
        return categoriesList.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {

        //AdapterCategoriesFragmentProductsInsideCards adapterCategoriesFragmentProductsInsideCards;
        TextView categoryName;
        TextView more;
        RecyclerView categoryItems;

        Typeface font_circular;
        Typeface font_maven;
        Typeface font_Quattrocento;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            more = (TextView) itemView.findViewById(R.id.more);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryItems = (RecyclerView) itemView.findViewById(R.id.categoryItems);
        }
    }
}