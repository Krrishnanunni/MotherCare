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

public class ViewAppoinment extends AppCompatActivity  implements JsonResponse{
    ListView a1;
    String[] dfname,dlname,dphone,demail,date,tokenn,starttime,endtime,maxtoken,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_appoinment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        a1 = findViewById(R.id.appointmentlist);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewAppoinment.this;
        String q = "/view_appoinment_for_user"+"?login_id=" + Login.lid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }


    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                int length = ja.length();
                dfname= new String[length];
                dlname= new String[length];
                date= new String[length];
                starttime= new String[length];
                endtime= new String[length];
                tokenn= new String[length];
                dphone= new String[length];
                demail= new String[length];
                maxtoken= new String[length];


                value = new String[length];

                for(int i=0; i<length;i++){
                    dfname[i]=ja.getJSONObject(i).getString("first_name");
                    dlname[i]=ja.getJSONObject(i).getString("last_name");
                    date[i]=ja.getJSONObject(i).getString("date");
                    starttime[i]=ja.getJSONObject(i).getString("start_time");
                    endtime[i]=ja.getJSONObject(i).getString("end_time");
                    tokenn[i]=ja.getJSONObject(i).getString("token_no");
                    maxtoken[i]=ja.getJSONObject(i).getString("max_tokens");
                    dphone[i]=ja.getJSONObject(i).getString("phone");
                    demail[i]=ja.getJSONObject(i).getString("email");

//                    value[i]="\nDoctor name: "+ dfname[i]+" "+ dlname[i]+ "\nDate: "+date[i]+"\nStart time: "+starttime[i]+"\nEnd time: "+endtime[i]+"\nPhone: "+dphone[i]+"\nEmail: "+demail[i];
                }





                AppoinmentAdapter ar = new AppoinmentAdapter(ViewAppoinment.this,dfname,dlname,dphone,demail,date,tokenn,starttime,maxtoken,endtime);
                a1.setAdapter(ar);


            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}