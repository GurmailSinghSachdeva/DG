package com.gurmail.singh.discountgali.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.NoConnectionError;
import com.gurmail.singh.discountgali.R;
import com.gurmail.singh.discountgali.activity.LocalDealsActivty;
import com.gurmail.singh.discountgali.activity.OfferDetailActivity;
import com.gurmail.singh.discountgali.activity.OfferOnlineStoresActivity;
import com.gurmail.singh.discountgali.activity.SearchItemActivity;
import com.gurmail.singh.discountgali.fragment.CustomSharingDialogFragment;
import com.gurmail.singh.discountgali.model.ModelTopStores;
import com.gurmail.singh.discountgali.model.TopOffers;
import com.gurmail.singh.discountgali.network.api.APIException;
import com.gurmail.singh.discountgali.utils.Constants;
import com.gurmail.singh.discountgali.utils.DialogUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AND-02 on 03-08-2016.
 */
public class Utils {
    private static Context mContext;

    public static void setApplicationContext(Context context) {
        if (mContext == null)
            mContext = context;
    }
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getSha512(String salt, String password) {
        String generatedPassword = null;
        try {
            salt = salt + password;
//            Syso.print("data for sha 512 >>> "+salt);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
//            Syso.print("output for sha 512 >>> "+generatedPassword);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static void showCustomSharingFragment(FragmentActivity activity, String content, String title) {
        CustomSharingDialogFragment fragment = new CustomSharingDialogFragment();
        if (title == null || title.trim().isEmpty())
            title = "Share";
        Bundle args = new Bundle();
        args.putString("content", content);
        args.putString("title", title);
        fragment.setArguments(args);
        fragment.show(activity.getSupportFragmentManager(), CustomSharingDialogFragment.TAG);
    }

    boolean isAesEnabled = true;

    public static String getPassword(String email, String password) {
//        AES.setKey(email);
//        AES.encrypt(password);
//        System.out.println("Encrypted: " + AES.getEncryptedString());
//        AES.decrypt(AES.getEncryptedString());
//        System.out.println("Decrypted : " + AES.getDecryptedString());
//        return AES.getEncryptedString();
        String key = getSha512(email, email);
        String key2 = key.substring(0, 64);
        String newPass = getSha512(password, key2);
        Syso.print("--------- email key :" + key + "\n key2 : " + key2 + "\n newPass :" + newPass);
        return newPass;
    }

    public static long getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

//    public static void showImage(Context context, String url, ImageView imageView, int placeHolder) {
//        if (!TextUtils.isEmpty(url)) {
//            Picasso.with(context).load(url).placeholder(placeHolder).error(placeHolder).into(imageView);
//        } else {
//            Picasso.with(context).load(placeHolder).placeholder(placeHolder).error(placeHolder).into(imageView);
//        }
//    }


//    public static void showImage(Context context, int res, ImageView imageView) {
//        Picasso.with(context).load(res).into(imageView);
//    }
//
//    public static void showCircularImage(Context context, String url, ImageView imageView, int placeHolder) {
//        if (!TextUtils.isEmpty(url))
//            Picasso.with(context).load(url).placeholder(placeHolder).error(placeHolder).transform(new CircleTransform()).fit().into(imageView);
//        else
//            Picasso.with(context).load(placeHolder).transform(new CircleTransform()).into(imageView);
//    }
//
//    public static void showCircularImage(Context context, String url, ImageView imageView) {
//        if (!TextUtils.isEmpty(url))
//            Picasso.with(context).load(url).placeholder(R.drawable.profile).error(R.drawable.profile).transform(new CircleTransform()).into(imageView);
//        else
//            Picasso.with(context).load(R.drawable.profile).transform(new CircleTransform()).into(imageView);
//    }
//
//    public static boolean isValidEmail(String email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    public static void showUnderDevelopmentAlert(Context context) {
//        new AlertDialog.Builder(context)
//                .setTitle(R.string.title_alert)
//                .setMessage(R.string.alert_under_development)
//                .setCancelable(false)
//                .setPositiveButton(R.string.tv_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                }).create().show();
//    }
public static void showSoftKeyboard(Activity context) {
    try {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            Syso.print("--------- open keyboard 1 :");
        }


    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void hideSoftKeyBoard(Activity context, View view) {
        try {
//            View view = context.getCurrentFocus();

            view.requestFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean value = imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Syso.print("--------- close keyboard 1 : " + value);
            }
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDeviceID()
    {
        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return unique_id;
    }
    public static void hideSoftKeyBoard2(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hidKeyBoard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(((Activity) context)
                    .getCurrentFocus().getWindowToken(), 0);

            ((Activity) context).getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }
    }


    public interface SpanClickListenerI {
        void onClick(String name, int  pos);
    }
    public static Spannable getSpanString(String content, String[] roots, SpanClickListenerI hashUserClickListener) {

        try {

            Spannable sp = null;
            if (content != null) {
                String rootStr = "" + content + " ";
                String roastStrSmallCases = "" + content.toLowerCase() + " ";
                sp = new SpannableString(rootStr);

                int fromIndex;
                for (int i=0; i<roots.length; i++) {
                    String rt = roots[i];
                    fromIndex = 0;
                    int index = roastStrSmallCases.indexOf(rt.toLowerCase(), fromIndex);

                    while (index != -1) {
                        fromIndex = index + rt.toLowerCase().length();
                        // TODO: do whole word check
                        UserHashClickableSpan span = new UserHashClickableSpan(Color.rgb(222, 95, 116), rt, i, hashUserClickListener);
                        sp.setSpan(span, index, fromIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                        index = roastStrSmallCases.indexOf(rt.toLowerCase(), fromIndex);
                    }

                }


            }

            return sp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SpannableString(content);
    }

//    public static String getImgUrl(Contact contact) {
//        //Syso.print("contact.getImageURL() "+contact.getImageURL());
//        return contact.getImageURL();
//    }
//
//    public static enum Status {
//        Online("Online", 0, R.drawable.ic_green_dot), Offline("Offline", 1, R.drawable.ic_grey_dot), Away("Away", 2, R.drawable.ic_yellow_dot), Invisible("Invisible", 3, R.drawable.ic_grey_dot), DoNotDisturb("Do Not Disturb", 4, R.drawable.ic_red_dot);
//
//        private final int position, drawable;
//        private String value;
//
//        private Status(String value, int position, int drawable) {
//            this.position = position;
//            this.drawable = drawable;
//            this.value = value;
//        }
//
//        public static String getName(int position) {
//            Status[] values = values();
//            for (Status type : values) {
//                if (type.position == position) {
//                    return type.value;
//                }
//            }
//            return null;
//        }

//        public static int getDrawable(int position) {
//            Status[] drawable = values();
//            for (Status type : values()) {
//                if (type.drawable == position) {
//                    return type.drawable;
//                }
//            }
//            return -1;
//        }

//        public static String[] getValues() {
//            String[] strings = new String[values().length];
//            int pos = 0;
//            for (Status type : values()) {
//                strings[pos] = type.value;
//                pos++;
//            }
//            return strings;
//        }
//    }

    public static int getInt(String value) {
        try {
            if (!TextUtils.isEmpty(value))
                return Integer.parseInt(value);
            else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDate(String time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            return sdf.format(new Date(Utils.getLong(time)));
        } else return "";
    }

    public static String getDateForMessage(String time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            return sdf.format(new Date(Utils.getLong(time)));
        } else return "";
    }

    public static String getRecentDate(String time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM hh:mm aa");
            return sdf.format(new Date(Utils.getLong(time)));
        } else return "";
    }

    public static long getLong(String time) {
        try {
            if (!TextUtils.isEmpty(time))
                return Long.parseLong(time);
            else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public static String getToken() {
//        String token = FirebaseInstanceId.getInstance().getToken();
//        Syso.print("************* token >" + token);
//        if (token != null) return token;
//        else return "";
//    }

    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Syso.print("Coulumn name :" + cursor.getColumnName(i));
                Syso.print("Coulumn type :" + cursor.getType(i));
                try {
                    Syso.print("Coulumn value :" + cursor.getString(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getRealPathFromURINew(Uri contentURI, Context context) {
        String result = null;
        /*Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                for(int i=0;i<cursor.getColumnCount();i++)
                {
                    Syso.print("Coulumn name :"+cursor.getColumnName(i));
                    Syso.print("Coulumn type :"+cursor.getType(i));
                    try {
                        Syso.print("Coulumn value :" + cursor.getString(i));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                int idx = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                result = cursor.getString(idx);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;*/
        //return contentURI.getPath();
        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(contentURI);

// Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        //String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        //if (cursor.moveToFirst()) {
        result = cursor.getString(columnIndex);
        Syso.print(" result 111111" + result);
        //}

        cursor.close();
        Syso.print(" result " + result);
        return result;
    }


    public static String encodeToNonLossyAscii(String original) {
        Charset asciiCharset = Charset.forName("US-ASCII");
        if (asciiCharset.newEncoder().canEncode(original)) {
            return original;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c < 128) {
                stringBuffer.append(c);
            } else if (c < 256) {
                String octal = Integer.toOctalString(c);
                stringBuffer.append("\\");
                stringBuffer.append(octal);
            } else {
                String hex = Integer.toHexString(c);
                stringBuffer.append("\\u");
                stringBuffer.append(hex);
            }
        }
        return stringBuffer.toString();
    }

    private static final Pattern UNICODE_HEX_PATTERN = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
    private static final Pattern UNICODE_OCT_PATTERN = Pattern.compile("\\\\([0-7]{3})");

    public static String decodeFromNonLossyAscii(String original) {
//        original="Hi there\\ud83d\\ude97\\ud83d\\ude97\\ud83d\\ude97";
        if (TextUtils.isEmpty(original))
            return "";

        Matcher matcher = UNICODE_HEX_PATTERN.matcher(original);
        StringBuffer charBuffer = new StringBuffer(original.length());
        while (matcher.find()) {
            String match = matcher.group(1);
            char unicodeChar = (char) Integer.parseInt(match, 16);
            matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
        }
        matcher.appendTail(charBuffer);
        String parsedUnicode = charBuffer.toString();

        matcher = UNICODE_OCT_PATTERN.matcher(parsedUnicode);
        charBuffer = new StringBuffer(parsedUnicode.length());
        while (matcher.find()) {
            String match = matcher.group(1);
            char unicodeChar = (char) Integer.parseInt(match, 8);
            matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
        }
        matcher.appendTail(charBuffer);
        return charBuffer.toString();
    }

    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

//    public static void setSecuredContactsStatus(Context context, View view, String status) {
//        int drawable = R.drawable.ic_green_dot;
//        if (!TextUtils.isEmpty(status)) {
//            switch (status) {
//                case "Online":
//                    drawable = R.drawable.ic_green_dot;
//                    break;
//                case "Offline":
//                    drawable = R.drawable.ic_grey_dot;
//                    break;
//                case "Away":
//                    drawable = R.drawable.ic_yellow_dot;
//                    break;
//                case "Invisible":
//                    drawable = R.drawable.ic_grey_dot;
//                    break;
//                case "Do Not Disturb":
//                    drawable = R.drawable.ic_red_dot;
//                    break;
//                default:
//                    drawable = R.drawable.ic_green_dot;
//                    break;
//            }
//        }
//        view.setBackgroundResource(drawable);
//    }
//
//    public static void dumpIntent(Intent i) {
//        String LOG_TAG = ">>>> dumping intent ";
//        if (i != null) {
//            Bundle bundle = i.getExtras();
//            if (bundle != null) {
//                Set<String> keys = bundle.keySet();
//                Iterator<String> it = keys.iterator();
//                Log.e(LOG_TAG, "Dumping Intent start");
//                while (it.hasNext()) {
//                    String key = it.next();
//                    Log.e(LOG_TAG, "[" + key + "=" + bundle.get(key) + "]");
//                }
//                Log.e(LOG_TAG, "Dumping Intent end");
//            } else {
//                Log.e(LOG_TAG, "bundle Null  ");
//            }
//        } else {
//            Log.e(LOG_TAG, "Null Intent ");
//        }
//    }
//

    ////////////// AMIT SAINI///////////
    public static String createStorageDirectory() {
        File basePath = Environment.getExternalStorageDirectory();
        File file = new File(basePath, ".SecuredComm");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        Syso.print("file.getAbsolutePath()  " + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public static String getFileName(String Url) {
        String temp[] = Url.split("/");
        if (temp.length > 1)
            return temp[temp.length - 1];
        return System.currentTimeMillis() + "";
    }

    public static File createFile(String Url) {
        return new File(createStorageDirectory(), getFileName(Url));
    }

    public static String getFilePath(String fileName) {
        return createStorageDirectory() + "/" + fileName;
    }

    public static void playMusicPlayer(Context context, String audioUrl) {
        Uri audioUri = Uri.parse(audioUrl);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(audioUri, "audio/*");
            context.startActivity(intent);
        } catch (Exception e) {
            AlertUtils.showToast(context, "No audio player found in this device");
            e.printStackTrace();
        }
    }

    public static void playVideoPlayer(Context context, String videoUrl) {
        Uri videoUri = Uri.parse(videoUrl);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(videoUri, "video/*");
            context.startActivity(intent);
        } catch (Exception e) {
            AlertUtils.showToast(context, "No video player found in this device");
            e.printStackTrace();
        }
    }

    public static void startCall(Context context, String phoneNo) {
        // if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
        context.startActivity(intent);
        // }
    }

    public static void sendSMSMessage(Context context, String phoneNo, String message) {
        //NOTE: Implicit message sending
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            AlertUtils.showToast(context, "SMS sent.");
        } catch (Exception e) {
            AlertUtils.showToast(context, "SMS faild, please try again.");
            e.printStackTrace();
        }
    }

    public static void sendSMS(Context context, String phoneNo, String message) {
        //NOTE: Explicit message sending
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String(phoneNo));
        smsIntent.putExtra("sms_body", message);

        try {
            context.startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            AlertUtils.showToast(context, "SMS faild, please try again.");
        }
    }



    public static String checkPasswordStrength(String str) {
        int strLength = str.length();
        if (strLength < 8) {
            Syso.print("too_short");
            return "too_short";
        } else if (strLength > 20) {
            Syso.print("too_long");
            return "too_long";
        } else if (!Pattern.matches("/\\d/", str)) {
            Syso.print("no_num");
            return "no_num";
        } else if (!Pattern.matches("/[a-z]/", str)) {
            Syso.print("no_lowercase");
            return "no_lowercase";
        } else if (!Pattern.matches("/[A-Z]/", str)) {
            Syso.print("no_uppercase");
            return "no_uppercase";
        } else if (!Pattern.matches("/[!@#\\$%\\^\\&*\\)\\(+=._-]/", str)) {
            Syso.print("bad_char");
            return "bad_char";
        } else if (strLength > 6 && !Pattern.matches("/[a-z]/", str) && !Pattern.matches("/[A-Z]/", str) && !Pattern.matches("/[!@#\\$%\\^\\&*\\)\\(+=._-]/", str)) {
            Syso.print("very_strong");
            return "very_strong";
        }
        Syso.print("oukey!!");
        return "strong";
    }


//    public static String getFontSize(int type) {
//        switch (type) {
//            case Constants.FontSize.Small:
//                return "Small";
//            case Constants.FontSize.Medium:
//                return "Medium";
//            case Constants.FontSize.Large:
//                return "Large";
//            case Constants.FontSize.ExtraLarge:
//                return "Extra Large";
//            default:
//                return "Medium";
//        }
//    }
//
//
//    public static int getFontSizeId(int type) {
//        switch (type) {
//            case Constants.FontSize.Small:
//                return R.style.FontStyle_Small;
//            case Constants.FontSize.Medium:
//                return R.style.FontStyle_Medium;
//            case Constants.FontSize.Large:
//                return R.style.FontStyle_Large;
//            case Constants.FontSize.ExtraLarge:
//                return R.style.FontStyle_ExtraLarge;
//            default:
//                return R.style.FontStyle_Medium;
//        }
//    }
//

    ////////////// AMIT SAINI///////////

    @SuppressLint("SimpleDateFormat")
    public static String getDaysDifference(Long inputDate) {
        try {
            long difference = (System.currentTimeMillis() / 1000L) - inputDate;
            long hour = TimeUnit.MILLISECONDS.toHours(difference);
            Syso.print("difference  "+difference+"   hour "+ hour);

            if (hour > 24) {
                return TimeUnit.MILLISECONDS.toDays(difference)+" day ago";
            }/*else if(hour < 24){
                return hour+" hour ago";
            }*/else if (hour == 0) {
                long min = TimeUnit.MILLISECONDS.toMinutes(difference);
                if (min <= 0)
                    return 1 + " min ago";
                else if (min <= 1)
                    return 1 + " min ago";
                else {
                    return min + " mins ago";
                }
            }

            if (hour <= 0)
                return 1 + " hour ago";
            else if (hour <= 1)
                return hour + " hour ago";
            else
                return hour + " hours ago";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputDate.toString();
    }

    public static String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();
        return DateUtils.getRelativeTimeSpanString(smsTimeInMilis,now.getTimeInMillis(),DateUtils.MINUTE_IN_MILLIS).toString();
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);

        return cal;                                  // return the date part
    }


    public static String getDate(long timeStamp){
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    public static String dateFormat(String startDateString, String formatfrom, String formatIn) {
        SimpleDateFormat form = new SimpleDateFormat(formatfrom);
        Date date1 = null;
        try {
            date1 = form.parse(startDateString);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat(formatIn);
        return postFormater.format(date1);
    }

    ///// ping to to any url////////////////
    /*public static int count= 0;
    public static void ping(final Context context, String host){
        boolean isReachable = false;
        PingParameters  pingParameters = new PingParameters(context, host, new PingCallback() {
            @Override
            public void success() {
                Syso.print("connection made is success");
            }

            @Override
            public void fail() {
                Syso.print("connection made is fail");
                pingGoogle(context,"https://www.google.com/");
            }
        });
        new Ping().execute(pingParameters);
    }

    public static void pingGoogle(final Context context, final String host){
        boolean isReachable = false;
        PingParameters  pingParameters = new PingParameters(context, host, new PingCallback() {
            @Override
            public void success() {
                Syso.print("connection made is success");
                pingFailureForOne(context,host);
            }

            @Override
            public void fail() {
                Syso.print("connection made is fail");
            }
        });
        new Ping().execute(pingParameters);
        count++;
    }

    public static void pingFailureForOne(Context context, String host){
        if(count >3){
            ping(context,host);
        }else{
            String message = "Internet connection is available but Text is experiencing delay connecting to SC server. If this issue persists, please contact support@securedcommunications.com.";
            AlertUtils.showToast(context,message);
            count = 0;
        }
    }

    public static void pingFailureForBoth(Context context){
        String message = "Text not able to reach secured servers as there is no internet connection. Please check your Wi-Fi, internet connection and try again.";
        AlertUtils.showToast(context,message);
    }


    public static class PingParameters {
        Context context;
        String host;
        PingCallback ping;

        PingParameters(Context context,String host,PingCallback ping){
            this.context = context;
            this.host = host;
            this.ping = ping;
        }
    }*/
    ///////////////////////////////////////////

    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
            status = activeNetworkInfo.isAvailable();
        return status;
    }

    public static boolean isNumber(String text){
        String regexStr = "^[0-9]*$";
        return  text.matches(regexStr);
    }

    private static String version;

    public static String getAppVersion(Context context){
        try {
            if(version!=null)
                return version;
            else {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                version =  pInfo.versionName;
                return version;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void handleError(Exception e, Context context) {
        handleError(e, context, null);
    }
    public static void handleErrorString(String e, Context context) {
        handleError(e, context, null);
    }

    public static void handleError(Exception e, Context context, Runnable runnable) {
        handleError(e, context, null, runnable);
    }

    public static void handleError(String message, final Context context,Runnable runnable)
    {
        DialogUtils.showAlert(context, message, runnable);
    }
    public static void handleError(Exception e, final Context context, String alternateMsg, Runnable runnable) {

        if (!Utils.isNetworkAvailable(context)) {
            DialogUtils.showAlert(context, context.getString(R.string.alert_no_network));
            return;
        }
        //   String msg = "No internet found. Check your connection or try again"; //"Unable to complete request. Please try again later.";
//        String msg = "Operation could not be completed due to some problem. We are trying hard to resolve the same. Your inconvenience is regretted.";
        String msg = "No Deals Available";

        if (alternateMsg != null && !alternateMsg.isEmpty())
            msg = alternateMsg;
        if (e != null) {
            if (e instanceof APIException) {
                APIException apiException = (APIException) e;

                // User Blocked
                if (apiException.getCode().equalsIgnoreCase(Constants.ServerCodes.USER_BLOCKED)) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
//                            doLogout((Activity) context);
                        }
                    };

                    // If User not logged in
                    //if(context instanceof LoginActivity || context instanceof RecoverActivity)
//                    if (SharedPreference.getMyUserId() == null)
//                        r = null;

                    // @ATUL
                    DialogUtils.showAlert(context, "Your account has been blocked by admin.", r);
                    //DialogUtils.showAlert(context, "Please try after some time.", "Server Under Maintenance", r);
                    return;
                }

                // Else
                if (apiException.getMessage() != null && !apiException.getMessage().trim().isEmpty()) {
                    msg = apiException.getMessage();
                }
            }
        }
        if(context instanceof Activity && !((Activity)context).isFinishing())
        DialogUtils.showAlert(context, msg, runnable);
    }


    private static Activity curContext = null;

    public static void setCurContext(Activity curContext) {
        Utils.curContext = curContext;
    }

    public static Activity getCurContext() {
        return curContext;
    }
    public static void navigatePlayStore() {
        final String appPackageName = Utils.getCurContext().getPackageName();
        try {
            Utils.getCurContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            Utils.getCurContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getAppStoreURL() {
        return "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
    }
    public static Context getApplicationContext() {
        return mContext;
    }

    public static boolean isNoInternetException(Exception e) {
        return e instanceof NoConnectionError;
    }

    public static void showOfferDetailActivity(Activity context, TopOffers topOffers) {
        if (context != null) {
            Intent intent = new Intent(context, OfferDetailActivity.class);
            intent.putExtra("offer", topOffers);
            context.startActivityForResult(intent, Constants.ACTIVITYFORRESULT.REQUESTOFFERDETAIL);
            context.overridePendingTransition(0, 0);
        }
    }

    public static void showOffersOnlineStoresActivity(Activity context, ModelTopStores modelTopStores) {
        if (context != null) {
            Intent intent = new Intent(context, OfferOnlineStoresActivity.class);
            intent.putExtra("store", modelTopStores);
            context.startActivity(intent);
        }

    }

    public static void showLocalDealsActivity(Activity context, String cityName, int categoryId) {

        if (context != null) {
            Intent intent = new Intent(context, LocalDealsActivty.class);
            intent.putExtra("cityId", cityName);
            intent.putExtra("categoryId", categoryId);
            context.startActivity(intent);
        }
    }

    public static void showSearchActivity(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, SearchItemActivity.class);
            context.startActivity(intent);
        }
    }


    public static void openUrl(Context context, String url) {
        if (context != null && url != null) {
            try {
                if (!url.startsWith("https://") && !url.startsWith("http://")) {
                    url = "http://" + url;
                }
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                //intent.setType("text/html");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
