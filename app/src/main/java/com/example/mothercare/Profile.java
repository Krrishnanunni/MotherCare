package com.example.mothercare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements JsonResponse {


    EditText e1,e2,e3,e4,e5,e6;
    String firstname, lastname, email, phone, address, dob, value;
    String fname, lname, maile, phno, adrs, dateofbirth;
    AppCompatButton update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        e1=findViewById(R.id.firstname);
        e2=findViewById(R.id.lastname);
        e3=findViewById(R.id.email);
        e4=findViewById(R.id.phone);
        e5=findViewById(R.id.address);
        e6=findViewById(R.id.dob);
        update=findViewById(R.id.button3);







        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Profile.this;
        String q = "/user_view_profile?log_id=" + Login.lid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        Toast.makeText(this, "///////////////////////////RR/", Toast.LENGTH_SHORT).show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                maile=e3.getText().toString();
                phno=e4.getText().toString();
                adrs=e5.getText().toString();
                dateofbirth=e6.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Profile.this;
                String p="/user_profile_update?log_id="+Login.lid+"&fname="+fname+"&lname="+lname+"&maile="+maile+"&phno="+phno+"&adrs="+adrs+"&dateofbirth="+dateofbirth;
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
                    firstname=ja.getJSONObject(0).getString("first_name");
                    lastname=ja.getJSONObject(0).getString("last_name");
                    email=ja.getJSONObject(0).getString("email");
                    phone=ja.getJSONObject(0).getString("phone");
                    address=ja.getJSONObject(0).getString("address");
                    dob=ja.getJSONObject(0).getString("dob");


//                utype=ja.getJSONObject(0).getString("user_type");
                    e1.setText(firstname);
                    e2.setText(lastname);
                    e3.setText(email);
                    e4.setText(phone);
                    e5.setText(address);
                    e6.setText(dob);





                }
                else{
                    Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show();

                }

            }
            if(method.equalsIgnoreCase("update")){
                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Home.class));
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