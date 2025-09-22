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

public class SentFeedback extends AppCompatActivity implements JsonResponse {
    String feedback;
    Button b1;
    EditText t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sent_feedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t1=findViewById(R.id.feedback);
        b1=findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=t1.getText().toString();


                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) SentFeedback.this;
                String q = "/sendfeedback?feedback=" + feedback +"&login_id=" + Login.lid;
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
                Toast.makeText(this, "Feedback sented", Toast.LENGTH_SHORT).show();
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