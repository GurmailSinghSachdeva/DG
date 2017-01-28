package com.example.lenovo.discountgali.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lenovo.discountgali.activity.SearchItemActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 24-01-2017.
 */


public class SharedPreference {
    // Shared Pref Name
    private static final String SHARED_PREF_NAME = "dt-ro";

    private static final String ROAST_ID_DEFAULT = "shared_roast_id_default";
    private static final String NEWS_ID_DEFAULT = "shared_news_id_default";
    // Shared Pref Keys
    private static final String KEY_MY_PROFILE = "KEY_MY_PROFILE";
    private static final String KEY_MY_USER_ID = "KEY_MY_USER_ID";
    private static final String KEY_IS_PROFILE_SELECTION_DONE = "KEY_IS_PROFILE_SELECTION_DONE";
    private static final String KEY_IS_SOCIAL_LOGIN = "KEY_IS_SOCIAL_LOGIN";
    private static final String KEY_SEARCHING_KEYWORDS = "searching_keywords_cache";
    private static final String KEY_IS_TRANSITION_ENABLED = "KEY_IS_TRANSITION_ENABLED";
    private static final String KEY_SOCIAL_ID_LINK_STATUS = "KEY_SOCIAL_ID_LINK_STATUS_";
    private static final String KEY_IS_TUTORIAL_SHOWN = "KEY_IS_TUTORIAL_SHOWN";
    private static final String KEY_IS_APP_UPDATE_API_CALLED = "KEY_IS_APP_UPDATE_API_CALLED";
    private static final String KEY_SERVER_VERSION_CODE = "KEY_SERVER_VERSION_CODE";
    private static final String KEY_IS_FORCE_UPDATE_AVAILABLE = "KEY_IS_FORCE_UPDATE_AVAILABLE";
    private static final String KEY_COMPAIGN_TRACKING_URL = "KEY_COMPAIGN_TRACKING_URL";
    private static final String KEY_HASH_TAG="KEY_HASH_TAG";
    private static final String KEY_CATEGORY_LIST="KEY_CATEGORY_LIST";
    private static final String KEY_OTP_STATUS="KEY_OTP_STATUS";

    private static final String KEY_DEFAULT_ROAST_ID = "KEY_DEFAULT_ROAST_ID";
    private static final String KEY_DEFAULT_NEWS_ID = "KEY_DEFAULT_NEWS_ID";



    //database utility
//
//    public static void saveMyProfile(User_2 user) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            if (user == null) {
//                editor.remove(KEY_MY_PROFILE);
//                editor.remove(KEY_MY_USER_ID);
//            } else {
//                editor.putString(KEY_MY_PROFILE, ObjectSerializer.serialize(user));
//                editor.putString(KEY_MY_USER_ID, user.id);
//                editor.putString(KEY_OTP_STATUS,user.otp_status);
//            }
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static User_2 getMyProfile() {
//        User_2 user_2 = null;
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            user_2 = (User_2) ObjectSerializer
//                    .deserialize(sPreferences.getString(KEY_MY_PROFILE, ""));
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return user_2;
//    }
//
//
//    public static void saveMyProfilePicUrl(String profile_pic_url) {
//        try {
//            User_2 user = getMyProfile();
//            if (user != null) {
//                user.image_url = profile_pic_url;
//            }
//            saveMyProfile(user);
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//    }
//
//    public static void saveMyProfileVerified(boolean is_verified) {
//        try {
//            User_2 user = getMyProfile();
//            if (user != null && user.is_verified != is_verified) {
//                user.is_verified = is_verified;
//                saveMyProfile(user);
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//    }
//
//    public static void saveMyUserId(String user_id) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            if (user_id == null) {
//                editor.remove(KEY_MY_USER_ID);
//            } else {
//                editor.putString(KEY_MY_USER_ID, user_id);
//            }
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static String getKeyOtpStatus()
//    {
//        try {
//            /*User_2 user = getMyProfile(mContext);
//            if(user == null)
//                return null;
//            else
//                return user.id;*/
//
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getString(KEY_OTP_STATUS, null);
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return null;
//    }
//    public static String getMyUserId() {
//        try {
//            /*User_2 user = getMyProfile(mContext);
//            if(user == null)
//                return null;
//            else
//                return user.id;*/
//
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getString(KEY_MY_USER_ID, null);
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return null;
//    }
//
//    public static boolean checkIfRoastIdSharedPrefernceExist()
//    {
//        //boolean a=false;
//        String path="/data/data/roastmobileapp.com.roastproject/shared_prefs/shared_roast_id_default.xml";
//        File file=new File(path);
//        if(file.exists())
//            return true;
//        else
//            return false;
//
//    }
//
//    public static int getRoastIdDefault()
//    {
//        SharedPreferences preferences = Utils.getApplicationContext().getSharedPreferences(ROAST_ID_DEFAULT,0);
//        return preferences.getInt(KEY_DEFAULT_ROAST_ID,-1);
//
//    }
//
//    public static void updateRoastId(int a)
//    {
//        try {
//            SharedPreferences preferences = Utils.getApplicationContext().getSharedPreferences(ROAST_ID_DEFAULT,0);
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putInt(KEY_DEFAULT_ROAST_ID,a);
//            editor.commit();
//        }catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//
//    public static boolean checkIfNewsIdSharedPrefernceExist()
//    {
//        //boolean a=false;
//        String path="/data/data/roastmobileapp.com.roastproject/shared_prefs/shared_news_id_default.xml";
//        File file=new File(path);
//        if(file.exists())
//            return true;
//        else
//            return false;
//
//    }
//
//    public static int getNewsIdDefault()
//    {
//        SharedPreferences preferences = Utils.getApplicationContext().getSharedPreferences(NEWS_ID_DEFAULT,0);
//        return preferences.getInt(KEY_DEFAULT_NEWS_ID,-1);
//
//    }
//
//    public static void updateNewsId(int a)
//    {
//        try {
//            SharedPreferences preferences = Utils.getApplicationContext().getSharedPreferences(NEWS_ID_DEFAULT,0);
//            SharedPreferences.Editor editor=preferences.edit();
//            editor.putInt(KEY_DEFAULT_NEWS_ID,a);
//            editor.commit();
//        }catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//    public static void saveIsProfileSelectionDone(boolean isProfileSelectionDone) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_PROFILE_SELECTION_DONE, isProfileSelectionDone);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static boolean isProfileSelectionDone() {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getBoolean(KEY_IS_PROFILE_SELECTION_DONE, true);
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return true;
//    }
//
//    public static void saveIsSocialLogin(boolean isProfileSelectionDone) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_SOCIAL_LOGIN, isProfileSelectionDone);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static boolean isSocialLogin() {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getBoolean(KEY_IS_SOCIAL_LOGIN, false);
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return false;
//    }
//
    public static void addSearchingWords(String searchKeyWord, Context context) {
        try {
            Set<String> stringSet = new HashSet<>(getSearchingWordsSet(context));
            if (stringSet.add(searchKeyWord)) {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(KEY_SEARCHING_KEYWORDS, stringSet).commit();
            }
        } catch (Exception e) {
//            L.printStackTrace(e);
        }
    }

    public static Set<String> getSearchingWordsSet(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Set<String> stringSet = preferences.getStringSet(KEY_SEARCHING_KEYWORDS, new HashSet<String>());
            return stringSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }


//
//    public static void clearSearchingWordsSet(Context context) {
//        try {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.clear();
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//    }
//
//    public static PushNotificationActivity.NotificationPrefChild getLastSelectedTypeNotification(PushNotificationActivity.NotificationPrefParent notificationPrefParent, Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return PushNotificationActivity.NotificationPrefChild.getTypeAccToInt(sharedPreferences.getInt("NotificationPref" + notificationPrefParent.name(), notificationPrefParent.defaultPrefrence.childType));
//    }
//
//    public static void setLastSelectedTypeNotification(PushNotificationActivity.NotificationPrefParent notificationPrefParent, Context context, PushNotificationActivity.NotificationPrefChild notPrefChild) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences.edit().putInt("NotificationPref" + notificationPrefParent.name(), notPrefChild.childType).commit();
//
//    }
//
//    public static void saveIsTransitionEnabled(boolean isTransitionEnabled) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_TRANSITION_ENABLED, isTransitionEnabled);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static boolean isTransitionEnabled() {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getBoolean(KEY_IS_TRANSITION_ENABLED, true);
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return false;
//    }
//
//    public static void saveSocialIdLinkStatus(String socialPlatformType, boolean isEnabled) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_SOCIAL_ID_LINK_STATUS + socialPlatformType, isEnabled);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static boolean isSocialIdLinked(String socialPlatformType) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getBoolean(KEY_SOCIAL_ID_LINK_STATUS + socialPlatformType, false);
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return false;
//    }
//
//    public static void saveIsTutuorialShown(boolean isTutuorialShown) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_TUTORIAL_SHOWN, isTutuorialShown);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static void clearIsTutuorialShown() {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.remove(KEY_IS_TUTORIAL_SHOWN);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//    }
//
//    public static boolean isTutuorialShown() {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(
//                    SHARED_PREF_NAME, 0);
//            return sPreferences.getBoolean(KEY_IS_TUTORIAL_SHOWN, false);
//
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return false;
//    }
//
//    public static void saveIsAppUpdateApiCalled(boolean isApiCalled) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_APP_UPDATE_API_CALLED, isApiCalled);
//            editor.commit();
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//    }
//
//    public static boolean isAppUpdateApiCalled() {
//        SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//        return sPreferences.getBoolean(KEY_IS_APP_UPDATE_API_CALLED, false);
//    }
//
//    public static boolean isForceUpdateAvailable() {
//        SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//        return sPreferences.getBoolean(KEY_IS_FORCE_UPDATE_AVAILABLE, false);
//    }
//
//    public static void saveIsForceUpdateAvailable(boolean isForceUpdate) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putBoolean(KEY_IS_FORCE_UPDATE_AVAILABLE, isForceUpdate);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void saveServerVersionCode(int updatedVersionCode) {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putInt(KEY_SERVER_VERSION_CODE, updatedVersionCode);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static int getServerVersionCode() {
//        SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//        return sPreferences.getInt(KEY_SERVER_VERSION_CODE, 0);
//    }
//
//    public static void saveCompaignTrackingUrl(Context context,String url) {
//        try
//        {
//            SharedPreferences sPreferences=context.getSharedPreferences(SHARED_PREF_NAME,0);
//            SharedPreferences.Editor editor=sPreferences.edit();
//            editor.putString(KEY_COMPAIGN_TRACKING_URL,url);
//            editor.commit();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getCompaignTrackingUrl() {
//        SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//        return sPreferences.getString(KEY_COMPAIGN_TRACKING_URL, "");
//    }
//
//    public static void saveHashTagList(ArrayList<String> listHashTag)
//    {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putString(KEY_HASH_TAG, ObjectSerializer.serialize(listHashTag));
//            editor.commit();
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<String> getHashTagList()
//    {
//        ArrayList<String> listHashTag=null;
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            listHashTag = (ArrayList<String>) ObjectSerializer.deserialize(sPreferences.getString(KEY_HASH_TAG, ""));
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return listHashTag;
//    }
//
//    public static void saveCategoryList(ArrayList<String> categoryList)
//    {
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            SharedPreferences.Editor editor = sPreferences.edit();
//            editor.putString(KEY_CATEGORY_LIST, ObjectSerializer.serialize(categoryList));
//            editor.commit();
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<String> getCategoryList()
//    {
//        ArrayList<String> categoryList=null;
//        try {
//            SharedPreferences sPreferences = Utils.getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, 0);
//            categoryList = (ArrayList<String>) ObjectSerializer.deserialize(sPreferences.getString(KEY_CATEGORY_LIST, ""));
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return categoryList;
//    }
//

}
