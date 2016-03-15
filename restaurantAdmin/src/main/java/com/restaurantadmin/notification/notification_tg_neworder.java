package com.restaurantadmin.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

public class notification_tg_neworder extends Activity {
    JSONObject obj_Detail=null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,
            txv_no_of_loyalty,txt_no_of_pers,txv_comment;
    Button btn_ok;
    Bundle extras=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_tg_neworder);
        System.out.println("myint"+getIntent().getExtras());
        onNewIntent(getIntent());
        initilize();
        setvalue();
        setlistner();
    }
    private void initilize() {
        txt_order_id=(TextView)findViewById(R.id.txt_order_id);
        booking_status=(TextView)findViewById(R.id.booking_status);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_time=(TextView)findViewById(R.id.txt_time);
        txv_no_of_loyalty=(TextView)findViewById(R.id.txv_no_of_loyalty);
        txt_no_of_pers=(TextView)findViewById(R.id.txt_no_of_pers);
        txv_comment=(TextView)findViewById(R.id.txv_comment);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setvalue() {
        try {
            if(obj_Detail.getString("booking_mode").equalsIgnoreCase("1")){
                booking_status.setText("Pending");
            }

            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("2")){
                booking_status.setText("Confirmed");
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("3")){
                booking_status.setText("Not Show");
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("4")){
                booking_status.setText("Review");
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("5")){
                booking_status.setText("Over");
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("6")){
                booking_status.setText("Cancel");
            }
            txt_order_id.setText(obj_Detail.getString("obj_oder_uid"));
            txt_date.setText("Booking Date : "+obj_Detail.getString("booking_date"));
            txt_time.setText("Booking Time : "+obj_Detail.getString("booking_time"));

            txv_no_of_loyalty.setText(obj_Detail.getString("loyalty"));
            txt_no_of_pers.setText(obj_Detail.getString("number_of_people"));
            if(obj_Detail.getString("comments").equalsIgnoreCase("")){
                txv_comment.setText("No Comments");
            }else{
                txv_comment.setText(obj_Detail.getString("comments"));
            }
            txt_user_name.setText(obj_Detail.getString("first_name")+" "+obj_Detail.getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setlistner() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(notification_tg_neworder.this, Login_Activity.class);
                startActivity(in);
//                finish();
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
         extras=new Bundle();
         extras = intent.getExtras();
        if (extras != null) {
            try {
                obj_Detail = new JSONObject(extras.getString("Detail"));
                System.out.println("obj_detail_new_tg==" + obj_Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
