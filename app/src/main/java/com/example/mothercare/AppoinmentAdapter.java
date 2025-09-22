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

public class AppoinmentAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] dfname,dlname,dphone,demail,date,tokenn,starttime,endtime,maxtoken;
    SharedPreferences sh;

    // Constructor to receive data
    public AppoinmentAdapter(Activity context, String[] dfname, String[] dlname, String[] dphone,
                             String[] demail, String[] date, String[] tokenn,
                             String[] starttime, String[] maxtoken, String[] endtime) {
        super(context, R.layout.activity_appoinment_adapter,date); // Assumes a layout named `activity_custom_event_team_more_details`
        this.context = context;
        this.dfname = dfname;
        this.dlname= dlname;
        this.dphone= dphone;
        this.demail= demail;
        this.date = date;
        this.tokenn=tokenn;
        this.starttime=starttime;
        this.endtime=endtime;
        this.maxtoken=maxtoken;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for each list item
        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.activity_appoinment_adapter, parent, false);

        // Assuming TextViews and ImageView are in `activity_custom_event_team_more_details.xml` layout
        TextView date1 = listItemView.findViewById(R.id.tvAppointmentDate);
        TextView dfname1 = listItemView.findViewById(R.id.tvDoctorName);
//        TextView dlname1 = listItemView.findViewById(R.id.tvDoctorName);
        TextView dphone1 = listItemView.findViewById(R.id.Phone);
        TextView demail1 = listItemView.findViewById(R.id.email);
        TextView tokenn1 = listItemView.findViewById(R.id.tvTokenNumber);
        TextView starttime1 = listItemView.findViewById(R.id.tvAppointmentStartTime);
        TextView endtime1 = listItemView.findViewById(R.id.tvAppointmentEndTime);




        // Set text and image for each item based on the position
        date1.setText(date[position]);
        dfname1.setText(dfname[position]+" "+dlname[position]);
//        dlname1.setText(dlname[position]);
        dphone1.setText(dphone[position]);
        demail1.setText(demail[position]);
        tokenn1.setText(tokenn[position]+"/"+maxtoken[position]);
        starttime1.setText(starttime[position]);
        endtime1.setText(endtime[position]);




        return listItemView;
    }
}
