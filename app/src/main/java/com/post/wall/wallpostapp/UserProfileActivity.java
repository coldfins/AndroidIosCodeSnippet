package com.post.wall.wallpostapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.post.wall.wallpostapp.model.User;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Utility;

import org.w3c.dom.Text;

/**
 * Created by ved_pc on 3/7/2017.
 */

public class UserProfileActivity extends ActionBarActivity {
    CircleImageView imgProfile;
    TextView txtUserName, txtEmail, txtDOB, txtMobileNumber;
    User user;
    LinearLayout layoutUserOtherInfo;
    CardView cardPrivateUserProfile;

    DisplayImageOptions doption = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_profile)
            .showImageOnFail(R.drawable.img_profile)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.v("TTT", "User info........" + (getIntent().getExtras().get("User").toString()));
        user = new Gson().fromJson(getIntent().getExtras().get("User").toString(), User.class);

        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        try {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.displayImage(user.getProfilePic(), imgProfile, doption);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(user.getFirstName() + " " + user.getLastName());
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(user.getEmail());
        txtDOB = (TextView) findViewById(R.id.txtDOB);
        txtDOB.setText(Utility.formatDate(user.getBirthDate()));
        txtMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);
        txtMobileNumber.setText(user.getContact());

        layoutUserOtherInfo = (LinearLayout) findViewById(R.id.layoutUserOtherInfo);
        cardPrivateUserProfile = (CardView) findViewById(R.id.cardPrivateUserProfile);
        if(!user.isPublic() && user.getUserId() != HomeActivity.user.getUser().getUserId()){
            layoutUserOtherInfo.setVisibility(View.GONE);
            cardPrivateUserProfile.setVisibility(View.VISIBLE);
        }else{
            layoutUserOtherInfo.setVisibility(View.VISIBLE);
            cardPrivateUserProfile.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        super.onBackPressed();
    }
}
