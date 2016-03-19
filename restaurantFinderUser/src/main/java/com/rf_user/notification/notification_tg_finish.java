package com.rf_user.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.FindRestaurant;

import org.json.JSONException;
import org.json.JSONObject;

public class notification_tg_finish extends Activity {
    JSONObject obj_Detail=null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,
            txt_no_of_pers,txt_restaurant_name,txt_loyality;
    Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_tg_finish);
        System.out.println(getIntent());
        onNewIntent(getIntent());
        initilize();
        setvalue();
        setlistner();
    }

    private void initilize() {
        txt_no_of_pers=(TextView)findViewById(R.id.txt_no_of_pers);
        txt_order_id=(TextView)findViewById(R.id.txt_order_id);
        booking_status=(TextView)findViewById(R.id.booking_status);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_time=(TextView)findViewById(R.id.txt_time);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        txt_loyality=(TextView)findViewById(R.id.txt_loyality);
        txt_restaurant_name=(TextView)findViewById(R.id.txt_restaurant_name);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setvalue() {
        try {

            booking_status.setText("Finished");
            txt_no_of_pers.setText("Number of people : "+obj_Detail.getString("number_of_people"));

            txt_order_id.setText("Booking Id :"+obj_Detail.getString("tg_order_uid"));
            txt_date.setText("Booking Date : "+obj_Detail.getString("booking_date"));
            txt_time.setText(" Booking Time : "+obj_Detail.getString("booking_time"));
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
                Intent in=new Intent(notification_tg_finish.this, FindRestaurant.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
       // if (extras != null) {
            try {
                obj_Detail = new JSONObject(extras.getString("Detail"));
                System.out.println("obj_detail==" + obj_Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

      //  }

    }
}
