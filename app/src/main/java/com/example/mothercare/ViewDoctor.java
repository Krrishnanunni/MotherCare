package com.example.mothercare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ViewDoctor extends AppCompatActivity implements JsonResponse {
    RecyclerView recyclerView;
    List<Doctor> doctorList;
    public static String did, dloginid, doctorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorList = new ArrayList<>();

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewDoctor.this;
        String q = "/view_doctorforuser";
        JR.execute(q);
    }

    private void showDialog(Doctor doctor) {
        did = doctor.getId();
        dloginid = doctor.getLoginId();
        doctorname = doctor.getName();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Define options and corresponding icons
        String[] options = {"Consulting Time", "Chat with Doctor"};
        int[] icons = {R.drawable.ic_consulting, R.drawable.ic_chat};

        // Custom ArrayAdapter with icons
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);

                // Set text styling
                textView.setTextSize(18);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(20, 20, 20, 20);

                // Add icons dynamically
                Drawable icon = ContextCompat.getDrawable(getContext(), icons[position]);
                if (icon != null) {
                    icon.setBounds(0, 0, 60, 60);
                    textView.setCompoundDrawables(icon, null, null, null);
                    textView.setCompoundDrawablePadding(20);
                }

                return view;
            }
        };

        // Create and show the dialog
        builder.setTitle("Choose an Option")
                .setAdapter(adapter, (dialog, which) -> {
                    Intent intent = null;
                    switch (which) {
                        case 0:
                            intent = new Intent(getApplicationContext(), ViewConsultingTIme.class);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(), ChatforDoc.class);
                            break;
                    }
                    if (intent != null) startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            if (jo.getString("status").equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject obj = ja.getJSONObject(i);
                    doctorList.add(new Doctor(
                            obj.getString("doctor_id"),
                            obj.getString("first_name") + " " + obj.getString("last_name"),
                            obj.getString("email"),
                            obj.getString("phone"),
                            obj.getString("qualification"),
                            obj.getString("login_id")
                    ));
                }
                recyclerView.setAdapter(new DoctorAdapter(this, doctorList, this::showDialog));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
