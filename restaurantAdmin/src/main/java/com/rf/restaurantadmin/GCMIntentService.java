package com.rf.restaurantadmin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.rf.restaurantadmin.CommonUtilities.SENDER_ID;
import static com.rf.restaurantadmin.CommonUtilities.displayMessage;

public class GCMIntentService extends  com.google.android.gcm.GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
    static String Detail=null;
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
           //     registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        //ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        System.out.println("myintent" + intent.getExtras());
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("type");
        Detail=new String();
        Detail = intent.getExtras().getString("Detail");

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
        String message = getString(R.string.gcm_deleted, total);
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
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void generateNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300 };
        Notification noti=new Notification();
        if(message.toString().equals("New order OO"))  //TableGrabOrder when user click online order submit
        {
            noti=new Notification();
            Intent notificationIntent = new Intent(context, com.restaurantadmin.notification.notification_oo_neworder.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {

                obj_Details = new JSONObject(Detail);
                System.out.println("detailsOO_CONFIRMEDingcm" + obj_Details);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                    context);
            noti = mBuilder
                    .setContentTitle("Hey, Look at this, you got new online order.")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentText("Name : " + obj_Details.getString("name") + "\n" + "UID : "
                            + obj_Details.getString("uid") + "\n" +"Status : "+obj_Details.getString("status")+"\n"+ "Booking Date : " + obj_Details.getString("delivery_schedule"))
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setSound(soundUri)
                    .setVibrate(vibrate)
                    .build();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       else if(message.toString().equals("New order TG")) //TableGrabOrder when user click online order submit
        {
            noti=new Notification();
            Intent notificationIntent = new Intent(context, com.restaurantadmin.notification.notification_tg_neworder.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {
                obj_Details = new JSONObject(Detail);

            System.out.println("details2ingcm" + Detail);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                        context);
                noti = mBuilder
                    .setContentTitle("Hey, Look at this, you got new table grabber booking.")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentText("Booking Date"+": "+obj_Details.getString("booking_date")+"\n"+"No of People"+": "
                            +obj_Details.getString("number_of_people"))
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setSound(soundUri)
                    .setVibrate(vibrate)
                    .build();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(message.toString().equals("TG Cancel User")) //cancel_order when user cancel TG order
        {
            noti=new Notification();
            System.out.println("detaisl_tg_Cancel"+Detail);
            Intent notificationIntent = new Intent(context, com.restaurantadmin.notification.notification_tg_cancelbyuser.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONArray Details= null;
            JSONObject obj_Details=null;
            try {
                Details = new JSONArray(Detail);
                String str_date="",str_no_people="",str_lastname="",
                        str_paymenttype="",str_hotelname="";
                System.out.println("dataarray"+Details);
                for(int i=0;i < Details.length();i++){
                     obj_Details=Details.getJSONObject(i);
                    System.out.println("oo_obj==="+obj_Details);
                    str_date=obj_Details.getString("booking_date");
                    str_no_people= obj_Details.getString("number_of_people");

                }
                System.out.println("details_tgcancel" + obj_Details);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(
                    context);
            noti = mBuilder
                    .setContentTitle("Your Table grabber order is cancelled by User.")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentText("Booking Date"+": "+str_date+"\n"+"No of People"+": "
                            +str_no_people)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setSound(soundUri)
                    .setVibrate(vibrate)
                    .build();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(message.toString().equals("OO Cancel User")) //cancel_order when user cancel oo order
        {
            noti=new Notification();
            Intent notificationIntent = new Intent(context, com.restaurantadmin.notification.notification_oo_cancelbyuser.class);
            notificationIntent.putExtra("Detail", Detail);
            JSONObject obj_Details= null;
            try {

                obj_Details = new JSONObject(Detail);
                System.out.println("detailsOO_CONFIRMEDingcm" + obj_Details);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
            noti = new Notification.Builder(context)
                    .setContentTitle("Your online order is cancelled by User.")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentText("Name : " + obj_Details.getString("name") + "\n" + "UID : "
                            + obj_Details.getString("uid") + "\n" +"Status : "+obj_Details.getString("status")+"\n"+ "Booking Date : " + obj_Details.getString("delivery_schedule"))
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setSound(soundUri)
                    .setVibrate(vibrate)
                    .build();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Random random = new Random();
        notificationManager.notify(random.nextInt(1000 - 0 + 1) + 0, noti);
      //  notificationManager.notify(0, noti);
    }


}
