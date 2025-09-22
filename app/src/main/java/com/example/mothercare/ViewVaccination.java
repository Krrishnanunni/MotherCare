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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ViewVaccination extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] vaccinationName,details,date,stat,value,vid;
    public static String vacid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_vaccination);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1=findViewById(R.id.vaccination);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewVaccination.this;
        String q = "/view_vaccination?login_id=" + Login.lid+"&babyid="+ViewBabies.bid;
        q=q.replace( " ", "%20");
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vacid = vid[position]; // Assign the selected baby name to the static variable
                showDialog();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option")
                .setItems(new String[]{"Mark as Completed"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) { // Precaution selected
//                            intent = new Intent(ViewBabies.this, ViewPrecaution.class);
//                            startActivity(new Intent(getApplicationContext(), ViewPrecaution.class));

                            JsonReq JR=new JsonReq();
                            JR.json_response=(JsonResponse) ViewVaccination.this;
                            String q = "/vaccinationupdate?vacid="+vacid;
                            q=q.replace( " ", "%20");
                            JR.execute(q);



                        } else if (which == 1) { // Vaccination selected

//                            startActivity(new Intent(getApplicationContext(), ViewVaccination.class));
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
                    vaccinationName = new String[length];
                    details = new String[length];
                    date = new String[length];
                    stat = new String[length];
                    value = new String[length];
                    vid = new String[length];


                    for (int i = 0; i < length; i++) {
                        vaccinationName[i] = ja.getJSONObject(i).getString("vaccination_name");
                        date[i] = ja.getJSONObject(i).getString("date");
                        stat[i] = ja.getJSONObject(i).getString("status");
                        vid[i] = ja.getJSONObject(i).getString("vaccination_id");

                        value[i] = "\nVaccination Name:" + vaccinationName[i] + "\ndate:" + date[i] + "\nstatus:" + stat[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);


                }
            }else if(method.equalsIgnoreCase("update")){
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}