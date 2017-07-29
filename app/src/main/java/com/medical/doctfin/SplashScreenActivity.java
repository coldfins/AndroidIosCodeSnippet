package com.medical.doctfin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity {

	public void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	Thread splashTread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

		setContentView(R.layout.activity_splash_screen);

		StartAnimations();
	}

	private void StartAnimations() {
		// Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		// anim.reset();
		// LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
		// l.clearAnimation();
		// l.startAnimation(anim);
		// anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		// anim.reset();
		// ImageView iv = (ImageView) findViewById(R.id.splash);
		// iv.clearAnimation();
		// iv.startAnimation(anim);

		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					// Splash screen pause time
					while (waited < 5000) {
						sleep(100);
						waited += 100;
					}
					Intent intent = new Intent(SplashScreenActivity.this, UserHomeActivity.class);
					startActivity(intent);
					SplashScreenActivity.this.finish();
				} catch (InterruptedException e) {
				} finally {
					SplashScreenActivity.this.finish();
				}

			}
		};
		splashTread.start();

	}

}
