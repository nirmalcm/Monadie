package com.spectrographix.monadie.ui.mygroups;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddMembersActivityUsersListAdapter extends RecyclerView.Adapter
{
    private List<User> groupMembers;
    private Context context;

    public AddMembersActivityUsersListAdapter(Context context, List<User> groupMembers)
    {
        this.groupMembers = groupMembers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygroups_activity_add_members_adapter_users_list, parent, false);
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

        viewHolderSub.userAddRemove.setTag(viewHolderSub.recyclerItem.getId(), groupMembers.get(position).getUserId());
        /*viewHolderSub.userAddRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolderSub.userAddRemove.isChecked())
                {
                    viewHolderSub.userAddRemove.setBackgroundResource(R.drawable.mygroups_toggle_addmember);
                }
                else
                {
                    viewHolderSub.userAddRemove.setBackgroundResource(R.drawable.mygroups_toggle_memberadded);
                }
            }
        });*/

        //in some cases, it will prevent unwanted situations
        viewHolderSub.userAddRemove.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        viewHolderSub.userAddRemove.setChecked(groupMembers.get(position).isSelected());

        viewHolderSub.userAddRemove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groupMembers.get(viewHolderSub.getAdapterPosition()).setSelected(isChecked);
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
        CheckBox userAddRemove;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            recyclerItem = (CardView) itemView.findViewById(R.id.id_recyclerItem);
            userImage = (ImageView) itemView.findViewById(R.id.id_userImage);
            userEmail = (TextView) itemView.findViewById(R.id.id_userEmail);
            userName = (TextView) itemView.findViewById(R.id.id_userName);
            userAddRemove = (CheckBox) itemView.findViewById(R.id.id_userAddRemove);
        }
    }
}