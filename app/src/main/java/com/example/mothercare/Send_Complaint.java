package com.example.mothercare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class Send_Complaint extends AppCompatActivity implements JsonResponse{
    EditText e1,e2;
    TextView t1;
    Button b1;
    String title,des;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); 
        setContentView(R.layout.activity_send_complaint);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t1=findViewById(R.id.reply);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Baby_Complaint_Reply.class));
            }
        });
        e1=findViewById(R.id.Complaint_title);
        e2=findViewById(R.id.Complaint_Des);
        b1=findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=e1.getText().toString();
                des=e2.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Send_Complaint.this;
                String q = "/send_complaint?title=" + title + "&complaint_description=" + des + "&login_id=" + Login.lid;
                q=q.replace( " ", "%20");
                JR.execute(q);
            }
        });





    }

    @Override
    public void response(JSONObject jo) throws JSONException {

        String method=jo.getString("method");

        if(method.equalsIgnoreCase("send")){
            String status=jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "Complaint sented", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            } else{
                Toast.makeText(this, "Registration failed ", Toast.LENGTH_SHORT).show();
            }
        }
        if (method.equalsIgnoreCase("view")){
            String status=jo.getString("status");





        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}