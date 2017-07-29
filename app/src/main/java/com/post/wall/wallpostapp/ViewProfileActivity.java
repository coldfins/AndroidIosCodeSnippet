package com.post.wall.wallpostapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.post.wall.wallpostapp.adapter.PopupDeviceAdapter;
import com.post.wall.wallpostapp.adapter.PopupDeviceWithSelectionAdapter;
import com.post.wall.wallpostapp.model.UserModel;
import com.post.wall.wallpostapp.utility.BitmapUtils;
import com.post.wall.wallpostapp.utility.CircleImageView;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.GetUrlClass;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.RestInterface;
import com.post.wall.wallpostapp.utility.Utility;
import com.post.wall.wallpostapp.utility.checkInternetisAvailable;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by ved_pc on 3/15/2017.
 */

public class ViewProfileActivity extends ActionBarActivity implements View.OnClickListener{

    EditText edtFirstname, edtLastname, mEmailView, edtMobileNumber;
    static EditText edtDOB;
    RelativeLayout layoutProgress;
    MaterialIconView materialIconViewAddRoom;
    CircleImageView imgProfile;
    ImageView imgPrivacy;

    TextView txtUserName, txtEmail;

    RestInterface restInterface;
//    GetUrlClass url;
//    RestAdapter adapter;

    static boolean startDatePickerIsOpen = false;

    DisplayImageOptions doption = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.img_profile)
            .showImageOnFail(R.drawable.img_profile)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        restInterface = Utility.getRetrofitInterface();
//        url = new GetUrlClass();
//        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(url.getUrl()).build();
//        restInterface = adapter.create(RestInterface.class);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(HomeActivity.user.getUser().getFirstName() + " " + HomeActivity.user.getUser().getLastName());
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(HomeActivity.user.getUser().getEmail());


        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        try {
            com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            imageLoader.displayImage(HomeActivity.user.getUser().getProfilePic(), imgProfile, doption);
        } catch (Exception e) {
            e.printStackTrace();
        }

        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtFirstname.setText(HomeActivity.user.getUser().getFirstName());
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        edtLastname.setText(HomeActivity.user.getUser().getLastName());
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setText(HomeActivity.user.getUser().getEmail());
        edtMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        edtMobileNumber.setText(HomeActivity.user.getUser().getContact());
        edtDOB = (EditText) findViewById(R.id.edtDOB);
        edtDOB.setText(Utility.getLeftDayTime(HomeActivity.user.getUser().getBirthDate()));
        edtDOB.setOnClickListener(this);
        imgPrivacy = (ImageView) findViewById(R.id.imgPrivacy);
        if(HomeActivity.user.getUser().isPublic()){
            imgPrivacy.setTag("everyone");
        }else{
            imgPrivacy.setTag("self");
        }
        imgPrivacy.setOnClickListener(this);


        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);

        materialIconViewAddRoom = (MaterialIconView) findViewById(R.id.materialIconViewAddRoom);
        materialIconViewAddRoom.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtDOB:
                if (!startDatePickerIsOpen) {
                    DialogFragment picker = new StartDatePickerFragment();

                    ((StartDatePickerFragment)picker).setParams(ViewProfileActivity.this);
                    FragmentManager fragmentManager = getFragmentManager();
                    picker.show(fragmentManager, "datePicker");
                    startDatePickerIsOpen = true;
                }

                break;
            case R.id.materialIconViewAddRoom:
                selectImage();

                break;
            case R.id.imgPrivacy:
                ArrayList<String> menuFilterList;
                menuFilterList = new ArrayList<String>();
                menuFilterList.add("Who can see this?");
                menuFilterList.add("Everyone");
                menuFilterList.add("Only Me");
                dropdownPopupWindow(imgPrivacy, menuFilterList);
                break;
            default:
                break;
        }
    }

    public static class StartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        Activity activity;

        public void setParams(Activity activity){
            this.activity = activity;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            return datePickerDialog ;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            startDatePickerIsOpen = false;
            super.onCancel(dialog);
        }

        /**
         * if User select the date from Date picker dialog
         *
         */
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            startDatePickerIsOpen = false;

            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar selectedDate = Calendar.getInstance(timeZone);
            selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);
            selectedDate.set(Calendar.MONTH, selectedMonth);
            selectedDate.set(Calendar.YEAR, selectedYear);

            StringBuilder dateTimeText = new StringBuilder();
            dateTimeText.append(new DecimalFormat("00")
                    .format(selectedMonth + 1)
                    + "/"
                    + new DecimalFormat("00").format(selectedDay)
                    + "/"
                    + new DecimalFormat("00").format(selectedYear));

            edtDOB.setText(dateTimeText);

        }
    }

    int flag = 0;
    private String userChooseTask, strUserProfileImage;
    Uri selectedImageUri;
    File scaledFile;
    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.CheckAllMarshmallowPermissionForActivityCamera(getApplicationContext(), ViewProfileActivity.this);
                Log.d("result", "onClick: " + result);
                if (items[item].equals("Take Photo")) {
                    flag = 1;
                    userChooseTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    flag = 1;
                    userChooseTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("TTT", "onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            Log.v("TTT", "onActivityResult   RESULT_OK..........." + requestCode);
            if (requestCode == Constants.SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == Constants.REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Log.v("TTT", "onSelectFromGalleryResult..........." + data);
        try {
            selectedImageUri = data.getData();
            Log.v("TTT", "onSelectFromGalleryResult....selectedImageUri......." + selectedImageUri);
            strUserProfileImage = getRealPathFromURI(selectedImageUri);
            Log.v("TTT", "onSelectFromGalleryResult....PATH......." + strUserProfileImage);
            if (strUserProfileImage != null) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(ViewProfileActivity.this);
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(R.drawable.img_profile)
                        .showImageOnFail(R.drawable.img_profile)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgProfile, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        Log.v("TTT", "onSelectFromGalleryResult onLoadingFailed");
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Log.v("TTT", "onSelectFromGalleryResult onLoadingComplete........" + imageUri);

                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("TTT", uri + "  **Uri   " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
//                        BitmapDrawable background = new BitmapDrawable(loadedImage);
//                        imgProfile.setBackgroundDrawable(background);
                        imgProfile.setImageBitmap(loadedImage);

                    }

                });
            } else {
                strUserProfileImage = BitmapUtils.getPath(ViewProfileActivity.this, selectedImageUri);
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(ViewProfileActivity.this);
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(R.drawable.img_profile)
                        .showImageOnFail(R.drawable.img_profile)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgProfile, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("**Uri", uri + " " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
                        imgProfile.setImageBitmap(loadedImage);
//                        BitmapDrawable background = new BitmapDrawable(loadedImage);
//                        imgProfile.setBackgroundDrawable(background);

                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        try {
            if (data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(ViewProfileActivity.this, thumbnail);
                strUserProfileImage = getRealPathFromURI(tempUri);
                if (strUserProfileImage != null)
                    Log.v("path", "" + strUserProfileImage);
                ImageLoader imageLoader = ImageLoader.getInstance();
                Utility.initImageLoader(ViewProfileActivity.this);
                int fallback = 0;
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(fallback)
                        .showImageOnFail(fallback)
                        .showImageOnLoading(R.drawable.img_profile).build();
                imageLoader.displayImage("file://" + strUserProfileImage, imgProfile, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loadedImage = Utility.modifyOrientation(loadedImage, strUserProfileImage);
                        Uri uri = getImageUri(getApplicationContext(), loadedImage);
                        Log.i("**Uri", uri + " " + imageUri);
                        scaledFile = new File(getRealPathFromURI(uri));
                        imgProfile.setImageBitmap(loadedImage);
                    }

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean Read = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0) {
                    if (cameraAccepted && Read) {
                        if (userChooseTask.equals("Take Photo"))
                            cameraIntent();
                        else if (userChooseTask.equals("Choose from Library"))
                            galleryIntent();
                    }
                }
                break;
            case Constants.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    if (grantResults.length > 1) {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                            if (userChooseTask.equals("Take Photo"))
                                cameraIntent();
                            else if (userChooseTask.equals("Choose from Library"))
                                galleryIntent();
                        }
                    } else {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (userChooseTask.equals("Take Photo"))
                                cameraIntent();
                            else if (userChooseTask.equals("Choose from Library"))
                                galleryIntent();
                        }
                    }
                }
                break;
        }
    }

    TypedFile photoTypedFile = null;
    private void attemptEditProfile() {

        boolean cancel = false;
        View focusView = null;

        // Reset errors.

        edtMobileNumber.setError(null);
        if (TextUtils.isEmpty(edtMobileNumber.getText().toString()) || edtMobileNumber.getText().toString().length() < 10) {
            edtMobileNumber.setError(getString(R.string.error_field_required));
            focusView = edtMobileNumber;
            cancel = true;
        }

        edtDOB.setError(null);
        if (TextUtils.isEmpty(edtDOB.getText().toString())) {
            edtDOB.setError(getString(R.string.error_field_required));
            focusView = edtDOB;
            cancel = true;
        }

        mEmailView.setError(null);
        String email = mEmailView.getText().toString();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Utility.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        edtLastname.setError(null);
        if (TextUtils.isEmpty(edtLastname.getText().toString())) {
            edtLastname.setError(getString(R.string.error_field_required));
            focusView = edtLastname;
            cancel = true;
        }

        edtFirstname.setError(null);
        if (TextUtils.isEmpty(edtFirstname.getText().toString())) {
            edtFirstname.setError(getString(R.string.error_field_required));
            focusView = edtFirstname;
            cancel = true;
        }

        if (scaledFile != null) {
            photoTypedFile = new TypedFile("image/*", scaledFile);
        }
//        else {
//            photoTypedFile = null;
//            cancel = true;
//            Toast.makeText(ViewProfileActivity.this, "Select profile image", Toast.LENGTH_LONG).show();
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if(focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            Log.v("TTT", HomeActivity.user.getUser().getUserId() + " , " + email + " , " + edtFirstname.getText().toString() + " , "  + edtLastname.getText().toString() + " , "  + edtDOB.getText().toString() + " , " + edtMobileNumber.getText().toString()+ " , " + photoTypedFile);
            restInterface.editProfile(HomeActivity.user.getUser().getUserId(), email, edtFirstname.getText().toString(), edtLastname.getText().toString(), edtDOB.getText().toString(),
                    edtMobileNumber.getText().toString(), (imgPrivacy.getTag().equals("everyone") ? true : false), photoTypedFile, new Callback<UserModel>() {

                        @Override
                        public void failure(RetrofitError error) {
                            showProgress(false);
                            String message = error.getMessage();
                            try {
                                boolean IsInternetConnected = checkInternetisAvailable.getInstance(ViewProfileActivity.this).check_internet();
                                if (IsInternetConnected) {
                                    String msg = "";
                                    if (message != null) {
                                        msg = message;
                                    } else {
                                        msg = "Server Problem.";
                                    }

                                    if (!msg.contains("<!DOCTYPE html PUBLIC ")) {
                                        Utility.showAlert(ViewProfileActivity.this, "Error: " + msg);
                                    }
                                } else {
                                    Utility.showAlert(ViewProfileActivity.this, Constants.NO_INTERNET_MESSAGE);
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }

                        @Override
                        public void success(UserModel model, Response arg1) {
                            int code = model.getError_code();

                            if (code == 0) {
                                showProgress(false);
//                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                Gson gson = new Gson();
                                String userdata = new Gson().toJson(model);
                                HomeActivity.user.setUser(model.getUser());
                                PreferenceManager.putLoggedUser(userdata, ViewProfileActivity.this);

//                                PreferenceManager.putLogin(true, ViewProfileActivity.this);
//
//                                try {
//                                    Log.e("data", userdata);
//                                } catch (Exception e) {
//                                    Log.e("error", e.toString());
//                                }
//                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                finish();
                            } else {
                                showProgress(false);
                                Toast.makeText(getApplicationContext(), model.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_password, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_change_password);
//        menuItem.setIcon(android.R.drawable.ic_menu_edit);

//        menu.getItem(0).setIcon(getResources().getDrawable(android.R.drawable.ic_menu_edit));


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if(item.getItemId() == R.id.action_change_password){
            attemptEditProfile();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if(show){
            layoutProgress.setVisibility(View.VISIBLE);
        }else{
            layoutProgress.setVisibility(View.GONE);
        }

    }

    ListPopupWindow dropDownPopup;
    public void dropdownPopupWindow(final View anchor, final ArrayList<String> STRINGS) {
        Log.v("TTT", "dropdownPopupWindow......" + anchor.getTag());
        dropDownPopup = new ListPopupWindow(ViewProfileActivity.this);
        dropDownPopup.setVerticalOffset(1);
        dropDownPopup.setHorizontalOffset(1);
        dropDownPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_back));

        PopupDeviceWithSelectionAdapter adapter = new PopupDeviceWithSelectionAdapter(ViewProfileActivity.this, R.layout.popupmenu_withselection_back, STRINGS);
        dropDownPopup.setAdapter(adapter);
        dropDownPopup.setAnchorView(anchor);

        dropDownPopup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        dropDownPopup.setWidth(300);
//		anchor.post(new Runnable() {
//	        @Override
//	        public void run() {
//	        	dropDownPopup.getListView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//	        }
//	    });

        Log.v("TTT", "anchor......" + anchor.getTag());
        int position = 1;
        if(anchor.getTag().equals("everyone"))
            position = 1;
        else if(anchor.getTag().equals("self"))
            position = 2;

        adapter.setSelectedPosition(position);
        try {
            if(((anchor.getRight() - anchor.getLeft()) != 0) && (Utility.getWidestView(ViewProfileActivity.this, adapter) > (anchor.getRight() - anchor.getLeft()))){
                dropDownPopup.setWidth(Utility.getWidestView(ViewProfileActivity.this, adapter) + 100);
            }
            else{
                dropDownPopup.setWidth(anchor.getRight() - anchor.getLeft());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dropDownPopup.setWidth(300);
        }

        dropDownPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("TTT", "dropdownPopupWindow setOnItemClickListener......" + anchor.getTag());
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        anchor.setTag("everyone");
                        break;
                    case 2:
                        anchor.setTag("self");
                        break;

                    default:
                        break;
                }

                dropDownPopup.dismiss();
            }
        });

        dropDownPopup.show();
    }

}
