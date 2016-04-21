package com.rf_user.notification;

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

import com.rf.restaurant_user.R;
import com.rf_user.activity.FindRestaurant;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class notification_tg_cancelation extends Activity {
    JSONObject obj_Detail = null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,txt_restaurant_name,txt_loyality,txt_numberof_people;
    Button btn_ok;
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
        setContentView(R.layout.activity_notification_tg_cancelation);
        System.out.println(getIntent());
        onNewIntent(getIntent());
        initilize();
        setlistner();
        setlangueage();
        setvalue();
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

            booking_status.setText(getResources().getString(R.string.str_Cancel));
            txt_order_id.setText(getResources().getString(R.string.tgrawfile_bookingid)+obj_Detail.getString("tg_order_uid"));
            txt_date.setText(getResources().getString(R.string.noti_Booking_Date)+obj_Detail.getString("booking_date"));
            txt_time.setText(getResources().getString(R.string.noti_bookingTime)+obj_Detail.getString("booking_time"));
            txt_numberof_people.setText(getResources().getString(R.string.noti_Numberofpeople)+obj_Detail.getString("number_of_people"));
            txt_loyality.setText(getResources().getString(R.string.noti_Loyalty)+obj_Detail.getString("loyalty"));
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