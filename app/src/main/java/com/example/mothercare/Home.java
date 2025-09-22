package com.example.mothercare;

import static com.example.mothercare.Login.usern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity implements JsonResponse{
    MaterialCardView profile,Baby,Carecenter,Doctor,Complaint,Feedback,Diet,appoinment,logout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        TextView txtUserName = findViewById(R.id.txtUserName);

        txtUserName.setText(usern);

        profile=findViewById(R.id.Profile);
        Baby=findViewById(R.id.BabyDetails);
        Doctor=findViewById(R.id.Doctor);
        Diet=findViewById(R.id.diet);
        Carecenter=findViewById(R.id.Carecenter);
        Complaint=findViewById(R.id.Complaint);
        Feedback=findViewById(R.id.Feedback);
        appoinment=findViewById(R.id.viewappointment);
        logout=findViewById(R.id.logout);
        profile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           startActivity(new Intent(getApplicationContext(),Profile.class));
        }

        });
        Baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewBabies.class));
            }


        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }


        });
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewDoctor.class));
            }


        });
        Carecenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewCarecenter.class));
            }


        });
        Diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewDiet.class));
            }


        });
        Complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Send_Complaint.class));
            }


        });
        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SentFeedback.class));
            }


        });
        appoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewAppoinment.class));
            }


        });




    }

    @Override
    public void response(JSONObject jo) throws JSONException {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}