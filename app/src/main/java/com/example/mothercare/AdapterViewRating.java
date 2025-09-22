package com.example.mothercare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class AdapterViewRating extends ArrayAdapter<String> {

    private Activity context;
    private String[] rating,review,date;
    SharedPreferences sh;

    // Constructor to receive data


    public AdapterViewRating(ViewRating context, String[] rating,String[] review,String[] date) {
        super(context, R.layout.activity_adapter_view_rating,date);
        this.context = context;
        this.rating = rating;
        this.review = review;
        this.date = date;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_adapter_view_rating, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout

        TextView date1 = listItemView.findViewById(R.id.tvDate);
        TextView rating1 = listItemView.findViewById(R.id.tvRating);
        TextView review1 = listItemView.findViewById(R.id.tvReview);

        // Set text and image for each item based on the position
        rating1.setText(rating[position]);
        review1.setText(review[position]);
        date1.setText(date[position]);




        return listItemView;
    }
}
