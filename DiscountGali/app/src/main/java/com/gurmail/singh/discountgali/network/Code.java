package com.gurmail.singh.discountgali.network;

/**
 * Created by AND-02 on 19-07-2016.
 */
public class Code {

    public static final int PARAMETER_INVALID = 2000;

    public static final int EMAIL_NOT_EXIST = 2001;
    public static final int EMAIL_EXIST = 2002;
    public static final int EMAIL_INVALID = 2003;
    public static final int EMAIL_VERIFIED = 2017;
    public static final int EMAIL_SUSPENDED = 2018;
    public static final int EMAIL_EXIST_UNVERIFIED = 2019;


    public static final int GROUP_VALID = 2004;
    public static final int GROUP_INVALID = 2005;
    public static final int GROUP_ERROR = 2006;
    public static final int GROUP_SUBEXP = 2007;

    public static final int LOGIN_SUCCESS = 2013;
    public static final int LOGIN_INVALID_EMAIL = 2014;
    public static final int LOGIN_INVALID_PASSWORD = 2016;

    public static final int PHONE_EXIST = 2009;
    public static final int PHONE_NOT_EXIST = 2008;
    public static final int PHONE_INVALID = 2015;

    public static final int ENROLL_SUCCESS =2012;
    public static final int ENROLL_FAIL = 2052; //TODO Need to change after server valid response

    public static final int ADD_FAV_SUCCESS = 2026; //TODO: change according to server
    public static final int PROFILE_SUCCESS = 2020;
    public static final int PROFILE_NOT_FOUND = 2021;
    public static final int PROFILE_UPDATED_SUCCESS = 2028;
    public static final int PROFILE_PIC_DELETED_SUCCESS = 2034;
    public static final int PROFILE_PIC_DELETED_FAIL = 3008;

    public static final int FAV_CONTACT_LIST_SUCCESS = 2023;
    public static final int FAV_CONTACT_LIST_NO_DATA = 2024;
    public static final int ADD_FAV_DUPLICATE = 2031;

    public static final int RESET_PASSWORD_LINK_SENT = 2030;
    public static final int CHANGED_PASSWORD_SUCCESS = 2033;
    public static final int RESET_PASSWORD_SUCCESS = 2029;

    public static final int MESSAGE_LIST_SUCCESS = 2040;
    public static final int NO_NEW_MESSAGE = 2041;
    public static final int MESSAGE_NOT_SENT = 2037;
    public static final int MESSAGE_SENT = 2036;
    public static final int SUCCESSFULLY_JOINED = 2047;
    public static final int INVALID_GROUP_OR_PASSWORD = 2048;

    public static final int USER_IS_BLOCKED = 2044;
    public static final int USER_UN_BLOCKED = 2045;
    public static final int INVALID_ACCOUNT = 2046;
    public static final int UPLOAD_CONTACT_SUCCESS = 2038;
    public static final int DEVICE_TOKEN_UPDATE_SUCCESS = 2052;
    public static final int DEVICE_TOKEN_UPDATE_FAIL = 3014;
    public static final int FAVORITE_SUCCESS_FULL=2057;

    public static final int GROUP_LIST_SUCCESS = 2055;
    public static final int CREATE_PRIVATE_GROUP = 2051;
    public static final int LEAVE_GROUP = 2062;
    public static final int DELETE_SECURED_CONTACT_SUCCESS = 2063;



    public static final int LOGOUT_SUCCESS = 2054;
    public static final int LOGOUT_FAIL = 3015;


    public static final int MESSAGE_DELETE_SUCCESS = 2058 ;
    public static final int MESSAGE_DELETE_FAIL = 3058 ;
    public static final int FORCE_UPDATE = 3020 ;

    public static final int SUCCESS_MESSAGE_CODE = 302;
}
