package com.example.mothercare;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class AdapterViewCarecenter extends ArrayAdapter<String> {

    private Activity context;
    private String[] carecentername,place,email,phone,landmark,careid,carename;
    SharedPreferences sh;

    // Constructor to receive data


    public AdapterViewCarecenter(ViewCarecenter context, String[]carecentername, String[]place, String[]email, String[]phone, String[]landmark, String[]careid, String[]carename) {
        super(context, R.layout.activity_adapter_view_carecenter,place);
        this.context = context;
        this.carecentername = carecentername;
        this.place = place;
        this.email = email;
        this.phone = phone;
        this.landmark = landmark;
        this.careid = careid;
        this.carename = carename;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_adapter_view_carecenter, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout

        TextView carecentername1 = listItemView.findViewById(R.id.textView_carecentername);
        TextView place1 = listItemView.findViewById(R.id.textView_place);
        TextView email1 = listItemView.findViewById(R.id.textView_email);
        TextView phone1 = listItemView.findViewById(R.id.textView_phone);
        TextView landmark1 = listItemView.findViewById(R.id.textView_landmark);
        TextView careid1 = listItemView.findViewById(R.id.textView_careid);
        TextView carename1 = listItemView.findViewById(R.id.textView_carename);
        // Set the data for each TextView
        carecentername1.setText(carecentername[position]);
        place1.setText(place[position]);
        email1.setText(email[position]);
        phone1.setText(phone[position]);
        landmark1.setText(landmark[position]);
        careid1.setText(careid[position]);
        carename1.setText(carename[position]);



        return listItemView;
    }
}
