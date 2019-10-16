package com.spectrographix.monadie.ui.myauctions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Group;
import com.spectrographix.monadie.ui.mygroups.MyGroupActivity;
import com.spectrographix.monadie.utility.Utility;

import java.io.Serializable;
import java.util.List;

public class AddAuctionGroupsActivityGroupsListAdapter extends RecyclerView.Adapter {
    private List<Group> groupList;
    private Context context;

    public AddAuctionGroupsActivityGroupsListAdapter(Context context, List<Group> groupList) {
        this.groupList = groupList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.myauctions_activity_add_auction_groups_adapter_groups_list, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        //Picasso.with(context)
        //      .load(groupList.get(position).getGroupImage())
        //    .into(viewHolderSub.groupImage);

        viewHolderSub.groupImage.setTag(viewHolderSub.groupImage.getId(), groupList.get(position).getGroupId());

        viewHolderSub.groupMembersCount.setText(String.valueOf(Utility.getStringArraySize(groupList.get(position).getGroupMembers()))+" Members");
        viewHolderSub.groupMembersCount.setTag(viewHolderSub.groupMembersCount.getId(), groupList.get(position).getGroupId());

        viewHolderSub.groupName.setText(groupList.get(position).getGroupName());
        viewHolderSub.groupName.setTag(viewHolderSub.groupMembersCount.getId(), groupList.get(position).getGroupId());

        viewHolderSub.recyclerItem.setTag(viewHolderSub.recyclerItem.getId(), groupList.get(position).getGroupId());
        viewHolderSub.recyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent trendingClickIntent = new Intent(context, MyGroupActivity.class);
                //trendingClickIntent.putExtra("KEY_GROUP", (Serializable) groupList.get(position));
                //context.startActivity(trendingClickIntent);
            }
        });

        viewHolderSub.groupAddRemove.setTag(viewHolderSub.groupAddRemove.getId(), groupList.get(position).getGroupId());

        //in some cases, it will prevent unwanted situations
        viewHolderSub.groupAddRemove.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        viewHolderSub.groupAddRemove.setChecked(groupList.get(position).isSelected());

        viewHolderSub.groupAddRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groupList.get(viewHolderSub.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder {
        CardView recyclerItem;
        ImageView groupImage;
        TextView groupMembersCount;
        TextView groupName;
        CheckBox groupAddRemove;

        public ViewHolderSub(View itemView) {
            super(itemView);
            recyclerItem = (CardView) itemView.findViewById(R.id.id_recyclerItem);
            groupImage = (ImageView) itemView.findViewById(R.id.id_groupImage);
            groupMembersCount = (TextView) itemView.findViewById(R.id.id_groupMembersCount);
            groupName = (TextView) itemView.findViewById(R.id.id_groupName);
            groupAddRemove = (CheckBox) itemView.findViewById(R.id.id_groupAddRemove);
        }
    }
}