package com.comp3004.goodbyeworld.tournamentmaster.view;

/**
 * Created by Shukri on 2017-10-10.
 */

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.*;
import android.view.*;
import android.content.Context;

import com.comp3004.goodbyeworld.tournamentmaster.R;

public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.OrgViewHolder> {

    ArrayList<String> orgList;

    public OrgAdapter(ArrayList<String> orgList, Context context) {
        this.orgList = orgList;
    }

    @Override
    public OrgAdapter.OrgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.org_row, parent, false);
        return new OrgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrgAdapter.OrgViewHolder holder, int index) {
        holder.image.setImageResource(R.drawable.planet);
        holder.text.setText(orgList.get(index).toString());
    }

    @Override
    public int getItemCount() {
        return orgList.size();
    }

    public static class OrgViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;

        public OrgViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_id);
            text = (TextView) itemView.findViewById(R.id.text_id);
        }
    }
}