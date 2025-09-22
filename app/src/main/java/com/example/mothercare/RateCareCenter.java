package com.example.mothercare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class RateCareCenter extends AppCompatActivity implements JsonResponse {
    EditText t1;
    TextView t2;
    String rating,review;
    Button b1;
    RatingBar r1;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate_care_center);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        t1=findViewById(R.id.review);
        t2=findViewById(R.id.view_rating);
        b1=findViewById(R.id.rate);
        r1=findViewById(R.id.ratingBar);


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewRating.class));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review=t1.getText().toString();
                rating=String.valueOf(r1.getRating());


                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) RateCareCenter.this;
                String q = "/send_rating?review=" + review + "&rate=" + rating + "&login_id=" + Login.lid + "&care_center_id=" + ViewCarecenter.cid;
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
                Toast.makeText(this, "successfuly rated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            } else{
                Toast.makeText(this, "Rating failed ", Toast.LENGTH_SHORT).show();
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