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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class notification_tg_neworder extends Activity {
    JSONObject obj_Detail=null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,
            txv_no_of_loyalty,txt_no_of_pers,txv_comment;
    Button btn_ok;
    Bundle extras=new Bundle();
    private Locale myLocale;
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
        txv_comment=(TextView)findViewById(R.id.txv_comment);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setvalue() {
        String[] onlineorderStatus = getResources().getStringArray(R.array.array_online_order_status);
        try {
            if(obj_Detail.getString("booking_mode").equalsIgnoreCase("1")){
                booking_status.setText(getResources().getString(R.string.str_Pending));
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("2")){
                booking_status.setText(getResources().getString(R.string.str_Confirmed));
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("3")){
                booking_status.setText(getResources().getString(R.string.str_Not_Show));
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("4")){
                booking_status.setText(getResources().getString(R.string.str_Review));
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("5")){
                booking_status.setText(getResources().getString(R.string.str_Over));
            }
            else if(obj_Detail.getString("booking_mode").equalsIgnoreCase("6")){
                booking_status.setText(getResources().getString(R.string.str_Cancel));
            }
            txt_order_id.setText(obj_Detail.getString("obj_oder_uid"));
            txt_date.setText(getResources().getString(R.string.tgrawfile_bookingid)+obj_Detail.getString("booking_date"));
            txt_time.setText(getResources().getString(R.string.noti_Booking_Time)+obj_Detail.getString("booking_time"));
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
