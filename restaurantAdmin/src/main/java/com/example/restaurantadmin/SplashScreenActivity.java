package com.example.restaurantadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.rf.restaurantadmin.Login_Activity;
import com.rf.restaurantadmin.R;
import com.sharedprefernce.LanguageConvertLocalPrefernce;

public class SplashScreenActivity extends Activity {

	Intent in;
	ConnectionDetector cd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		cd = new ConnectionDetector(getApplicationContext());

		

		// METHOD 1     
        
        /****** Create Thread that will sleep for 5 seconds *************/        
       Thread background = new Thread() {
           public void run() {
                
               try {
                   // Thread will sleep for 5 seconds
                   sleep(5*1000);
                    
                   // After 5 seconds redirect to another intent
                   Intent i=new Intent(getBaseContext(),Login_Activity.class);
                   startActivity(i);
                    
                   //Remove activity
                   finish();
                    
               } catch (Exception e) {
                
               }
           }
       };
        
       // start thread
       background.start();

	

	}

	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		System.out.println("!!!!!!!!!!!!!!!onStart");
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		System.out.println("!!!!!!!!!!!!!!!onRestart");
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		System.out.println("!!!!!!!!!!!!!!!onPause");
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		System.out.println("!!!!!!!!!!!!!!!onStop");
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		System.out.println("!!!!!!!!!!!!!!!onDestroy");
		LanguageConvertLocalPrefernce.loadLocale(getApplicationContext());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	
}
