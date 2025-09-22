package com.example.mothercare;

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

public class ViewRating extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] rating,review,date,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1=findViewById(R.id.ratinglist);



        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewRating.this;
        String q = "/view_rating?careid=" + ViewCarecenter.cid;
        q=q.replace( " ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                int length = ja.length();
                rating = new String[length];
                review = new String[length];
                date = new String[length];




                value = new String[length];

                for(int i=0; i<length;i++){
                    rating[i]=ja.getJSONObject(i).getString("rating");
                    review[i]=ja.getJSONObject(i).getString("review");
                    date[i]=ja.getJSONObject(i).getString("date");


//                    value[i] = "\nRating:" + rating[i] + "\nReview:" + review[i] + "\nDate:" + date[i];
                }

//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                AdapterViewRating ar = new AdapterViewRating(ViewRating.this,rating,review,date);
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