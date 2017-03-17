package com.example.lenovo.discountgali.utils;

/**
 * Created by Dushyant Singh on 11/24/2016.
 */
public class Constants {
    public static final int PAGE_NO_DEFAULT = 1;
    public static final String REDIRECT_URL = "redirect_url";
    public static final String COUPON_CODE = "coupon_code";
    public static boolean DEBUG = false; // TODO: should be false for final build

    public static String urlContactUs = "http://discountgali.com/Contactus.aspx";
    public static String urlMerchant = "http://www.discountgali.com/login.aspx";
//    public static final String BASE_URL = "http://dev2.rapidsoft.in/Roast/Services/index";
//    public static final String UPLOAD_PROFILE_PIC_URL = "http://dev2.rapidsoft.in/Roast/UploadServices/uploadUserProfileImage";

//    public static final String BASE_URL = "http://project.rapidsoft.in/Roast/Services";
//    public static final String UPLOAD_PROFILE_PIC_URL = "http://project.rapidsoft.in/Roast/UploadServices/uploadUserProfileImage";

//    public static final String BASE_URL = "http://test.rapidsoft.in/Roast/Services";
//    public static final String UPLOAD_PROFILE_PIC_URL = "http://test.rapidsoft.in/Roast/UploadServices/uploadUserProfileImage";
//    public static final String UPLOAD_ROAST_PIC_URL = "http://test.rapidsoft.in/Roast/UploadServices/uploadRoastImage";

    public static final String OTP_DELIMITER = " is ";//for otp recognize
    public static final String SMS_ORIGIN = "OTPROAST";
    public static final String OTP_PENDING = "pending";
    public static final String OTP_VERIFIED = "verified";

    /*public static final String BASE_URL = "http://roastation.in/Services";
    public static final String UPLOAD_PROFILE_PIC_URL = "http://roastation.in /UploadServices/uploadUserProfileImage";
    public static final String UPLOAD_ROAST_PIC_URL = "http://roastation.in/UploadServices/uploadRoastImage";*/

    public static final String BASE_URL = "http://roast.rapidsoft.in/Services";
    public static final String UPLOAD_PROFILE_PIC_URL = "http://roast.rapidsoft.in/UploadServices/uploadUserProfileImage";
    public static final String UPLOAD_ROAST_PIC_URL = "http://roast.rapidsoft.in/UploadServices/uploadRoastImage";

//    public static final String BASE_URL = "http://192.168.1.68/Roast/Services";
//    public static final String UPLOAD_PROFILE_PIC_URL = "http://192.168.1.68/Roast/UploadServices/uploadUserProfileImage";
//    public static final String UPLOAD_ROAST_PIC_URL = "http://192.168.1.68/Roast/UploadServices/uploadRoastImage";

//    public static final String BASE_URL = "http://192.168.0.82/Roast/Services";
//    public static final String UPLOAD_PROFILE_PIC_URL = "http://192.168.0.82/Roast/Services/UploadServices/uploadUserProfileImage";
//    public static final String UPLOAD_ROAST_PIC_URL = "http://192.168.0.82/Roast/UploadServices/uploadRoastImage";

    public static final int PAGE_START_DEFAULT = 1;
    public static final int PAGE_LIMIT_DEFAULT = 15; // -1 = all records, -2 = page limit set on server, any positive value = will fetch those no. of records

    public static final int PAGE_LIMIT_RECENT = 20;
    public static final int PAGE_LIMIT_UPDATE = 25;

    public static final int PAGE_LIMIT_DEFAULT_LARGE = 10; // -1 = all records, -2 = page limit set on server, any positive value = will fetch those no. of records
    public static final int PAGE_LIMIT_ALL_DATA = -1; // -1 = all records, -2 = page limit set on server, any positive value = will fetch those no. of records

    public static final String HTAG = "HASHTAG";
    public static final String HTAG_TIME = "HTAG_TIME";
    public static final String HTAG_STATUS = "HTAG_STATUS";
    public static final String ROAST = "ROAST";

    public static final int MIN_PROFILE_SELECTION_DEFAULT = 5;
    public static final int MIN_PASSWORD_LENGTH = 6;

    public static String zipJson = "2"; // 1=true,  2=false
    public static String decode = "1"; // 1=true,  2=false
    public static boolean encode = (decode.equalsIgnoreCase("1"));
    public static boolean zip = (zipJson.equalsIgnoreCase("1"));

    public static String Device_Type_Value = "1"; // device type for 1=>android ,2=>iphone
    public static String Device_Token_Value = "";

    public static boolean isImageChooserLib = true;
    public final static String NORMAL_RECORD = "0";
    public final static String NEW_RECORD = "1";
    public final static String OLD_RECORD = "2";
    public final static String UPDATE_RECORD = "2";

    public final static String MYFEED = "1";
    public final static String EXPLORE = "2";
    public final static String NEWS = "3";
    public final static String VIDEOS = "4";
    public final static String LIKEDSTORIES = "5";
    public final static String DATABASE_EMPTY = "database_empty";
    public final static String DATABASE_END_REACHED = "database_end_reached";

    public final static int FOLLOWING_NOTIFICATION_PAGE = 1;
    public final static int OWN_NOTIFICATION_PAGE = 2;


    public final static String UPDATE_TOTAL_ROAST_ADD = "1";
    public final static String UPDATE_TOTAL_ROAST_DEL = "2";
    public final static String UPDATE_FOLLOWING_COUNT_ADD = "3";
    public final static String UPDATE_FOLLOWING_COUNT_DEL = "4";
    public final static String UPDATE_FOLLOWER_COUNT_ADD = "5";
    public final static String UPDATE_FOLLOWER_COUNT_DEL = "6";


    public final static String old = "old";
    public final static String recent = "recent";

    public final static int offline = 1;
    public final static int online = 2;
    public static int typeOffline = 2;
    public static int typeOnline = 1;
    public static int phone_number_max_digits = 10;
    public static int LoggedIn = 1;
    public static int otp_sent_success = 1;

    public interface SocialPlatformIds {
        String FACEBOOK = "1";
        String TWITTER = "2";
        String GOOGLE = "3";
        String TUMBLR = "4";
    }

    public enum SocialLinkType {
        LINK("1"), UNLINK("2");
        public String typeCodeJson;

        SocialLinkType(String typeCodeJson) {
            this.typeCodeJson = typeCodeJson;
        }

        public static SocialLinkType getDefaultSearchType() {
            return LINK;
        }
    }

    public interface ServerCodes {
        String USER_BLOCKED = "403";
    }

    public static final String YOUTUBE_KEY = "AIzaSyATZj7caPNVCi-ZlDQ0nvOPT9dnbC967UM";


    public interface Grids {
        int Top_stores = 3;
        int Local_Deal_Categories = 2;
    }
    public interface ACTIVITYFORRESULT {
        int REQUESTOFFERDETAIL = 1;
        int REQUESTOFFERSTORES = 2;
    }
}

