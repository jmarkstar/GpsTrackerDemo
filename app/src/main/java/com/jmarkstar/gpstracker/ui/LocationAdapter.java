package com.jmarkstar.gpstracker.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jmarkstar.gpstracker.R;
import com.jmarkstar.gpstracker.models.LocationModel;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jmarkstar on 25/05/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationVH> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm:ss");
    private List<LocationModel> locations;

    public void addList(List<LocationModel> locations){
        this.locations = locations;
    }

    @Override public LocationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_locations_item, null, false);
        return new LocationVH(view);
    }

    @Override public void onBindViewHolder(LocationVH holder, int position) {
        LocationModel location = locations.get(position);
        holder.tvLatitude.setText(String.valueOf(location.getLatitude()));
        holder.tvLongitude.setText(String.valueOf(location.getLongitude()));
        holder.tvDate.setText(dateFormat.format(location.getDate()));
    }

    @Override public int getItemCount() {
        return locations.size();
    }

    public class LocationVH extends RecyclerView.ViewHolder{

        TextView tvLatitude;
        TextView tvLongitude;
        TextView tvDate;

        public LocationVH(View itemView) {
            super(itemView);
            tvLatitude = (TextView)itemView.findViewById(R.id.tv_latitude);
            tvLongitude = (TextView)itemView.findViewById(R.id.tv_longitude);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);
        }
    }
}
