package com.spectrographix.monadie.ui.myproducts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Product;
import com.spectrographix.monadie.ui.home.AuctionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyProductsActivityProductsListAdapter extends RecyclerView.Adapter
{
    private List<Product> myProductsList;
    private Context context;

    public MyProductsActivityProductsListAdapter(Context context, List<Product> myProductsList)
    {
        this.myProductsList = myProductsList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myproducts_activity_my_products_adapter_products_list, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        Picasso.with(context)
                .load(myProductsList.get(position).getProductImage())
                .into(viewHolderSub.productImage);

        viewHolderSub.productImage.setTag(viewHolderSub.productImage.getId(), myProductsList.get(position).getProductId());

        final int prodPrice = Integer.valueOf(myProductsList.get(position).getProductPrice());
        viewHolderSub.productPrice.setText("SR "+String.valueOf(prodPrice));
        viewHolderSub.productPrice.setTag(viewHolderSub.productPrice.getId(), myProductsList.get(position).getProductId());

        viewHolderSub.productName.setText(myProductsList.get(position).getProductName());
        viewHolderSub.productName.setTag(viewHolderSub.productPrice.getId(), myProductsList.get(position).getProductId());

        viewHolderSub.recyclerItem.setTag(viewHolderSub.recyclerItem.getId(), myProductsList.get(position).getProductId());
        viewHolderSub.recyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trendingClickIntent = new Intent(context, MyProductActivity.class);
                trendingClickIntent.putExtra("KEY_SELECTED_PRODUCT", myProductsList.get(position));
                context.startActivity(trendingClickIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return myProductsList.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        CardView recyclerItem;
        ImageView productImage;
        TextView productPrice;
        TextView productName;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            recyclerItem = (CardView) itemView.findViewById(R.id.card_view);
            productImage = (ImageView) itemView.findViewById(R.id.id_productImage);
            productPrice = (TextView) itemView.findViewById(R.id.id_productPrice);
            productName = (TextView) itemView.findViewById(R.id.id_productName);
        }
    }
}