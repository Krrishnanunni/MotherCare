package com.example.mothercare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class AdapterBabyComplaintReply extends ArrayAdapter<String> {

    private Activity context;
    private String[] description,title,reply,date;
    SharedPreferences sh;

    // Constructor to receive data


    public AdapterBabyComplaintReply(Baby_Complaint_Reply context, String[] description,String[] title,String[] reply,String[] date ) {
        super(context, R.layout.activity_adapter_baby_complaint_reply,description);
        this.context = context;
        this.description = description;
        this.title = title;
        this.reply = reply;
        this.date = date;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_adapter_baby_complaint_reply, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout
        TextView description1 = listItemView.findViewById(R.id.tvDescription);
        TextView title1 = listItemView.findViewById(R.id.tvTitle);
        TextView reply1 = listItemView.findViewById(R.id.tvReply);
        TextView date1 = listItemView.findViewById(R.id.tvDate);


        // Set text and image for each item based on the position
        description1.setText(description[position]);
        title1.setText(title[position]);
        reply1.setText(reply[position]);
        date1.setText(date[position]);




        return listItemView;
    }
}
