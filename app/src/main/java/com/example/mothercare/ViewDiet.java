package com.example.mothercare;

import android.annotation.SuppressLint;
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

public class ViewDiet extends AppCompatActivity implements JsonResponse {
    ListView d1;

    String[] diet,value,date;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_diet);

        d1=findViewById(R.id.diet);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewDiet.this;
        String q = "/view_diet"+"?login_id=" + Login.lid;
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
                diet= new String[length];
                date= new String[length];


                value = new String[length];

                for(int i=0; i<length;i++){
                    diet[i]=ja.getJSONObject(i).getString("plan_details");
                    date[i]=ja.getJSONObject(i).getString("date");

//                    value[i]="\ndiet: "+ diet[i] +"\ndate: "+date[i];

                }

//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
//                d1.setAdapter(ar);
                DietAdapter ar = new DietAdapter(ViewDiet.this,diet,date);
                d1.setAdapter(ar);

                Toast.makeText(this, "jjjj", Toast.LENGTH_SHORT).show();





            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }        }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}