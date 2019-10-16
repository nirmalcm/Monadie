package com.spectrographix.monadie.ui.mygroups;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.spectrographix.monadie.R;
import com.spectrographix.monadie.classes.Group;
import com.spectrographix.monadie.classes.Url;
import com.spectrographix.monadie.ui.home.MainActivity;
import com.spectrographix.monadie.ui.myauctions.CreateAuctionOneActivity;
import com.spectrographix.monadie.ui.myauctions.CreateAuctionTwoActivity;
import com.spectrographix.monadie.utility.AppController;
import com.spectrographix.monadie.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyGroupsActivityGroupListAdapter extends RecyclerView.Adapter
{
    private List<Group> groupList;
    private Context context;

    public MyGroupsActivityGroupListAdapter(Context context, List<Group> groupList)
    {
        this.groupList = groupList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygroups_activity_my_groups_adapter_groups_list, parent, false);
        return new ViewHolderSub(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
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
                Intent trendingClickIntent = new Intent(context, MyGroupActivity.class);
                trendingClickIntent.putExtra("KEY_GROUP",(Serializable) groupList.get(position));
                context.startActivity(trendingClickIntent);
            }
        });

        viewHolderSub.groupMenu.setTag(viewHolderSub.groupMenu.getId(), groupList.get(position).getGroupId());
        viewHolderSub.groupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v,position);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return groupList.size();
    }

    public class ViewHolderSub extends RecyclerView.ViewHolder
    {
        CardView recyclerItem;
        ImageView groupImage;
        TextView groupMembersCount;
        TextView groupName;
        ImageView groupMenu;

        public ViewHolderSub(View itemView)
        {
            super(itemView);
            recyclerItem = (CardView) itemView.findViewById(R.id.id_recyclerItem);
            groupImage = (ImageView) itemView.findViewById(R.id.id_groupImage);
            groupMembersCount = (TextView) itemView.findViewById(R.id.id_groupMembersCount);
            groupName = (TextView) itemView.findViewById(R.id.id_groupName);
            groupMenu = (ImageView) itemView.findViewById(R.id.id_groupMenu);
        }
    }

    private void showPopupMenu(View view,int position) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mygroups_activity_groups_adapter_groups_list_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(view,position));
        popup.show();
    }

    private void deleteGroupUsingVolley(final String groupId)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.MONGO_GROUPS_DELETE_GROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (obj.getString("status").equals("success")) {
                                Toast.makeText(context, "Group deleted !", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Operation Failed !", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(context, "Operation Failed !", Toast.LENGTH_SHORT).show();
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
                params.put("group_id", groupId);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        View view;
        int position;

        public MyMenuItemClickListener(View view,int position) {
            this.view =view;
            this.position =position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.id_menu_deleteGroup:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm delete ?");
                    //builder.setMessage("You can add this product to auction later !");
                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String selectedGroupId = groupList.get(position).getGroupId();
                            deleteGroupUsingVolley(selectedGroupId);
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    return true;
                case R.id.id_menu_editGroup:
                    Toast.makeText(context, "Feature not available !", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}