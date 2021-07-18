package com.vpnmastersm.singaporevpnmaster.adapter;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vpnmastersm.singaporevpnmaster.R;
import com.vpnmastersm.singaporevpnmaster.vpnhelper.CountryPrefs;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ServerCountryAdapter extends RecyclerView.Adapter<ServerCountryAdapter.RecentViewHolder> implements Filterable {
    private final Context context;

    private List<ServerLocation> locationList;
    private final LocationClickListener locationClickListener;
    private final Dialog dialog;
    private final CountryPrefs countryPrefs;
    private List<ServerLocation> filteredLocationList;

    public ServerCountryAdapter(Context context, List<ServerLocation> locationList, LocationClickListener locationClickListener, Dialog dialog) {
        this.context = context;
        this.locationList = locationList;
        this.filteredLocationList = locationList;
        this.locationClickListener = locationClickListener;
        this.dialog = dialog;
        this.countryPrefs = new CountryPrefs(context);

    }

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loc_item, viewGroup, false);
        return new RecentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecentViewHolder viewHolder, int position) {
        final ServerLocation location = filteredLocationList.get(viewHolder.getAdapterPosition());
        viewHolder.txtLocationName.setText(location.getName());

        Glide.with(context).load(location.getCountryflag()).into(viewHolder.ivCountryFlag);

        viewHolder.itemView.setOnClickListener(v -> {

            dialog.dismiss();
            locationClickListener.onClickItemListener(viewHolder.getAdapterPosition(), location);
        });

    }



    @Override
    public int getItemCount() {
        return filteredLocationList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredLocationList = locationList;
                } else {
                    List<ServerLocation> filteredList = new ArrayList<>();
                    for (ServerLocation row : locationList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredLocationList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredLocationList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredLocationList = (ArrayList<ServerLocation>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }


    class RecentViewHolder extends RecyclerView.ViewHolder {
        private TextView txtLocationName;
        private ImageView ivCountryFlag;




        RecentViewHolder(View itemView) {
            super(itemView);
            txtLocationName = itemView.findViewById(R.id.country_name);
            ivCountryFlag = itemView.findViewById(R.id.flag);




        }
    }

    public interface LocationClickListener {
        void onClickItemListener(int position, ServerLocation location);
    }
}
