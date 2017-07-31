package utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dating.coolmatch.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Util {
    public static String NO_INTERNET_MESSAGE = "Internet Connection is not available.";
    public static void showAlert(Context context, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String getLeftTime(String challangeDateTime) {
        Log.v("TTT", "getLeftTime...." + challangeDateTime);
        String str = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = new Date();
            Calendar cal = Calendar.getInstance();
            try {
                convertedDate = dateFormat.parse(challangeDateTime);
                cal.setTime(dateFormat.parse(challangeDateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(TimeZone.getTimeZone("UTC"));
            long diff = currentTime.getTimeInMillis() - cal.getTimeInMillis();
            long second = (long) diff / 1000;
            long minute = (long) second / 60;
            long hours = (long) minute / 60;
            long days = hours / 24;

            if (days < 1) {
                if (second >= 0 || second < 0) {
                    if (second == 1) {
                        str = second + " s ago";
                    } else if (second > 1) {
                        str = second + " s(s) ago";
                    } else {
                        str = "Now";
                    }
                }
                if (minute > 0) {
                    if (minute == 1) {
                        str = minute + " m ago";
                    } else {
                        str = minute + " m(s) ago";
                    }
                }
                if (hours > 0) {
                    if (hours == 1) {
                        str = hours + " h ago";
                    } else {
                        str = hours + " h(s) ago";
                    }
                }
            } else if (second < 604800) // 30 * 24 * 60 * 60
            {
                str = days + " days ago";
            } else {
                str = new SimpleDateFormat("dd, MMM yyyy").format(convertedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getLeftTimeMessage(String challangeDateTime) {
        String str = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = new Date();
            Calendar cal = Calendar.getInstance();
            try {
                convertedDate = dateFormat.parse(challangeDateTime);
                cal.setTime(dateFormat.parse(challangeDateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(TimeZone.getTimeZone("UTC"));
            long diff = currentTime.getTimeInMillis() - cal.getTimeInMillis();// convertedDate.getTime()
            long second = (long) diff / 1000;
            long minute = (long) second / 60;
            long hours = (long) minute / 60;
            long days = hours / 24;

            if (days < 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                String time1 = sdf.format(convertedDate);
                str = time1;
            } else {
                str = new SimpleDateFormat("dd, MMM yyyy").format(convertedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public static void setWhiteToolBar(Toolbar toolbar, Context ctx, int color) {
        Drawable upArrow = ctx.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
    }
}
