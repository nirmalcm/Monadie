package com.spectrographix.monadie.utility;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.spectrographix.monadie.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static final String DATE_TIME_FORMAT_STAMP = "ddMMyyyyHHmmss";
    public static final String DATE_TIME_FORMAT_TIMER = "dd.MM.yyyy, HH:mm:ss";
    public static final String DATE_TIME_FORMAT_CLASSIC = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_MULTILINE = "dd/MM/yyyy\nHH:mm:ss";

    public Utility()
    {
    }

    public static void utilTest()
    {
        String partialEmail = hideEmailPartially("nirmalcm.sg@gmail.com");

        String dateNow = convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(getCurrentTimeStamp()),DATE_TIME_FORMAT_STAMP);

        String timeStampNow = convertSimpleDateTimeFormatStringToTimeStampString(dateNow,DATE_TIME_FORMAT_STAMP);

        String dateNowFromTimeStampNow = convertTimeStampStringToSimpleDateTimeFormatString(timeStampNow,DATE_TIME_FORMAT_STAMP);

        String calendarNow = convertCalendarToTimeStampString(Calendar.getInstance(),DATE_TIME_FORMAT_CLASSIC);
    }

    public static final int getStringArraySize(final String stringArray) {
        String[] membersArray = stringArray.split(",");
        return membersArray.length;
    }

    private void requestFocus(View view, Context context) {
        if (view.requestFocus()) {
            //(Activity)context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void applyFonts(final View v, Typeface fontToSet)
    {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    applyFonts(child, fontToSet);
                }
            } else if (v instanceof TextView) {
                ((TextView)v).setTypeface(fontToSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    public static void setFontSize(final View v, Float fontSize)
    {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setFontSize(child, fontSize);
                }
            } else if (v instanceof TextView) {
                ((TextView)v).setTextSize(TypedValue.COMPLEX_UNIT_DIP,fontSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String hideEmailPartially(String email)
    {
        return email.replaceAll("(?<=..).(?=...*@)", "*");
    }

    public static void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }

    public static void setVisibility(int visibility, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setVisibility(visibility);
            if (child instanceof ViewGroup){
                setVisibility(visibility, (ViewGroup)child);
            }
        }
    }
    public static void disableView(LinearLayout linearLayout)
    {
        for (int k = 0; k < linearLayout.getChildCount(); k++) {
            View child = linearLayout.getChildAt(k);
            child.setEnabled(false);
        }
    }

    public static void enableView(LinearLayout linearLayout)
    {
        for (int k = 0; k < linearLayout.getChildCount(); k++) {
            View child = linearLayout.getChildAt(k);
            child.setEnabled(true);
        }
    }
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static String convertTimeStampStringToSimpleDateTimeFormatString(String timestamp, String format)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.valueOf(timestamp)* 1000L);
        String dateAndTime = DateFormat.format(format, cal).toString();
        return dateAndTime;
    }

    public static String convertSimpleDateTimeFormatStringToTimeStampString(String simpledateTimeFormat, String format)
    {
        long ts = 0L;
        Timestamp timestamp;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date parsedDate = dateFormat.parse(simpledateTimeFormat);
            timestamp = new Timestamp(parsedDate.getTime());
            ts = timestamp.getTime()/1000L;
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        return String.valueOf(ts);
    }

    public static String convertCalendarToTimeStampString(Calendar calendar, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDateandTime = sdf.format(calendar.getTime());

        return currentDateandTime;

    }

    public static String getCurrentDateTimeInString(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }

    public static long getDifferenceInDateAndTimeInTimeStamp(long startDateAndTimeInTimeStamp, long endDateAndTimeInTimeStamp)
    {
        String startDateAndTime =convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(startDateAndTimeInTimeStamp),"dd.MM.yyyy, HH:mm:ss");
        String endDateAndTime =convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(endDateAndTimeInTimeStamp),"dd.MM.yyyy, HH:mm:ss");

        long remainingTime = 0;

        Date startDate;
        Date endDate;

        long startLong;
        long endLong;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        try {
            startDate = formatter.parse(startDateAndTime);
            endDate = formatter.parse(endDateAndTime);
            startLong = startDate.getTime();
            endLong = endDate.getTime();
            remainingTime = endLong - startLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remainingTime;
    }

    public static long getDifferenceInDateAndTimeInString(String startDateAndTime, String endDateAndTime)
    {
        long remainingTime = 0;

        Date startDate;
        Date endDate;

        long startLong;
        long endLong;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        try {
            startDate = formatter.parse(startDateAndTime);
            endDate = formatter.parse(endDateAndTime);
            startLong = startDate.getTime();
            endLong = endDate.getTime();
            remainingTime = endLong - startLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remainingTime;
    }

    public String timeCalculate(double ttime) {

        long days, hours, minutes, seconds;
        String daysT = "", restT = "";

        days = (Math.round(ttime) / 86400);
        hours = (Math.round(ttime) / 3600) - (days * 24);
        minutes = (Math.round(ttime) / 60) - (days * 1440) - (hours * 60);
        seconds = Math.round(ttime) % 60;

        if(days==1) daysT = String.format("%d day ", days);
        if(days>1) daysT = String.format("%d days ", days);

        restT = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return daysT + restT;
    }

    public String getRealPathFromURI(Context context,Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public String getRealPathFromURI2(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public final static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);

        /*if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }*/
        return resizedBitmap;
    }

    public void accountPopUp(View v, Context context) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.mygroups_activity_my_groups_popup_add_group,(ViewGroup)v.findViewById(R.id.popup));
            // create a 300px width and 470px height PopupWindow
            final PopupWindow popupWindow = new PopupWindow(layout, 700,
                    900, true);

            // display the popup in the center
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);


            TextView userEmail = (TextView) layout.findViewById(R.id.userEmail);
            userEmail.setText(new SessionManager(context).getUserEmail());
            Button cancelButton = (Button) layout.findViewById(R.id.cancelPopup);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
