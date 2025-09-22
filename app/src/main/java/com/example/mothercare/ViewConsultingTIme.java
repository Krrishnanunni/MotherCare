package com.example.mothercare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewConsultingTIme extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] date,start_time,end_time,value,amount,tokenlimit,consultid,doctorid,stats;

    public static String conid,doctor_id,amt; // Static variable to hold the selected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_consulting_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1=findViewById(R.id.consultingtimelist);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewConsultingTIme.this;
        String q = "/ViewConsultingTimeForUser?did=" + ViewDoctor.did;
        q=q.replace( " ", "%20");
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conid = consultid[position];
                doctor_id = doctorid[position];
                amt = amount[position];
                showDialog();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) ViewConsultingTIme.this;
                String q = "/ViewConsultingTimecheck?conid=" + conid +"&did=" + doctor_id+ "&lid=" + Login.lid;
                q=q.replace( " ", "%20");
                JR.execute(q);

            }
        });



    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option")
                .setItems(new String[]{"Make Appointment and Pay"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) { // Precaution selected
//                            intent = new Intent(ViewBabies.this, ViewPrecaution.class);
//                            startActivity(new Intent(getApplicationContext(), ViewPrecaution.class));
                            if(stats[0].equalsIgnoreCase("something")) {
                                startActivity(new Intent(getApplicationContext(), User_payment.class));
                            } else {
                                Toast.makeText(ViewConsultingTIme.this,"You have already taken appointment in this time!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ViewConsultingTIme.class));
                            }



                        } else {
                            Toast.makeText(ViewConsultingTIme.this, "helo", Toast.LENGTH_SHORT).show();
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
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("view")) {
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("display");

                    int length = ja.length();
                    date = new String[length];
                    amount = new String[length];
                    start_time = new String[length];
                    end_time = new String[length];
                    consultid = new String[length];
                    doctorid = new String[length];
                    tokenlimit = new String[length];

                    value = new String[length];

                    for (int i = 0; i < length; i++) {
                        date[i] = ja.getJSONObject(i).getString("date");
                        amount[i] = ja.getJSONObject(i).getString("amount");
                        start_time[i] = ja.getJSONObject(i).getString("start_time");
                        end_time[i] = ja.getJSONObject(i).getString("end_time");
                        consultid[i] = ja.getJSONObject(i).getString("consulting_id");
                        doctorid[i] = ja.getJSONObject(i).getString("doctor_id");
                        tokenlimit[i] = ja.getJSONObject(i).getString("max_tokens");

                        value[i] = "\nDate: " + date[i] + "\nAmount: " + amount[i] + "\nStart Time: " + start_time[i] + "\nEnd Time: " + end_time[i]+ "\nToken limit: " + tokenlimit[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }else if (method.equalsIgnoreCase("check")) {
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("display");

                    int length = ja.length();
                    stats = new String[length];


                    value = new String[length];

                    for (int i = 0; i < length; i++) {
                        stats[i] = ja.getJSONObject(i).getString("status");


//                        value[i] = "\nDate: " + date[i] + "\nAmount: " + amount[i] + "\nStart Time: " + start_time[i] + "\nEnd Time: " + end_time[i]+ "\nToken limit: " + tokenlimit[i];
                    }

//                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);
                } else {
                    stats = new String[1];
                    stats[0]="something";
                    Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();
                }

            }

            else if (method.equalsIgnoreCase("appointment")) {
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    // Adjusted based on new backend response:
                    String message = jo.getString("message");
                    if (message.contains("exceed")) {
                        Toast.makeText(this, "You already have a pending appointment for this consultation.", Toast.LENGTH_SHORT).show();
                    } else if (message.contains("token limit reached")) {
                        Toast.makeText(this, "Token limit reached. Cannot create appointment.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}