package com.example.mothercare;

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

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity implements JsonResponse{
    Button Register;
    EditText Fname,Lname,mail,Ph,Addrs,Dofb,usename,pswd;


    String uname,psw,fn,ln,phn,em,add,dateofbirth,q;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Register=findViewById(R.id.register);

//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Login.class));
//            }
//        });

        Fname=findViewById(R.id.Firstname);
        Lname=findViewById(R.id.Lastname);
        Ph=findViewById(R.id.Phone);
        mail=findViewById(R.id.Email);
        Addrs=findViewById(R.id.Address);
        Dofb=findViewById(R.id.Dob);
        usename=findViewById(R.id.Username);
        pswd=findViewById(R.id.Password);


    Register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uname=usename.getText().toString();
            psw=pswd.getText().toString();
            fn=Fname.getText().toString();
            ln=Lname.getText().toString();
            phn=Ph.getText().toString();
            em=mail.getText().toString();
            add=Addrs.getText().toString();
            dateofbirth=Dofb.getText().toString();

            if (uname.equalsIgnoreCase("")) {
                usename.setError("Username can't be empty");
                usename.setFocusable(true);
            } else if (psw.equalsIgnoreCase("")) {
                pswd.setError("Password can't be empty");
                pswd.setFocusable(true);
            } else if (fn.equalsIgnoreCase("")) {
                Fname.setError("First name can't be empty");
                Fname.setFocusable(true);
            } else if (ln.equalsIgnoreCase("")) {
                Lname.setError("Last name can't be empty");
                Lname.setFocusable(true);
            } else if (phn.equalsIgnoreCase("")) {
                Ph.setError("Phone number can't be empty");
                Ph.setFocusable(true);
            } else if (em.equalsIgnoreCase("")) {
                mail.setError("Email can't be empty");
                mail.setFocusable(true);
            } else if (add.equalsIgnoreCase("")) {
                Addrs.setError("Address can't be empty");
                Addrs.setFocusable(true);
            } else if (dateofbirth.equalsIgnoreCase("")) {
                Dofb.setError("Date of birth can't be empty");
                Dofb.setFocusable(true);
            } else {
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Registration.this;
                q = "/user_register?uname=" + uname + "&psw=" + psw + "&fn=" + fn + "&ln=" + ln + "&phn=" + phn + "&em=" + em + "&add=" + add + "&dob=" + dateofbirth;
                q = q.replace(" ", "%20");
                JR.execute(q);


                System.out.println("Username: " + uname);
                System.out.println("Password: " + psw);
                System.out.println("First Name: " + fn);
                System.out.println("Last Name: " + ln);
                System.out.println("Phone: " + phn);
                System.out.println("Email: " + em);
                System.out.println("Address: " + add);
                System.out.println("DOB: " + dateofbirth);
            }



        }
    });


    }


    @Override
    public void response(JSONObject jo) throws JSONException {

        String status=jo.getString("status");

        if(status.equalsIgnoreCase("success")){
            Toast.makeText(this, "Registration successfully completed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        else{
            Toast.makeText(this, "Registration failed ", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}