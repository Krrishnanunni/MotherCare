package com.example.mothercare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class View_Services extends AppCompatActivity implements JsonResponse{
    ListView l1;
    public static String service_id;
    String[] description,type,service,ser_id,amount,starttime,endtime,value;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_services);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1=findViewById(R.id.serviceslist);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) View_Services.this;
        String q = "/view_services?careid=" + ViewCarecenter.cid;
        q=q.replace( " ", "%20");
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                service_id = ser_id[position]; // Assign the selected baby name to the static variable
                showDialog();
            }
        });

    }

    // Method to show the dialog box
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option")
                .setItems(new String[]{"Send Enquiry","Chat","Rate"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) { // Precaution selected
//                            intent = new Intent(ViewBabies.this, ViewPrecaution.class);
                            startActivity(new Intent(getApplicationContext(), SendEnquiries.class));
                        } else if (which == 1) { // Vaccination selected

                            startActivity(new Intent(getApplicationContext(), Chat.class));
                        }else if (which == 2) { // Vaccination selected

                            startActivity(new Intent(getApplicationContext(), ViewRating.class));
                        }
//                        if (intent != null) {
//                            intent.putExtra("baby_id", bid);
//                            startActivity(intent);
//                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                int length = ja.length();
                description = new String[length];
                type = new String[length];
                service = new String[length];
                amount = new String[length];
                ser_id = new String[length];
                starttime = new String[length];
                endtime = new String[length];


                value = new String[length];

                for(int i=0; i<length;i++){
                    description[i]=ja.getJSONObject(i).getString("description");
                    type[i]=ja.getJSONObject(i).getString("type");
                    service[i]=ja.getJSONObject(i).getString("service");
                    amount[i]=ja.getJSONObject(i).getString("amount");
                    ser_id[i]=ja.getJSONObject(i).getString("service_id");
                    starttime[i]=ja.getJSONObject(i).getString("start_time");
                    endtime[i]=ja.getJSONObject(i).getString("end_time");


//                    value[i] = "\nDescription:" + description[i] + "\nType:" + type[i] + "\nService:" + service[i] +"\namount:" + amount[i]+"\nserid:" + ser_id[i];

                }

                AdapterViewServices ar = new AdapterViewServices(View_Services.this,description,type,service,ser_id,amount,starttime,endtime);
                l1.setAdapter(ar);





            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}