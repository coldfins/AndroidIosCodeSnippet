package com.post.wall.wallpostapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ved_pc on 3/15/2017.
 */

public class SettingActivity extends ActionBarActivity implements View.OnClickListener{
    LinearLayout layoutUserProfile, layoutChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutUserProfile = (LinearLayout) findViewById(R.id.layoutUserProfile);
        layoutUserProfile.setOnClickListener(this);
        layoutChangePassword = (LinearLayout) findViewById(R.id.layoutChangePassword);
        layoutChangePassword.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layoutUserProfile:
                Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
            case R.id.layoutChangePassword:
                Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
        }
    }
}
