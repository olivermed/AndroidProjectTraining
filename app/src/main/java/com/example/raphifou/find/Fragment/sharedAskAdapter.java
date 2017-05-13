package com.example.raphifou.find.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raphifou.find.MapsActivity;
import com.example.raphifou.find.R;
import com.example.raphifou.find.Retrofit.FireBaseObject;

import java.util.List;

/**
 * Created by oliviermedec on 13/05/2017.
 */

public class sharedAskAdapter extends RecyclerView.Adapter<sharedAskAdapter.ViewHolder> {
    private List<FireBaseObject> fireBaseObjects;
    public Context context;

    public sharedAskAdapter(Context context, List<FireBaseObject> fireBaseObjects) {
        this.fireBaseObjects = fireBaseObjects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_msg_req, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtTitle.setText(fireBaseObjects.get(position).data.loginUser);
        String desc = "Position: " + fireBaseObjects.get(position).data.latitude + " :: " + fireBaseObjects.get(position).data.longitude;
        holder.txtDesc.setText(desc);
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(context, MapsActivity.class);
                mapIntent.putExtra(context.getString(R.string.latitude), fireBaseObjects.get(position).data.latitude);
                mapIntent.putExtra(context.getString(R.string.longitude), fireBaseObjects.get(position).data.longitude);
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fireBaseObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDesc;
        public Button btnShow;
        public ViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtDesc = (TextView)itemView.findViewById(R.id.txtDesc);
            btnShow = (Button)itemView.findViewById(R.id.btnShow);
        }
    }
}
