package com.spectrographix.monadie.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Auction;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivityLiveAuctionsAdapter extends RecyclerView.Adapter
{
    private List<Auction> liveAuctions;
    private Context context;

    public MainActivityLiveAuctionsAdapter(Context context, List<Auction> liveAuctions)
    {
        this.liveAuctions = liveAuctions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_activity_main_adapter_live_auctions, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        Picasso.with(context)
                .load(liveAuctions.get(position).getAuctionProductImage())
                .into(viewHolderSub.trendingImage);

        viewHolderSub.trendingImage.setTag(viewHolderSub.trendingImage.getId(), liveAuctions.get(position).getAuctionId());

        final int prodPrice = Integer.valueOf(liveAuctions.get(position).getBasePrice());
        //viewHolderSub.trendingPrice.setText("SR "+String.valueOf(prodPrice));
        //viewHolderSub.trendingPrice.setTag(viewHolderSub.trendingPrice.getId(), liveAuctions.get(position).getProductId());

        viewHolderSub.productName.setText(liveAuctions.get(position).getAuctionProductName()+",     SR "+String.valueOf(prodPrice));
        viewHolderSub.productName.setTag(viewHolderSub.productName.getId(), liveAuctions.get(position).getAuctionId());

        viewHolderSub.trendingCardView.setTag(viewHolderSub.trendingCardView.getId(), liveAuctions.get(position).getAuctionId());
        viewHolderSub.trendingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trendingClickIntent = new Intent(context, AuctionActivity.class);
                trendingClickIntent.putExtra("KEY_SELECTED_AUCTION",liveAuctions.get(position));
                context.startActivity(trendingClickIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return liveAuctions.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        CardView trendingCardView;
        ImageView trendingImage;
        TextView trendingPrice;
        TextView productName;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            trendingCardView = (CardView) itemView.findViewById(R.id.card_view);
            trendingImage = (ImageView) itemView.findViewById(R.id.id_productImage);
            trendingPrice = (TextView) itemView.findViewById(R.id.id_productPrice);
            productName = (TextView) itemView.findViewById(R.id.id_productName);
        }
    }
}