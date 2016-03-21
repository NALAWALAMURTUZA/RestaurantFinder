package com.rf_user.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rf.restaurant_user.R;
import com.rf_user.activity.FindRestaurant;
import com.rf_user.global.Global_variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class notification_oo_confirmation extends Activity {
    JSONObject obj_Detail=null;
    TextView txt_order_id,booking_status,txt_date,txt_time,txt_user_name,
            txv_order_add,txt_user_mobile,txv_paymenttype,txv_hotelname,txv_nocart
            ,txv_delivery_charge,txv_total,txv_discount,txt_offer_id,txt_offer_name
            ,txv_delivery_type;
    LinearLayout ll_offer,ll_address;
    Button btn_ok;
    ListView lv_cart;
    notification_cart_adapter notification_cart_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification_oo_confirmation);
        System.out.println(getIntent());
        onNewIntent(getIntent());
        initilize();
        setvalue();
        setlistner();
    }

    private void initilize() {
        lv_cart= (ListView) findViewById(R.id.lv_cart);
        txv_nocart= (TextView) findViewById(R.id.txv_nocart);
        txt_order_id = (TextView) findViewById(R.id.txt_order_id);
        booking_status = (TextView) findViewById(R.id.booking_status);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_user_name = (TextView) findViewById(R.id.txt_user_name);
        txv_order_add= (TextView) findViewById(R.id.txv_order_add);
        txt_user_mobile= (TextView) findViewById(R.id.txt_user_mobile);
        txv_paymenttype= (TextView) findViewById(R.id.txv_paymenttype);
        txv_hotelname= (TextView) findViewById(R.id.txv_hotelname);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        txv_delivery_charge=(TextView)findViewById(R.id.txv_delivery_charge);
        txv_total=(TextView)findViewById(R.id.txv_total);
        txv_discount=(TextView)findViewById(R.id.txv_discount);
        txt_offer_id=(TextView)findViewById(R.id.txt_offer_id);
        txt_offer_name=(TextView)findViewById(R.id.txt_offer_name);
        txv_delivery_type=(TextView)findViewById(R.id.txv_delivery_type);
        ll_offer=(LinearLayout)findViewById(R.id.ll_offer);
        ll_address=(LinearLayout)findViewById(R.id.ll_address);
    }

    private void setvalue() {
        try {
            JSONArray cart=new JSONArray();
//            if (obj_Detail.getString("order_status").equalsIgnoreCase("Confirmed")) {
                booking_status.setText("Confirmed");
//            } else if (obj_Detail.getString("order_status").equalsIgnoreCase("Cancel")) {
//                booking_status.setText("Cancel");
//            }
            txt_date.setText("Order Date : "+obj_Detail.getString("delivery_schedule"));
            txt_time.setText("Order Time : "+obj_Detail.getString("delivery_schedule_from"));



            txv_order_add.setText(obj_Detail.getString("address"));
            txt_user_mobile.setText(obj_Detail.getString("mobile_no")+","+" "+obj_Detail.getString("email"));
            if(obj_Detail.has("data")){

                String struid="",str_firstname="",str_lastname="",
                        str_paymenttype="",str_hotelname=""
                        ,str_offer_uid="",str_offer_name="",str_total="",str_discount="",str_delivery_charge="";
                JSONArray array=obj_Detail.getJSONArray("data");
                System.out.println("dataarray"+array);
                for(int i=0;i < array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    System.out.println("oo_obj==="+obj);
                    if(obj.has("cart")){
                        cart=obj.getJSONArray("cart");
                        System.out.println("cart");
                    }
                    struid=obj.getString("uid");
                    str_firstname= obj.getString("first_name");
                    str_lastname= obj.getString("last_name");
                    str_paymenttype= obj.getString("payment_status");
                    str_hotelname= obj.getString("name_en");

                    //str_offer_uid=obj.getString("applied_offers");
                    //str_offer_name= obj.getString("offername");
                    str_total= obj.getString("total");
                  //  str_discount= obj.getString("discount");
                    str_delivery_charge= obj.getString("delivery_charge");
                    /*if(str_offer_uid.equalsIgnoreCase("")||str_offer_uid.equalsIgnoreCase("null")||str_offer_uid.length()==0){
                        ll_offer.setVisibility(View.GONE);

                    }else{
                        txt_offer_id.setText(str_offer_uid);
                        ll_offer.setVisibility(View.VISIBLE);
                        if(str_discount.equalsIgnoreCase("")||str_discount.equalsIgnoreCase("null")||str_discount.length()==0){

                        }else{
                            txv_discount.setText("Total : "+str_discount+ "%"+ " " + "Discount");
                        }
                        if(str_offer_name.equalsIgnoreCase("")||str_offer_name.equalsIgnoreCase("null")||str_offer_name.length()==0){

                        }else{
                            txt_offer_name.setText(str_offer_name);
                        }
                    }*/


                    if(obj.getString("delivery_ok").equalsIgnoreCase("1")){
                        txv_delivery_type.setText("Delivery");
                    }else{
                        txv_delivery_type.setText("Pick Up");
                    }


                    if(obj.getString("delivery_ok").equalsIgnoreCase("1")){
                        ll_address.setVisibility(View.VISIBLE);
                    }else{
                        ll_address.setVisibility(View.GONE);
                    }
                }
                if(cart.length()!=0){
                notification_cart_adapter = new notification_cart_adapter(
                        notification_oo_confirmation.this, cart);

                lv_cart.setAdapter(notification_cart_adapter);
                    lv_cart.setVisibility(View.VISIBLE);
                    txv_nocart.setVisibility(View.GONE);
                }else{
                    lv_cart.setVisibility(View.GONE);
                    txv_nocart.setVisibility(View.VISIBLE);
                }
                if(!struid.equalsIgnoreCase("")){
                    txt_order_id.setText(struid);
                }



                if(!str_total.equalsIgnoreCase("")||!str_total.equalsIgnoreCase("null")){
                    txv_total.setText(Global_variable.Categories_sr+" "+str_total);
                }


                if(!str_delivery_charge.equalsIgnoreCase("")||!str_delivery_charge.equalsIgnoreCase("null")){
                    txv_delivery_charge.setText(Global_variable.Categories_sr+" "+str_delivery_charge);
                }


                if(!str_paymenttype.equalsIgnoreCase("")||!str_paymenttype.equalsIgnoreCase("null")){
                    txv_paymenttype.setText(str_paymenttype);
                }
                if(!str_hotelname.equalsIgnoreCase("")||!str_hotelname.equalsIgnoreCase("null")){
                    txv_hotelname.setText(str_hotelname);
                }
                if(!str_firstname.equalsIgnoreCase("")&&!str_lastname.equalsIgnoreCase("")){

                    txt_user_name.setText(str_firstname + " " + str_lastname);
                }else{
                    if(!str_firstname.equalsIgnoreCase("")||!str_firstname.equalsIgnoreCase("null")){
                        txt_user_name.setText(str_firstname);
                    }else {
                        txt_user_name.setText(str_lastname);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setlistner() {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(notification_oo_confirmation.this, FindRestaurant.class);
                startActivity(in);
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
       // if (extras != null) {
            try {
                System.out.println("in===");
                obj_Detail = new JSONObject(extras.getString("Detail"));
                System.out.println("obj_detail!!!!!" + obj_Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

      //  }

    }
}