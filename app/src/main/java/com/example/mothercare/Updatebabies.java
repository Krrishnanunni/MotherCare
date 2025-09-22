package com.example.mothercare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Updatebabies extends AppCompatActivity implements JsonResponse{

    Button Submit;
    String bname,bdob,bgender,bweight;
    EditText babyname,babydob,babygender,babyweight;

    String Sname,Sdob,Sgender,Sweight;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updatebabies);

        babydob=findViewById(R.id.bdob);
        babygender=findViewById(R.id.bgender);
        Submit=findViewById(R.id.submit);
        babyname=findViewById(R.id.bname);
        babyweight=findViewById(R.id.baby_weight);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Updatebabies.this;
        String q = "/view_a_baby?bid=" + ViewBabies.bid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdob =babydob.getText().toString();
                bgender=babygender.getText().toString();
                bname=babyname.getText().toString();
                bweight=babyweight.getText().toString();


                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Updatebabies.this;
                String p="/update_baby?bid="+ViewBabies.bid+"&name="+bname+"&dob="+bdob+"&gender="+bgender+"&weight="+bweight;
                p=p.replace( " ", "%20");
                JR.execute(p);

            }
        });

    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try{

            String status=jo.getString("status");
            String method = jo.getString("method");

            if(method.equalsIgnoreCase("view")){
                if(status.equalsIgnoreCase("success")){
                    JSONArray ja=jo.getJSONArray("data");
                    Sname=ja.getJSONObject(0).getString("name");
                    Sdob=ja.getJSONObject(0).getString("dob");
                    Sgender=ja.getJSONObject(0).getString("gender");
                    Sweight=ja.getJSONObject(0).getString("weight");


//                utype=ja.getJSONObject(0).getString("user_type");
                    babydob.setText(Sdob);
                    babygender.setText(Sgender);
                    babyname.setText(Sname);
                    babyweight.setText(Sweight);

                }
                else{
                    Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show();

                }

            }
            if(method.equalsIgnoreCase("update")){
                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ViewBabies.class));
                }

            }





        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}