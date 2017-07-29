package com.housing.housecheap;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends ActionBarActivity {
	   private static int SPLASH_TIME_OUT = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		 new Handler().postDelayed(new Runnable() {
			 
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	                Intent i = new Intent(SplashScreen.this, MainActivity.class);
	                startActivity(i);
	 
	                // close this activity
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
