package com.example.lenovo.discountgali.network;

/**
 * Created by Arpit on 10/8/2015.
 */
public class ServerRequests {

    public static final String URL = "http://discountgali.com/MobileApi.asmx?";
//    public static final String URL = "http://rnd2.rapidsoft.in/";
//    public static final String URL = "http://192.168.1.78:8081/";
    public static final String BASE_URL = URL;

    public static final String SP_USER_REGISTER = "UserRegistration/";
    public static final String SP_PROFILE = "Profile/";
    public static final String SP_MESSAGE = "Message/";
    public static final String SP_AGENCY = "agency/";

    public static final String REQUEST_EMAIL_EXIST = SP_USER_REGISTER + "iaEmailExists";
    public static final String REQUEST_GROUP_VALID = SP_USER_REGISTER + "iaGroupValid";
    public static final String REQUEST_VALID_PHONE = SP_USER_REGISTER + "iaValidPhone";
    public static final String REQUEST_LOGIN = SP_USER_REGISTER + "iaLogin";
    public static final String REQUEST_FORGOT_PASSWORD = SP_USER_REGISTER + "iaResetPassword";
    public static final String REQUEST_ENROLL = SP_USER_REGISTER + "iaEnroll";
    public static final String REQUEST_CHANGE_PASSWORD = SP_USER_REGISTER + "iaChangePassword";
    public static final String REQUEST_RESEND_EMAIL = SP_USER_REGISTER + "iaResendEmailVerification";
    public static final String REQUEST_UPDATE_TOKEN = SP_USER_REGISTER + "iaUpdateDeviceToken";
    public static final String REQUEST_LOGOUT = SP_USER_REGISTER + "ialogout";



    public static final String REQUEST_GET_PROFILE = SP_PROFILE + "iaGetAccountDetails";
    public static final String REQUEST_UPDATE_PROFILE = SP_PROFILE + "iaUpdateProfile";
    public static final String REQUEST_GET_SECURED_CONTACTS = SP_PROFILE + "iaGetSecuredContacts";
    public static final String REQUEST_ADD_FAV = SP_PROFILE + "iaAddSecuredContacts";
    public static final String REQUEST_DELETE_PHOTO = SP_PROFILE + "iaDeleteProfilePhoto";
    public static final String BLOCK_UNBLOCK_USER = SP_PROFILE + "iaBlockUnblockUser";
    public static final String REQUEST_ADD_CONTACTS = SP_PROFILE + "iaUploadContacts";
    public static final String REQUEST_GET_GROUP_LIST = SP_PROFILE + "iaGetAllGroup";
    public static final String REQUEST_Add_FAVORITE = SP_PROFILE + "iaAddFavorite";
    public static final String REQUEST_GET_FAVORITE = SP_PROFILE + "iaGetFavoriteContacts";
    public static final String REQUEST_CREATE_PRIVATE_GROUP = SP_PROFILE + "iaCreatePrivateGroup";
    public static final String REQUEST_INVITE_ALL = SP_PROFILE + "iaInviteAll";
    public static final String REQUEST_LEAVE_GROUP = SP_PROFILE + "iaLeaveGroup";
    public static final String REQUEST_DELETE_SECURED_CONTACT = SP_PROFILE + "iaDeleteSecuredContect";


    public static final String REQUEST_SEND_MESSAGE = SP_MESSAGE + "iaSendMessage";
    public static final String REQUEST_GET_MESSAGE = SP_MESSAGE + "iaGetRecentMessages";
    public static final String REQUEST_DELETE_MESSAGE = SP_MESSAGE + "iaDeleteMessages";

    public static final String REQUEST_GET_OFFERS = BASE_URL                            + "op=GetOnlineTopOffers";
    public static final String REQUEST_TOP_STORES = BASE_URL                            + "op=GetOnlineBrand";
    public static final String REQUEST_CATEGORIES = BASE_URL                            + "op=GetCategories" ;
    public static final String REQUEST_LOCAL_DEAL_CATEGORIES = BASE_URL                 + "op=GetLocalDealCategories" ;
    public static final String REQUEST_OFFERS_STORES_WISE = BASE_URL                    + "op=GetOnlineTopDeals";
    public static final String REQUEST_GET_OFFERS_ONLINE_CATEGORY_WISE = BASE_URL       + "op=GetOnlineDealOnCategory";
    public static final String REQUEST_CITY_LIST = BASE_URL                             + "op=GetListOfCities";
    public static final String REQUEST_LOCAL_DEALS = BASE_URL                           + "op=GetLocalDeals";
    public static final String REQUEST_SEARCH = BASE_URL                                + "op=GetSearchOnlineResult";
    public static final String REQUEST_GET_URL_ON_MOBILE_NUMBER = BASE_URL              + "op=GetDealWithMobileNo";
    public static final String REQUEST_BANNER = BASE_URL                                + "op=GetOnlineBanner" ;
    public static final String REQUEST_DEAL_OF_THE_DAY = BASE_URL                       + "op=GetDealofDay";
    public static final String REQUEST_LOGIN_DG = BASE_URL                              + "op=MobileLogin";
    public static final String REQUEST_LOGIN_VERIFY = BASE_URL                          + "op=MobileLoginVerification";
    public static final String REQUEST_INSERT_CAMPAIGN = BASE_URL                       + "op=InsertCampaignRecords";


    public static final String UPLOAD_USER_PROFILE_IMAGE = BASE_URL + SP_PROFILE + "iaUploadFile";
//    public static final String UPLOAD_USER_PROFILE_IMAGE = BASE_URL + SP_PROFILE + "iaUpload2";

    public static final String REQUEST_JOIN_AGENCY = SP_AGENCY + "iaJoinAgency";

}
