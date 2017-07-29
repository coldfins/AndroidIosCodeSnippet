package com.post.wall.wallpostapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.post.wall.wallpostapp.utility.Utility;

public class ForgotPasswordSuccessActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.forgotpassword_success);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolBar.setTitle("E-Mail Sent");
        toolBar.setTitleTextColor(getResources().getColor(android.R.color.black));

        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));

        Utility.setWhiteToolBar(toolBar, this, getResources().getColor(android.R.color.black));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
        super.onBackPressed();
    }

}
