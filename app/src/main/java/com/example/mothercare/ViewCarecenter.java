package com.example.mothercare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewCarecenter extends AppCompatActivity implements JsonResponse{
    ListView l1;
    EditText e1;
    Button b1,b2;
    String searcontent,min,max;



    String[] carecentername,place,email,phone,landmark,value,careid,carename,clid;
    public static String cid,cloginid,centername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_carecenter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        l1=findViewById(R.id.carecenterlist);
        e1=findViewById(R.id.search);
        b1=findViewById(R.id.filter);
        b2=findViewById(R.id.srch);

        EditText minPrice = findViewById(R.id.min_price);
        EditText maxPrice = findViewById(R.id.max_price);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility
                if (minPrice.getVisibility() == View.GONE && maxPrice.getVisibility() == View.GONE) {
                    minPrice.setVisibility(View.VISIBLE);
                    maxPrice.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.VISIBLE);
                } else {
                    minPrice.setVisibility(View.GONE);
                    maxPrice.setVisibility(View.GONE);
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                min=minPrice.getText().toString();
                max=maxPrice.getText().toString();
                searcontent=e1.getText().toString();

                JsonReq jr = new JsonReq();
                jr.json_response= (JsonResponse) ViewCarecenter.this;
                String q = "/user_filter?min="+min+"&max="+max+"&search="+searcontent;
                jr.execute(q);


            }
        });

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                JsonReq jr = new JsonReq();
                jr.json_response= (JsonResponse) ViewCarecenter.this;
                String q = "/user_view_carecenter";
                jr.execute(q);


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                searcontent=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) ViewCarecenter.this;
                String q = "/Search_care?search="+searcontent;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });





        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewCarecenter.this;
        String q = "/view_carecenterforuser";
        q = q.replace(" ", "%20");
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cid = careid[position];
                cloginid = clid[position];
                centername = carename[position];
                // Assign the selected baby name to the static variable
                showDialog();
            }
        });

    }
    ////
// Method to show the dialog box
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option")
                .setItems(new String[]{"Show services","Chat","Rate","Request"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) { // Precaution selected
//                            intent = new Intent(ViewBabies.this, ViewPrecaution.class);
                            startActivity(new Intent(getApplicationContext(), View_Services.class));
                        } else if (which == 1) { // Vaccination selected

                            startActivity(new Intent(getApplicationContext(), Newchat.class));
                        }else if (which == 2) { // Vaccination selected

                            startActivity(new Intent(getApplicationContext(), RateCareCenter.class));
                        } else if (which==3) {
                            startActivity(new Intent(getApplicationContext(), SendRequest.class));

                        }
//                        if (intent != null) {
//                            intent.putExtra("baby_id", bid);
//                            startActivity(intent);
//                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                int length = ja.length();

                carecentername = new String[length];
                place = new String[length];
                email = new String[length];
                phone = new String[length];
                landmark = new String[length];
                careid = new String[length];
                clid = new String[length];
                carename = new String[length];
                value = new String[length];

                for (int i = 0; i < length; i++) {
                    carecentername[i] = ja.getJSONObject(i).getString("CareCentreName");
                    place[i] = ja.getJSONObject(i).getString("Place");
                    phone[i] = ja.getJSONObject(i).getString("phone");
                    email[i] = ja.getJSONObject(i).getString("Email");
                    careid[i] = ja.getJSONObject(i).getString("care_center_id");
                    landmark[i] = ja.getJSONObject(i).getString("Landmark");
                    carename[i] = ja.getJSONObject(i).getString("CareCentreName");
                    clid[i] = ja.getJSONObject(i).getString("login_id");

//                    value[i] = "\ncarecentername:" + carecentername[i] + "\nplace:" + place[i] + "\nphone:" + phone[i] +"\nemail:" + email[i]+"\nlandmark:" + landmark[i];
                }

//                ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                AdapterViewCarecenter ar = new AdapterViewCarecenter(ViewCarecenter.this, carecentername,place,email,phone,landmark,careid,carename);
                l1.setAdapter(ar);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}