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

public class Baby_Complaint_Reply extends AppCompatActivity implements JsonResponse {
    ListView t1;
    String[] description,title,reply,date,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_baby_complaint_reply);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        t1=findViewById(R.id.view_reply);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Baby_Complaint_Reply.this;
        String q = "/view_complaint?login_id=" + Login.lid;
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
                    description = new String[length];
                    title = new String[length];
                    date = new String[length];
                    reply = new String[length];
                    value = new String[length];

                    for(int i=0; i<length;i++){
                        description[i]=ja.getJSONObject(i).getString("description");
                        title[i]=ja.getJSONObject(i).getString("title");
                        date[i]=ja.getJSONObject(i).getString("date");
                        reply[i]=ja.getJSONObject(i).getString("reply");

//                        value[i]="\ndescription:"+ description[i]+"\ntitle:"+title[i]+"\ndate:"+date[i]+"\nreply:"+reply[i];

                    }

//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);


                AdapterBabyComplaintReply ar=new AdapterBabyComplaintReply(Baby_Complaint_Reply.this,description,title,reply,date);
                t1.setAdapter(ar);



            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}