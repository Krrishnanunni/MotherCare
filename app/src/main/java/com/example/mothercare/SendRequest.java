package com.example.mothercare;

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

public class SendRequest extends AppCompatActivity implements JsonResponse{
String Requirement;
    Button b1;
    EditText t1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t1=findViewById(R.id.request);
        b1=findViewById(R.id.submit);
        tv1=findViewById(R.id.view_proposal);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Requirement = t1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) SendRequest.this;
                String q = "/send_request?Requirement=" + Requirement+"&login_id=" + Login.lid+"&careid="+ViewCarecenter.cid;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewRequest.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        String method=jo.getString("method");

        if(method.equalsIgnoreCase("send")){
            String status=jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(this, "Request Sented", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            } else{
                Toast.makeText(this, " failed ", Toast.LENGTH_SHORT).show();
            }
        }
        if (method.equalsIgnoreCase("view")) {
            String status = jo.getString("status");




        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}