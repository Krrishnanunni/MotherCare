package com.example.mothercare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class ViewRequestAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] requirement,stats,amount,req;
    SharedPreferences sh;

    // Constructor to receive data


    public ViewRequestAdapter(ViewRequest context, String[] requirement, String[] stats, String[] amount, String[] req) {
        super(context, R.layout.activity_view_request_adapter,req);
        this.context = context;
        this.requirement = requirement;
        this.stats = stats;
        this.amount = amount;
        this.req = req;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_view_request_adapter, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout

        TextView requirement1 = listItemView.findViewById(R.id.tv_requirement);
        TextView stats1 = listItemView.findViewById(R.id.tv_stats);
        TextView amount1 = listItemView.findViewById(R.id.tv_amount);
        TextView req1 = listItemView.findViewById(R.id.tv_req);


        requirement1.setText(requirement[position]);
        stats1.setText(stats[position]);
        amount1.setText(amount[position]);
        req1.setText(req[position]);



        return listItemView;
    }
}
