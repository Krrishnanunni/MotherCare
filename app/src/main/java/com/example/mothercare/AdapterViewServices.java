package com.example.mothercare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class AdapterViewServices extends ArrayAdapter<String> {

    private Activity context;
    private String[] description,type,service,ser_id,amount,starttime,endtime;
    SharedPreferences sh;

    // Constructor to receive data


    public AdapterViewServices(View_Services context, String[] description, String[] type, String[] service, String[] serId, String[] amount, String[] starttime, String[] endtime) {
        super(context, R.layout.activity_adapter_view_services,starttime);
        this.context = context;
        this.description = description;
        this.type = type;
        this.service = service;
        this.ser_id = serId;
        this.amount = amount;
        this.starttime = starttime;
        this.endtime = endtime;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_adapter_view_services, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout
        TextView service1 = listItemView.findViewById(R.id.tvServiceName);
        TextView type1 = listItemView.findViewById(R.id.tvServiceType);
        TextView description1 = listItemView.findViewById(R.id.tvServiceDescription);
        TextView starttime1 = listItemView.findViewById(R.id.tvStartTime);
        TextView endtime1 = listItemView.findViewById(R.id.tvEndTime);
        TextView amount1 = listItemView.findViewById(R.id.tvAmount);






        // Set text and image for each item based on the position
        service1.setText(service[position]);
        type1.setText(type[position]);
        description1.setText(description[position]);
        amount1.setText(amount[position]);
        starttime1.setText(starttime[position]);
        endtime1.setText(endtime[position]);




        return listItemView;
    }
}
