package com.rf_user.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.osf.restaurant_user.R;
import com.rf_user.activity.FindRestaurant;

import org.json.JSONException;
import org.json.JSONObject;

public class notification_tg_cancelation extends Activity {
    JSONObject obj_Detail = null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,txt_restaurant_name,txt_loyality,txt_numberof_people;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_tg_cancelation);
        System.out.println(getIntent());
        onNewIntent(getIntent());
        initilize();
        setvalue();
        setlistner();
    }

    private void initilize() {
        txt_order_id=(TextView)findViewById(R.id.txt_order_id);
        txt_restaurant_name=(TextView)findViewById(R.id.txt_restaurant_name);
        txt_loyality=(TextView)findViewById(R.id.txt_loyality);
        txt_numberof_people=(TextView)findViewById(R.id.txt_numberof_people);
        booking_status=(TextView)findViewById(R.id.booking_status);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_time=(TextView)findViewById(R.id.txt_time);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setvalue() {
        try {

            booking_status.setText("Cancel");
            txt_order_id.setText("Booking Id :"+obj_Detail.getString("tg_order_uid"));
            txt_date.setText("Booking Date : "+obj_Detail.getString("booking_date"));
            txt_time.setText(" Booking Time : "+obj_Detail.getString("booking_time"));
            txt_numberof_people.setText("Number of people : " +obj_Detail.getString("number_of_people"));
            txt_loyality.setText("Loyalty: "+obj_Detail.getString("loyalty"));
            txt_restaurant_name.setText(obj_Detail.getString("restaurant_name"));
            txt_user_name.setText(obj_Detail.getString("firstname")+" "+obj_Detail.getString("surname"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setlistner() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(notification_tg_cancelation.this, FindRestaurant.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
    //    if (extras != null) {
            try {
                obj_Detail = new JSONObject(extras.getString("Detail"));
                System.out.println("obj_detail" + obj_Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    //    }

    }
}