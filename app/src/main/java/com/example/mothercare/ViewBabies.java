package com.example.mothercare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewBabies extends AppCompatActivity implements JsonResponse {
    RecyclerView recyclerView;
    List<BabyModel> babyList;
    Button button6;

    public static String bid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_babies);

        recyclerView = findViewById(R.id.recyclerView);
        button6 = findViewById(R.id.button6);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        babyList = new ArrayList<>();

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewBabies.this;
        String q = "/view_babies?login_id=" + Login.lid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        button6.setOnClickListener(v -> startActivity(new Intent(this, AddBabies.class)));
    }


    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("view") && status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                for (int i = 0; i < ja.length(); i++) {
                    String name = ja.getJSONObject(i).getString("name");
                    String dob = ja.getJSONObject(i).getString("dob");
                    String gender = ja.getJSONObject(i).getString("gender");
                    String babyId = ja.getJSONObject(i).getString("baby_id");
                    String weight = ja.getJSONObject(i).getString("weight");

                    DateTimeFormatter formatter = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    }
                    LocalDate birthDate = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        birthDate = LocalDate.parse(dob, formatter);
                    }
                    Period age = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        age = Period.between(birthDate, LocalDate.now());
                    }
                    String ageStr = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        ageStr = age.getYears() + " years, " + age.getMonths() + " months";
                    }

                    babyList.add(new BabyModel(name, ageStr, gender, weight, babyId));
                }

                recyclerView.setAdapter(new BabyAdapter(this, babyList));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

//    public void showDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("Choose an Option")
//                .setItems(new String[]{"Precaution", "Vaccination", "Edit", "Delete"}, (dialog, which) -> {
//                    if (which == 0) startActivity(new Intent(this, ViewPrecaution.class));
//                    else if (which == 1) startActivity(new Intent(this, ViewVaccination.class));
//                    else if (which == 2) startActivity(new Intent(this, Updatebabies.class));
//                    else if (which == 3) {
//                        JsonReq JR = new JsonReq();
//                        JR.json_response = this;
//                        JR.execute("/deletebaby?babyid=" + bid);
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .show();
//    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");

        String[] options = {"Precaution", "Vaccination", "Edit", "Delete"};
        int[] icons = {R.drawable.ic_precaution, R.drawable.ic_vaccine, R.drawable.ic_edit, R.drawable.ic_delete};

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(18);
                textView.setTextColor(Color.BLACK);
                textView.setCompoundDrawablesWithIntrinsicBounds(icons[position], 0, 0, 0);
                textView.setCompoundDrawablePadding(20);
                return view;
            }
        };

        builder.setAdapter(adapter, (dialog, which) -> {
            if (which == 0) startActivity(new Intent(this, ViewPrecaution.class));
            else if (which == 1) startActivity(new Intent(this, ViewVaccination.class));
            else if (which == 2) startActivity(new Intent(this, Updatebabies.class));
            else if (which == 3) {
                JsonReq JR = new JsonReq();
                JR.json_response = this;
                JR.execute("/deletebaby?babyid=" + bid);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
