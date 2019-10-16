package com.spectrographix.monadie.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Category;
import com.spectrographix.monadie.utility.RecyclerViewClickListener;

import java.util.List;

public class CategoriesActivityHorizontalAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>
{
    private RecyclerViewClickListener mListener;
    private List<Category> categoriesList;
    private Context context;

    private int selectedPosition = 0;

    public CategoriesActivityHorizontalAdapter(RecyclerViewClickListener listener, Context context, List<Category> categoriesList)
    {
        mListener = listener;
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fragment_categories_horizontal_tabs, parent, false);
        return new ViewHolderSub(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof ViewHolderSub) {
            final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

            viewHolderSub.font_circular = Typeface.createFromAsset(context.getAssets(),"fonts/circular_std_medium.otf");
            viewHolderSub.font_maven = Typeface.createFromAsset(context.getAssets(),"fonts/maven_pro_medium.ttf");
            viewHolderSub.font_Quattrocento = Typeface.createFromAsset(context.getAssets(),"fonts/quattrocento_sans_regular.ttf");

            viewHolderSub.tabName.setTag(viewHolderSub.tabName.getId(), categoriesList.get(position).getCategoryId());
            viewHolderSub.tabName.setText(categoriesList.get(position).getCategoryName());
            viewHolderSub.tabName.setTypeface(viewHolderSub.font_maven);

            viewHolderSub.tabLine.setTag(viewHolderSub.tabName.getId(), categoriesList.get(position).getCategoryId());
            if(selectedPosition==position)
                viewHolderSub.tabLine.setBackgroundColor(Color.parseColor("#3e4a66"));
            else
                viewHolderSub.tabLine.setBackgroundColor(Color.parseColor("#ffffff"));

            viewHolderSub.recyclerItem.setTag(viewHolderSub.recyclerItem.getId(), categoriesList.get(position).getCategoryId());
            viewHolderSub.recyclerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    mListener.onClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return categoriesList.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public RecyclerViewClickListener mListener;
        LinearLayout recyclerItem;

        TextView tabName;
        LinearLayout tabLine;

        Typeface font_circular;
        Typeface font_maven;
        Typeface font_Quattrocento;

        public ViewHolderSub(View itemView, RecyclerViewClickListener listener)
        {
            super(itemView);
            mListener = listener;
            recyclerItem = (LinearLayout) itemView.findViewById(R.id.testRecyclerItem);
            tabName = (TextView) itemView.findViewById(R.id.testTabText);
            tabLine = (LinearLayout) itemView.findViewById(R.id.testTabBottomLine);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}