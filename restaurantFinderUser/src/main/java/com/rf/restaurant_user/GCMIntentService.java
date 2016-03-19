package com.rf.restaurant_user;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.rf.restaurant_user.CommonUtilities.SENDER_ID;
import static com.rf.restaurant_user.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";
    static String Detail=null;
    Context context;
    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");

       // ServerUtilities.register(context, GlobalVaraibleActivity.str_userid,
         //       registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(com.rf.restaurant_user.R.string.gcm_unregistered));
        //ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        System.out.println("myintent" + intent.getExtras());
        String message = intent.getExtras().getString("type");
        Detail = intent.getExtras().getString("Detail");
        System.out.println("details==="+Detail);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);

    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(com.rf.restaurant_user.R.string.gcm_deleted, total);

        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(com.rf.restaurant_user.R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context,
                getString(com.rf.restaurant_user.R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */

    private static void generateNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300 };
        Notification noti=new Notification();
        System.out.println("mesageof_notifi" + message);
        if(message.toString().equalsIgnoreCase("2")) //confirmationapi 2 admin Confirm TG
        {
            noti=new Notification();
            System.out.println("detailsof_tg_2"+Detail);
            Intent notificationIntent = new Intent(context, com.rf_user.notification.notification_tg_confirmation.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {
                obj_Details = new JSONObject(Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("details2ingcm"+obj_Details);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                        context);
                noti = mBuilder
                        .setContentTitle("Your table is booked successfully.")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentText("Order Id:"+" "+obj_Details.getString("tg_order_uid")+"\n"+"Name:"+" "+obj_Details.getString("firstname") + "\n" +"Booking Date:"+" "+ obj_Details.getString("booking_date"))
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSmallIcon(com.rf.restaurant_user.R.drawable.ic_launcher)
                        .setSound(soundUri)
                        .setVibrate(vibrate)
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(message.toString().equalsIgnoreCase("6"))  //confirmationapi 6 admin cancle TG
         {
             noti=new Notification();
             Intent notificationIntent = new Intent(context, com.rf_user.notification.notification_tg_cancelation.class);
             notificationIntent.putExtra("Detail", Detail);
             JSONObject obj_Details= null;
             try {
                 obj_Details = new JSONObject(Detail);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             System.out.println("details2ingcm" + obj_Details);
             PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
             try {
                 NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                         context);
                 noti = mBuilder
                         .setContentTitle("Sorry.Your table grabing booking cancel by the restaurant.")
                         .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                         .setContentText("Order Id:" + " " + obj_Details.getString("tg_order_uid") + "\n" + "Name:" + " " + obj_Details.getString("firstname") + "\n" + "Booking Date:" + " " + obj_Details.getString("booking_date"))
                         .setContentIntent(contentIntent)
                         .setAutoCancel(true)
                         .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                         .setSmallIcon(com.rf.restaurant_user.R.drawable.ic_launcher)
                         .setSound(soundUri)
                         .setVibrate(vibrate)
                         .build();

             } catch (JSONException e) {
                 e.printStackTrace();
             }
        }
        else if(message.toString().equalsIgnoreCase("4"))  //confirmationapi 4 admin click finish  new user get review TG
        {
            noti=new Notification();
            Intent notificationIntent = new Intent(context, com.rf_user.notification.notification_tg_finish.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {
                obj_Details = new JSONObject(Detail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("details2ingcm" + obj_Details);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                        context);
                noti = mBuilder
                        .setContentTitle("Your table is booked successfully.")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentText("Order Id:"+" "+obj_Details.getString("tg_order_uid")+"\n"+"Name:"+" "+obj_Details.getString("firstname") + "\n" +"Booking Date:"+" "+ obj_Details.getString("booking_date"))
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSmallIcon(com.rf.restaurant_user.R.drawable.ic_launcher)
                        .setSound(soundUri)
                        .setVibrate(vibrate)
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(message.toString().equalsIgnoreCase("OO Confirmed"))  //OO_OrderStatus OO Confirmed online order confirm
        {
            noti=new Notification();
            System.out.println("detailsof_oo"+Detail);
            Intent notificationIntent = new Intent(context, com.rf_user.notification.notification_oo_confirmation.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {
                String struid="",str_firstname="",str_lastname="",
                        str_paymenttype="",str_hotelname="";
                obj_Details = new JSONObject(Detail);
                System.out.println("detailsOO_CONFIRMEDingcm" + obj_Details);
                if(obj_Details.has("data")){

                    JSONArray array=obj_Details.getJSONArray("data");
                    System.out.println("dataarray"+array);
                    for(int i=0;i < array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        System.out.println("oo_obj==="+obj);
                        struid=obj.getString("uid");
                        str_firstname= obj.getString("first_name");
                        str_lastname= obj.getString("last_name");
                        str_paymenttype= obj.getString("payment_status");
                        str_hotelname= obj.getString("name_en");
                    }
                }


            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                        context);
                noti = mBuilder
                        .setContentTitle("Your table is booked successfully.")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentText("Order Id:"+" "+struid+"\n"+"Name:"+" "+str_firstname+" "+str_lastname+"\n"+"Date"+" "+obj_Details.getString("delivery_schedule"))
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSmallIcon(com.rf.restaurant_user.R.drawable.ic_launcher)
                        .setSound(soundUri)
                        .setVibrate(vibrate)
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(message.toString().equalsIgnoreCase("OO Cancel"))  //OO_OrderStatus OO Confirmed online order confirm
        {
            noti=new Notification();
            Intent notificationIntent = new Intent(context, com.rf_user.notification.notification_oo_cancelation.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {
                String struid="",str_firstname="",str_lastname="",
                        str_paymenttype="",str_hotelname="";
                obj_Details = new JSONObject(Detail);
                System.out.println("detailsOO_CONFIRMEDingcm" + obj_Details);
                if(obj_Details.has("data")){

                    JSONArray array=obj_Details.getJSONArray("data");
                    System.out.println("dataarray"+array);
                    for(int i=0;i < array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        System.out.println("oo_obj==="+obj);
                        struid=obj.getString("uid");
                        str_firstname= obj.getString("first_name");
                        str_lastname= obj.getString("last_name");
                        str_paymenttype= obj.getString("payment_status");
                        str_hotelname= obj.getString("name_en");
                    }
                }


                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                        context);
                noti = mBuilder
                        .setContentTitle("Sorry.Your online order cancel by the restaurant.")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentText("Order Id:"+" "+struid+"\n"+"Name:"+" "+str_firstname+" "+str_lastname+"\n"+"Date"+" "+obj_Details.getString("delivery_schedule"))
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSmallIcon(com.rf.restaurant_user.R.drawable.ic_launcher)
                        .setSound(soundUri)
                        .setVibrate(vibrate)
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
      //  notificationManager.notify(0, noti);
        Random random = new Random();
        notificationManager.notify(random.nextInt(1000 - 0 + 1) + 0, noti);
    }

}
