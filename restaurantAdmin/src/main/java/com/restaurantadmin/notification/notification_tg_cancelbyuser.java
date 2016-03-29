package com.restaurantadmin.notification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class notification_tg_cancelbyuser extends Activity {

    JSONObject obj_Detail=null;
    TextView txt_order_id,booking_status,txt_date,txt_time,
            txv_no_of_loyalty,txt_no_of_pers,txv_email,txv_comment,txt_user_name;
    Button btn_ok;
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_tg_cancelbyuser);
        System.out.println(getIntent());
        onNewIntent(getIntent());
        initilize();
        setvalue();
        setlistner();
        setlangueage();
    }


   private void setlangueage() {
        // language*****************
        Locale.getDefault().getLanguage();
        System.out.println("Device_language"
                + Locale.getDefault().getLanguage());

        String langPref = "Language";
        SharedPreferences prefs_oncreat = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs_oncreat.getString(langPref, "");

        System.out.println("Murtuza_Nalawala_language_oncreat" + language);
        if (language.equalsIgnoreCase("")) {
            System.out.println("Murtuza_Nalawala_language_oncreat_if");

        } else if (language.equalsIgnoreCase("ro")) {
            System.out.println("Murtuza_Nalawala_language_oncreat_if_ar");
            setLocaleonload("ro");

        } else if (language.equalsIgnoreCase("en")) {
            System.out.println("Murtuza_Nalawala_language_oncreat_if_en");
            setLocaleonload("en");

        } else {
            System.out.println("Murtuza_Nalawala_language_oncreat_if_else");
            setLocaleonload("en");

        }
    }
    public void setLocaleonload(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        System.out.println("Murtuza_Nalawala_deleteall");

    }

    @Override
    public void onResume() {
        System.out.println("murtuza_nalawala");
        super.onResume(); // Always call the superclass method first
        LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void initilize() {
        txt_order_id=(TextView)findViewById(R.id.txt_order_id);
        booking_status=(TextView)findViewById(R.id.booking_status);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_time=(TextView)findViewById(R.id.txt_time);
        txv_no_of_loyalty=(TextView)findViewById(R.id.txv_no_of_loyalty);
        txt_no_of_pers=(TextView)findViewById(R.id.txt_no_of_pers);
//        txt_hotel_name=(TextView)findViewById(R.id.txt_hotel_name);
        txv_email=(TextView)findViewById(R.id.txv_email);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        txv_comment=(TextView)findViewById(R.id.txv_comment);
    }

    private void setvalue() {
        try {
//            if(obj_Detail.getString("booking_mode").equalsIgnoreCase("1")){
                booking_status.setText("Cancel By User");
//            }
//
//            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("2")){
//                booking_status.setText("Confirmed");
//            }
//            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("3")){
//                booking_status.setText("Not Show");
//            }
//            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("4")){
//                booking_status.setText("Review");
//            }
//            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("5")){
//                booking_status.setText("Over");
//            }
//            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("6")){
//                booking_status.setText("Cancel");
//            }
            txt_order_id.setText("Booking Id :"+obj_Detail.getString("tg_order_uid"));
            txt_date.setText("Booking Date : "+obj_Detail.getString("booking_date"));
            txt_time.setText("Booking Time : "+obj_Detail.getString("booking_time"));
//            txt_hotel_name.setText(obj_Detail.getString("restaurant_name"));
            txv_email.setText(obj_Detail.getString("email"));
            txv_no_of_loyalty.setText(obj_Detail.getString("loyalty"));
            txt_no_of_pers.setText(obj_Detail.getString("number_of_people"));
            if(obj_Detail.getString("comment").equalsIgnoreCase("")){
                txv_comment.setText("No Comments");
            }else{
                txv_comment.setText(obj_Detail.getString("comment"));
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
                Intent in=new Intent(notification_tg_cancelbyuser.this, Login_Activity.class);
                startActivity(in);
//                finish();
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
       // if (extras != null) {
            try {
                JSONArray Details=new JSONArray(extras.getString("Detail"));

                System.out.println("obj_detail==array" + Details);
                for(int i=0;i < Details.length();i++){
                    obj_Detail=Details.getJSONObject(i);
                    System.out.println("oo_objtg_cancel==="+obj_Detail);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

       // }

    }
}
