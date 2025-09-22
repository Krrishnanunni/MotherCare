package com.example.mothercare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ViewProposal extends AppCompatActivity implements JsonResponse, PaymentResultListener {
    ListView l1;
    String[] requirement,enq,stats,value,amt,rply;
    public static String enqid,amnts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_proposal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Checkout.preload(getApplicationContext());
        l1=findViewById(R.id.proposal);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewProposal.this;
        String q = "/view_proposal?login_id=" + Login.lid;
        q=q.replace( " ", "%20");
        JR.execute(q);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enqid = enq[position];
                amnts = amt[position];
                // Check if status is pending before showing payment dialog
                if (stats[position].equalsIgnoreCase("interested")) {
                    showDialog();
                }
                else if (stats[position].equalsIgnoreCase("pending")) {
                    Toast.makeText(ViewProposal.this, "Not Accepted your requirement", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ViewProposal.this, "Payment already completed for this request", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option")
                .setItems(new String[]{"Make Payment"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
                        if (which == 0) { // Precaution selected
//                            intent = new Intent(ViewBabies.this, ViewPrecaution.class);
//                            startActivity(new Intent(getApplicationContext(), ViewPrecaution.class));

                            startPayment();

//                            JsonReq JR=new JsonReq();
//                            JR.json_response=(JsonResponse) ViewProposal.this;
//                            String q = "/userMakePaymentforservices?enqid="+enqid;
//                            q=q.replace( " ", "%20");
//                            JR.execute(q);



                        } else if (which == 1) { // Vaccination selected

//                            startActivity(new Intent(getApplicationContext(), ViewVaccination.class));
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

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_edrzdb8Gbx5U5M");

        // Set up payment details
        try {
            JSONObject options = new JSONObject();
            options.put("name", "NINO CARE");
            options.put("description", "Payment for your order");
            options.put("currency", "INR");
//            options.put("amount", Integer.parseInt(ViewConsultingTIme.amt) * 100); // Convert to integer and multiply
            options.put("amount", Float.parseFloat(amnts) * 100); // Convert to integer and multiply
            options.put("prefill.email", "test@example.com");
            options.put("prefill.contact", "7025166955");

            // Open Razorpay Checkout activity
            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in starting payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            String method = jo.getString("method");
            if (method.equalsIgnoreCase("view")) {
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = jo.getJSONArray("display");

                int length = ja.length();
                requirement = new String[length];
                stats = new String[length];
                enq = new String[length];
                amt = new String[length];
                rply = new String[length];


                value = new String[length];

                for(int i=0; i<length;i++){
                    requirement[i]=ja.getJSONObject(i).getString("requirements");
                    stats[i]=ja.getJSONObject(i).getString("status");
                    enq[i]=ja.getJSONObject(i).getString("enquiry_id");
                    amt[i]=ja.getJSONObject(i).getString("amount");
                    rply[i]=ja.getJSONObject(i).getString("reply");

                    value[i]="\nRequirement:"+ requirement[i]+"\nAmount:"+amt[i]+"\nStatus:"+stats[i]+"\nReply:"+ rply[i];

                }

                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                l1.setAdapter(ar);




            }
                    }else if (method.equalsIgnoreCase("payment")){
                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                    }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPaymentSuccess(String s) {
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewProposal.this;
        String q = "/userMakePaymentforservices?enqid="+enqid;
        q=q.replace( " ", "%20");
        JR.execute(q);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "payment error!!", Toast.LENGTH_SHORT).show();
    }
}