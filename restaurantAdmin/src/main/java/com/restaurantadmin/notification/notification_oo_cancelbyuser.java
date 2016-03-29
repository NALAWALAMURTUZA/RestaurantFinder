package com.restaurantadmin.notification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.restaurantadmin.Global.Global_variable;
import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class notification_oo_cancelbyuser extends Activity {
    JSONObject obj_Detail=null;
    TextView txv_hotelname,txt_order_id,booking_status,txt_date,txt_time,txt_user_name,
            txt_user_mobile,txv_delivery_type,txt_del_charge,txv_nocart,txv_order_add
            ,txv_delivery_charge,txv_total,txv_discount,txt_offer_id,txt_offer_name;
    LinearLayout ll_offer,ll_address;
    Button btn_ok;
    ListView lv_cart;
    notification_cart_adapter notification_cart_adapter;
    private Locale myLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_oo_cancelbyuser);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        lv_cart= (ListView) findViewById(R.id.lv_cart);
        txv_nocart= (TextView) findViewById(R.id.txv_nocart);
        txt_user_mobile=(TextView)findViewById(R.id.txt_user_mobile);
        txv_hotelname=(TextView)findViewById(R.id.txv_hotelname);
        txt_order_id=(TextView)findViewById(R.id.txt_order_id);
        booking_status=(TextView)findViewById(R.id.booking_status);
        txt_date=(TextView)findViewById(R.id.txt_date);
        txt_time=(TextView)findViewById(R.id.txt_time);
        txt_user_name=(TextView)findViewById(R.id.txt_user_name);
        txv_delivery_type=(TextView)findViewById(R.id.txv_delivery_type);
        txt_del_charge=(TextView)findViewById(R.id.txt_del_charge);
        txv_order_add=(TextView)findViewById(R.id.txv_order_add);
        txv_delivery_charge=(TextView)findViewById(R.id.txv_delivery_charge);
        txv_total=(TextView)findViewById(R.id.txv_total);
        txv_discount=(TextView)findViewById(R.id.txv_discount);
        txt_offer_id=(TextView)findViewById(R.id.txt_offer_id);
        txt_offer_name=(TextView)findViewById(R.id.txt_offer_name);
        ll_offer=(LinearLayout)findViewById(R.id.ll_offer);
        ll_address=(LinearLayout)findViewById(R.id.ll_address);
        btn_ok=(Button)findViewById(R.id.btn_ok);
    }

    private void setvalue() {
        try {
            if(obj_Detail.has("cart")) {
                JSONArray cart = new JSONArray();
                cart=obj_Detail.getJSONArray("cart");
                System.out.println("newordecart"+cart);
                if(cart.length()!=0){

                    notification_cart_adapter = new notification_cart_adapter(
                            notification_oo_cancelbyuser.this, cart);

                    lv_cart.setAdapter(notification_cart_adapter);
                    lv_cart.setVisibility(View.VISIBLE);
                    txv_nocart.setVisibility(View.GONE);
                }else{
                    lv_cart.setVisibility(View.GONE);
                    txv_nocart.setVisibility(View.VISIBLE);
                }
            }
            booking_status.setText(obj_Detail.getString("status"));

            txt_order_id.setText(obj_Detail.getString("uid"));
            txt_date.setText("Order Date : "+obj_Detail.getString("delivery_schedule"));
            txt_time.setText("Order Date : "+obj_Detail.getString("delivery_schedule_from"));
            txt_user_name.setText(obj_Detail.getString("name"));
            txv_hotelname.setText(obj_Detail.getString("restaurant_name"));
            txt_del_charge.setText(Global_variable.Categories_sr+" "+obj_Detail.getString("delivery_charge"));
            txv_delivery_charge.setText(Global_variable.Categories_sr+" "+obj_Detail.getString("delivery_charge"));
            txv_total.setText(Global_variable.Categories_sr+" "+obj_Detail.getString("total"));
            txv_order_add.setText(obj_Detail.getString("address"));
            if(obj_Detail.getString("delivery_ok").equalsIgnoreCase("1")){
                ll_address.setVisibility(View.VISIBLE);
            }else{
                ll_address.setVisibility(View.GONE);
            }
           /* if(obj_Detail.getJSONObject("data_obj_offer").getString("applied_offers").equalsIgnoreCase("")){
                ll_offer.setVisibility(View.GONE);

            }else{
                ll_offer.setVisibility(View.VISIBLE);
                txt_offer_id.setText(obj_Detail.getJSONObject("data_obj_offer").getString("applied_offers"));
                txt_offer_name.setText(obj_Detail.getJSONObject("data_obj_offer").getString("offername"));
                txv_discount.setText("Total : "+obj_Detail.getJSONObject("data_obj_offer").getString("discount") + " " + "Discount");
            }*/

            if(obj_Detail.getString("delivery_ok").equalsIgnoreCase("1")){
                txv_delivery_type.setText("Delivery");
            }else{
                txv_delivery_type.setText("Pick Up");
            }

            txt_user_mobile.setText(obj_Detail.getString("contact_number") + "," + " " + obj_Detail.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setlistner() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(notification_oo_cancelbyuser.this, Login_Activity.class);
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
                obj_Detail = new JSONObject(extras.getString("Detail"));
                System.out.println("obj_detail==" + obj_Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

      //  }

    }
}
