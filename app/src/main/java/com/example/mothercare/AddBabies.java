package com.example.mothercare;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class AddBabies extends AppCompatActivity implements JsonResponse{
    Button b1;
    EditText babyname;
    EditText Dob;
    String gender;
    EditText weight;
    EditText e1;
    String name,dob,kg;
    DatePickerDialog datePickerDialog;
    RadioButton r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_babies);

        babyname = findViewById(R.id.babyname);
        r1=findViewById(R.id.radioMale);
        r2=findViewById(R.id.radioFemale);
        e1=(EditText)  findViewById(R.id.editTextText2);
        weight=findViewById(R.id.weight);
        b1=findViewById(R.id.button5);



        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddBabies.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Format day and month to always have two digits
                                @SuppressLint("DefaultLocale") String formattedDay = String.format("%02d", dayOfMonth);
                                @SuppressLint("DefaultLocale") String formattedMonth = String.format("%02d", (monthOfYear + 1)); // Month is zero-based

                                // Set formatted date in EditText
                                e1.setText(formattedDay + "/" + formattedMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();


            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=babyname.getText().toString();
                dob=e1.getText().toString();
                if (r1.isChecked()) {

                    gender="male";
                }
                if (r2.isChecked()) {

                    gender="female";
                }
                kg=weight.getText().toString();
                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) AddBabies.this;
                String q = "/addbaby?name=" + name + "&dob=" + dob + "&gender=" + gender + "&login_id=" + Login.lid+"&weight=" + kg;

                q=q.replace( " ", "%20");
                JR.execute(q);
            }
        });
            }


    @Override
    public void response(JSONObject jo) throws JSONException {
        String method = jo.getString("method");
        if (method.equalsIgnoreCase("addbaby")) {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "Baby added", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), Home.class));
            } else {
                Toast.makeText(this, " failed ", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}