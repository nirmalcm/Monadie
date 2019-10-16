package com.spectrographix.monadie.ui.mygroups;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGroupActivityUsersListAdapter extends RecyclerView.Adapter
{
    private List<User> groupMembers;
    private Context context;

    public MyGroupActivityUsersListAdapter(Context context, List<User> groupMembers)
    {
        this.groupMembers = groupMembers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygroups_activity_my_group_adapter_users_list, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        final ViewHolderSub viewHolderSub = (ViewHolderSub) holder;

        Picasso.with(context)
                .load(groupMembers.get(position).getUserImage())
                .into(viewHolderSub.userImage);

        viewHolderSub.userImage.setTag(viewHolderSub.userImage.getId(), groupMembers.get(position).getUserId());

        viewHolderSub.userEmail.setText(groupMembers.get(position).getUserEmail());
        viewHolderSub.userEmail.setTag(viewHolderSub.userEmail.getId(), groupMembers.get(position).getUserId());

        viewHolderSub.userName.setText(groupMembers.get(position).getUserFirstName()+" "+ groupMembers.get(position).getUserLastName());
        viewHolderSub.userName.setTag(viewHolderSub.userEmail.getId(), groupMembers.get(position).getUserId());

        viewHolderSub.userStatus.setTag(viewHolderSub.userStatus.getId(), groupMembers.get(position).getUserId());

        if (groupMembers.get(position).getUserStatus()!=null)
        {
            if (groupMembers.get(position).getUserStatus().equals("verified"))
                viewHolderSub.userStatus.setImageResource(R.drawable.tick);
            else
                viewHolderSub.userStatus.setImageResource(R.drawable.clock);
        }

        viewHolderSub.recyclerItem.setTag(viewHolderSub.recyclerItem.getId(), groupMembers.get(position).getUserId());
        viewHolderSub.recyclerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent trendingClickIntent = new Intent(context, MyGroupActivity.class);
                //trendingClickIntent.putExtra("KEY_GROUP",(Serializable)groupMembers.get(position));
                //context.startActivity(trendingClickIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return groupMembers.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        CardView recyclerItem;
        ImageView userImage;
        TextView userEmail;
        TextView userName;
        ImageView userStatus;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            recyclerItem = (CardView) itemView.findViewById(R.id.id_recyclerItem);
            userImage = (ImageView) itemView.findViewById(R.id.id_userImage);
            userEmail = (TextView) itemView.findViewById(R.id.id_userEmail);
            userName = (TextView) itemView.findViewById(R.id.id_userName);
            userStatus = (ImageView) itemView.findViewById(R.id.id_userStatus);
        }
    }
}