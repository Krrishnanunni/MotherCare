package com.example.mothercare;






import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Newchat extends Activity  implements JsonResponse
{
    EditText ed1;
    ImageView b1,b2;
    String chat;
    ListView l1;
    TextView t1;



    String url="";

    String[] s_id;
        String[] r_id;
        String[] message;
        String[] date;
    String[] time;


    SharedPreferences sh;


    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchat);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "");
        ed1=(EditText)findViewById(R.id.editText1);
        l1=(ListView)findViewById(R.id.listView1);
        b1=(ImageView)findViewById(R.id.imageView1);
        b2=(ImageView)findViewById(R.id.back);
        t1=(TextView)findViewById(R.id.teacher_name);
        t1.setText(ViewCarecenter.centername);

        //Toast.makeText(getApplicationContext(), "hii1", Toast.LENGTH_SHORT).show();

        startTimer();
        getChats();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewCarecenter.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                chat=ed1.getText().toString();
                if(chat.equalsIgnoreCase(""))
                {
                    ed1.setError("Empty Message ");
                    ed1.setFocusable(true);
                }
                else
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Newchat.this;
                    String q = "/chat?chat=" + chat + "&loginid=" + Login.lid + "&id=" + ViewCarecenter.cloginid;
                    q=q.replace(" ","%20");
                    JR.execute(q);



                }
            }
        });
    }

    void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 30000);
    }

    void initializeTimerTask() {
        timerTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        getChats();
                    }
                });
            }
        };
    }

    void getChats() {


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Newchat.this;
        String q = "/viewchat?logid=" +Login.lid + "&id=" + ViewCarecenter.cloginid;
        q=q.replace(" ","%20");
//	        Toast.makeText(getApplicationContext(),q, Toast.LENGTH_SHORT).show();
        JR.execute(q);



    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("view")){
                String status=jo.getString("status");
                Log.d("pearl",status);
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    s_id= new String[ja1.length()];
                    r_id=new String[ja1.length()];
                    message=new String[ja1.length()];
                    date=new String[ja1.length()];
                    time=new String[ja1.length()];

//					 val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        s_id[i]=ja1.getJSONObject(i).getString("sender_id");
                        r_id[i]=ja1.getJSONObject(i).getString("receiver_id");
                        message[i]=ja1.getJSONObject(i).getString("message");
                        date[i] = ja1.getJSONObject(i).getString("date_time");
                        time[i] =  ja1.getJSONObject(i).getString("time");


                    }

                    l1.setAdapter(new Customchat(this,message,s_id,time));



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if(method.equalsIgnoreCase("done")){

                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Newchat.class));


                }

            }


        }catch(Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();


        startActivity(new Intent(getApplicationContext(), ViewCarecenter.class));
    }

}

