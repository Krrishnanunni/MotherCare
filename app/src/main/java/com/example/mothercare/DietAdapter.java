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

public class DietAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] diet,date;
    SharedPreferences sh;

    // Constructor to receive data
    public DietAdapter(Activity context, String[] diet, String[] date) {
        super(context, R.layout.diet_item,diet); // Assumes a layout named `activity_custom_event_team_more_details`
        this.context = context;
        this.diet = diet;
        this.date = date;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.diet_item, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout
        TextView date1 = listItemView.findViewById(R.id.tvDietDate);
        TextView diet1 = listItemView.findViewById(R.id.tvDietDetails);


        // Set text and image for each item based on the position
        date1.setText(date[position]);
        diet1.setText(diet[position]);



        return listItemView;
    }
}
