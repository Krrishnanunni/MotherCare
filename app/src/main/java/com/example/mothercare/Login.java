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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity  implements JsonResponse{
    EditText e1,e2;
    Button b1;

    TextView SignUp;

    String uname,psw,q;

    public static String lid,utype,usern;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1=findViewById(R.id.loginbutton);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.password);
        SignUp=findViewById(R.id.signup);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname=e1.getText().toString();
                psw=e2.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Login.this;
                q="/user_login?uname="+uname+"&psw="+psw;
                q=q.replace(" ","%20");
                JR.execute(q);



            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) throws JSONException {

        try{

            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                JSONArray ja=jo.getJSONArray("data");
                lid=ja.getJSONObject(0).getString("login_id");
                utype=ja.getJSONObject(0).getString("user_type");
                usern=ja.getJSONObject(0).getString("first_name");

                if(utype.equalsIgnoreCase("user")){
                    Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
                else{
                    Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show();

                }



            }
            else{
                Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show();

            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}