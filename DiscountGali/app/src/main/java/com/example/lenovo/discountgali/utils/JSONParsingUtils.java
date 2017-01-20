package com.example.lenovo.discountgali.utils;


import android.text.Spannable;
import android.util.Log;

import com.example.lenovo.discountgali.model.LocalDealCategoryModel;
import com.example.lenovo.discountgali.model.ModelCategories;
import com.example.lenovo.discountgali.model.ModelTopStores;
import com.example.lenovo.discountgali.model.TopOffers;
import com.example.lenovo.discountgali.utility.Syso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParsingUtils {

    public static ArrayList<LocalDealCategoryModel> getLocalDealCategories(JSONArray listdata) {
        ArrayList<LocalDealCategoryModel> categoriesList = new ArrayList<>();
        try {
            if(listdata!=null && listdata.length()>0)
            {
                categoriesList = new ArrayList<>();
                for (int i = 0; i < listdata.length(); i++) {
                    LocalDealCategoryModel modelCategories = new LocalDealCategoryModel();
                    JSONObject object = listdata.getJSONObject(i);

                    modelCategories.setCategoryId(object.getInt("CategoryId"));
                    if(object.has("Logo"))
                        modelCategories.setCategoryLogo("http://discountgali.com" + getSubString(object.getString("Logo")));
                    modelCategories.setCategoryName(object.getString("CategoryName"));
                    categoriesList.add(modelCategories);

                }
            }
        } catch (JSONException e) {
            Syso.print("HELLO 4" + e.getMessage());
            e.printStackTrace();
        }
        return categoriesList;
    }

    public static ArrayList<ModelCategories> getCategoriesList(JSONArray listdata) {
        ArrayList<ModelCategories> categoriesList = new ArrayList<>();
        try {
            if(listdata!=null && listdata.length()>0)
            {
                categoriesList = new ArrayList<>();
                for (int i = 0; i < listdata.length(); i++) {
                    ModelCategories modelCategories = new ModelCategories();
                    JSONObject object = listdata.getJSONObject(i);

                    modelCategories.setCategoryId(object.getInt("CategoryId"));
                    if(object.has("Logo"))
                    modelCategories.setCategoryLogo("http://discountgali.com" + getSubString(object.getString("Logo")));
                    modelCategories.setCategoryName(object.getString("CategoryName"));
                    modelCategories.setCategoryStatus(object.getBoolean("CategoryStatus"));
                    categoriesList.add(modelCategories);

                }
            }
        } catch (JSONException e) {
            Syso.print("HELLO 4" + e.getMessage());
            e.printStackTrace();
        }
        return categoriesList;
    }

    public static ArrayList<ModelTopStores> getTopStores(JSONArray listdata) {
        ArrayList<ModelTopStores> topStoresList = new ArrayList<>();
        try {
            if(listdata!=null && listdata.length()>0)
            {
                topStoresList = new ArrayList<>();
                for (int i = 0; i < listdata.length(); i++) {
                    ModelTopStores modelTopStores = new ModelTopStores();
                    JSONObject object = listdata.getJSONObject(i);

                    modelTopStores.setId(object.getInt("CompanyId"));
                    modelTopStores.setIcon("http://discountgali.com" + getSubString(object.getString("Logo")));
//                    modelTopStores.setIcon(object.getString("Logo"));

                    modelTopStores.setName(object.getString("CompanyName"));

                    Syso.print("HELLO " + modelTopStores.getIcon() + modelTopStores.getName());
                    topStoresList.add(modelTopStores);

                }
            }
        } catch (JSONException e) {
            Syso.print("HELLO 4" + e.getMessage());
            e.printStackTrace();
        }
        return topStoresList;
    }

    private static String getSubString(String logo) {
        if(checkNullString(logo))
        {
            return logo.substring(1);
        }
        return "";
    }

    private static boolean checkNullString(String logo) {
        return (logo!=null && !logo.isEmpty())?true:false;
    }


    public static ArrayList<TopOffers> getTopOffers(JSONArray listdata) {
        ArrayList<TopOffers> topOffersList = null;

        try {
            if(listdata!=null && listdata.length()>0)
            {
                topOffersList = new ArrayList<>();
                for (int i = 0; i < listdata.length(); i++) {
                    TopOffers topoffers = new TopOffers();
                    JSONObject object = listdata.getJSONObject(i);
                    topoffers.BrandName = object.getString("BrandName");
                    topoffers.OnlineDeal_Logo = "http://discountgali.com" + getSubString(object.getString("OnlineDeal_Logo"));
                    topoffers.onlineDeal_WebSiteName = object.getString("onlineDeal_WebSiteName");
                    topoffers.OnlineDeal_CouponCode = object.getString("OnlineDeal_CouponCode");
                    topoffers.OnlineDeal_Offer = object.getString("OnlineDeal_Offer");
                    topoffers.OnlineDeal_OfferDescription = object.getString("OnlineDeal_OfferDescription");
                    topoffers.OnlineDeal_Avail = object.getString("OnlineDeal_Avail");
                    topoffers.OnlineDeal_StartDate = object.getString("OnlineDeal_StartDate");
                    topoffers.OnlineDeal_EndDate = object.getString("OnlineDeal_EndDate");

                    topoffers.OnlineDealId = object.getInt("OnlineDealId");
                    topoffers.OnlineDeal_Type = object.getInt("OnlineDeal_Type");

                    topOffersList.add(topoffers);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return topOffersList;
    }

//    public static Roast_2 getNews(JSONObject roastJO) {
//        Roast_2 roast_2 = null;
//        try {
//            if (roastJO != null) {
//                roast_2 = new Roast_2();
//                // News Data
//                roast_2.is_news = true;
//                roast_2.newsModel.id = Utils.decodeStr(roastJO.optString("news_id", ""));
//                roast_2.newsModel.title = Utils.decodeStr(roastJO.optString("title", ""));
//                roast_2.newsModel.desc = Utils.decodeStr(roastJO.optString("description", ""));
//                roast_2.newsModel.image_url = Utils.decodeStr(roastJO.optString("news_image", ""));
//                roast_2.newsModel.video_url = Utils.decodeStr(roastJO.optString("news_video", ""));
//                roast_2.newsModel.link_image_url = Utils.decodeStr(roastJO.optString("link_image", ""));
//                roast_2.newsModel.link_url = Utils.decodeStr(roastJO.optString("news_link", ""));
//                roast_2.newsModel.link_name = Utils.decodeStr(roastJO.optString("link_name", ""));
//                roast_2.newsModel.news_created_on = Utils.decodeStr(roastJO.optString("news_created_on", ""));
//                roast_2.newsModel.news_updated_on = Utils.decodeStr(roastJO.optString("news_updated_on", ""));
//
//
//                roast_2.newsModel.featured_news = Utils.decodeStr(roastJO.optString("featured_news", "")).isEmpty() ? false : true;
//                if (roast_2.newsModel.featured_news) {
//                    if (Utils.decodeStr(roastJO.optString("featured_news", "")).equalsIgnoreCase("0")) {
//                        roast_2.newsModel.featured_news = false;
//                    }
//                }
//
//                roast_2.newsModel.like_count = Utils.stringToInt(Utils.decodeStr(roastJO.optString("like_count", "")));
//
//                RoastActivityManager.setNewsLikeCount(roast_2.newsModel.id, roast_2.newsModel.like_count);
//
//                roast_2.newsModel.is_news_liked = Utils.decodeStr(roastJO.optString("is_news_liked", "")).isEmpty() ? false : true;
//                if (roast_2.newsModel.is_news_liked) {
//                    if (Utils.decodeStr(roastJO.optString("is_news_liked", "")).equalsIgnoreCase("0")) {
//                        roast_2.newsModel.is_news_liked = false;
//                    }
//                }
//                RoastActivityManager.setNewsLikedStaus(roast_2.newsModel.id, roast_2.newsModel.is_news_liked);
//
//                roast_2.newsModel.category_id = Utils.decodeStr(roastJO.optString("category_id", ""));
//                roast_2.newsModel.category_name = Utils.decodeStr(roastJO.optString("category_name", ""));
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return roast_2;
//    }
//
//    public static Roast_2 getRoast(JSONObject roastJO) {
//        Roast_2 roast = null;
//        try {
//            if (roastJO != null) {
//                roast = new Roast_2();
//                // Roast Data
//
//                roast.is_news = Utils.decodeStr(roastJO.optString("is_news", "")).equalsIgnoreCase("1");
//                roast.newsModel.id = Utils.decodeStr(roastJO.optString("news_ids", ""));
//                roast.newsModel.category_id = Utils.decodeStr(roastJO.optString("news_category", ""));
//                roast.newsModel.category_name = Utils.decodeStr(roastJO.optString("news_category_name", ""));
//                roast.newsModel.desc = Utils.decodeStr(roastJO.optString("news_description", ""));
//                roast.newsModel.image_url = Utils.decodeStr(roastJO.optString("news_image", ""));
//
//                roast.newsModel.like_count = Utils.stringToInt(Utils.decodeStr(roastJO.optString("news_like_count", "")));
//                RoastActivityManager.setNewsLikeCount(roast.newsModel.id, roast.newsModel.like_count);
//
//                roast.newsModel.is_news_liked = Utils.decodeStr(roastJO.optString("is_news_liked", "")).isEmpty() ? false : true;
//                RoastActivityManager.setNewsLikedStaus(roast.newsModel.id, roast.newsModel.is_news_liked);
//
//                roast.newsModel.link_url = Utils.decodeStr(roastJO.optString("news_link", ""));
//                roast.newsModel.link_name = Utils.decodeStr(roastJO.optString("news_link_name", ""));
//                roast.newsModel.title = Utils.decodeStr(roastJO.optString("news_title", ""));
//                roast.newsModel.video_url = Utils.decodeStr(roastJO.optString("news_video", ""));
//
//                if (roastJO.has("roast_id")) {
//                    roast.roast_id = Utils.decodeStr(roastJO.optString("roast_id", ""));
//                } else if (roastJO.has("id")) {
//                    roast.roast_id = Utils.decodeStr(roastJO.optString("id", ""));
//                }
//                //roast.roast_id = Utils.decodeStr(roastJO.optString("roast_id", ""));
//
//
//                //               roast.content = Utils.decodeStr(roastJO.optString("content", ""));
//
//                if (roastJO.has("category_name")) {
//                    roast.category_name = Utils.decodeStr(roastJO.optString("category_name"));
//                }
//
//                if (roastJO.has("category_id")) {
//                    roast.category_id = Utils.decodeStr(roastJO.optString("category_id"));
//                }
//
//                if (roastJO.has("roast_image")) {
//                    roast.roast_image = Utils.decodeStr(roastJO.optString("roast_image", ""));
//                }
//
//                roast.content = Utils.decodeStr(roastJO.optString("content", ""));
//                // TODO TO SOLVE COMMENT COUNT 0                // TODO TO SOLVE COMMENT COUNT 0
//                if (roastJO.has("comment_count")) {
//                    roast.comment_count = Utils.stringToInt(Utils.decodeStr(roastJO.optString("comment_count", "")));
//                    RoastActivityManager.setRoastCommentCount(roast.roast_id, roast.comment_count);
//                }
//
//                if (roastJO.has("roast_status")) {
//                    roast.status = Utils.stringToInt(Utils.decodeStr(roastJO.optString("roast_status", "")));
//                   // RoastActivityManager.setRoastCommentCount(roast.roast_id, roast.status);
//                }
//
//                // TODO TO SOLVE LIKE COUNT 0
//                if (roastJO.has("like_count")) {
//                    roast.like_count = Utils.stringToInt(Utils.decodeStr(roastJO.optString("like_count", "")));
//                    RoastActivityManager.setRoastLikeCount(roast.roast_id, roast.like_count);
//                }
//
//                roast.share_count = Utils.stringToInt(Utils.decodeStr(roastJO.optString("share_count", "")));
//
//                roast.is_liked = Utils.decodeStr(roastJO.optString("is_liked", "")).isEmpty() ? false : true;
//                if (roast.is_liked) {
//                    if (Utils.decodeStr(roastJO.optString("is_liked", "")).equalsIgnoreCase("0")) {
//                        roast.is_liked = false;
//                    }
//                }
//
//                RoastActivityManager.setRoastLikedStaus(roast.roast_id, roast.is_liked);
//
//                //roast.setCreated_date(Utils.decodeStr(roastJO.optString("created_on", "")));
//
//
//                // User
//                JSONObject roaster_userJO = roastJO.optJSONObject("roaster_user");
//                if (roaster_userJO != null)
//                    roast.roaster_user = getUser(roaster_userJO);
//
//                // HashTags
//                JSONArray hashTagsJA = roastJO.optJSONArray("hash_tags");
//                if (hashTagsJA != null && hashTagsJA.length() > 0) {
//                    roast.hash_tags = new ArrayList<>();
//                    int size_1 = hashTagsJA.length();
//                    for (int j = 0; j < size_1; j++) {
//                        roast.hash_tags.add(getHashTag(hashTagsJA.getJSONObject(j)));
//                    }
//                }
//                if(roast.hash_tags!=null&&roast.hash_tags.size()>0)
//                {
//                    roast.has_hash_tags=true;
//                }
//
//                // Tagged Users
//                JSONArray taggedUsersJA = roastJO.optJSONArray("tagged_users");
//                if (taggedUsersJA != null && taggedUsersJA.length() > 0) {
//                    roast.tagged_users = new ArrayList<>();
//                    int size_1 = taggedUsersJA.length();
//                    for (int j = 0; j < size_1; j++) {
//                        roast.tagged_users.add(getUser(taggedUsersJA.getJSONObject(j)));
//                    }
//                }
//                if(roast.tagged_users!=null&&roast.tagged_users.size()>0)
//                {
//                    roast.has_tagged_users=true;
//                }
//
//
//                // Comment
//
//                roast.comment = getComment(roastJO.optJSONObject("comment"));
//
//                if(roast.comment!=null&&!roast.comment.comment_id.isEmpty())
//                {
//                    roast.has_comment=true;
//                    if(roast.comment.hash_tags!=null&&roast.comment.hash_tags.size()>0)
//                    {
//                        roast.has_comment_hash_tag=true;
//                    }
//
//                    if(roast.comment.tagged_users!=null&&roast.tagged_users.size()>0)
//                    {
//                        roast.has_comment_tagged_users=true;
//                    }
//                }
//                roast.spannableContent = Utils.getRoastString(roast.content, roast.hash_tags, roast.tagged_users);
//
//                if(roast.is_news)
//                {
//                    roast.roast_updated_on = Utils.decodeStr(roastJO.optString("news_updated_on", ""));
//                    roast.created_on=Utils.decodeStr(roastJO.optString("created_on",""));
//
//                }
//                else
//                {
//                    roast.roast_updated_on = Utils.decodeStr(roastJO.optString("roast_updated_on", ""));
//                    roast.created_on=Utils.decodeStr(roastJO.optString("created_on",""));
//                }
//                //roast.created_on = Utils.decodeStr(roastJO.optString("created_on", ""));
//
//
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return roast;
//    }
//
//    public static ArrayList<Roast_2> getRoastsList(JSONArray roastJA) {
//        ArrayList<Roast_2> roastList = new ArrayList<Roast_2>();
//        try {
//            if (roastJA != null && roastJA.length() > 0) {
//                int size = roastJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject roastJO = roastJA.getJSONObject(i);
//
//                    if (roastJO != null) {
//                        Roast_2 roast = getRoast(roastJO);
//
//                        roastList.add(roast);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return roastList;
//    }
//
//    public static ArrayList<Roast_2> getNewsList(JSONArray roastJA) {
//        ArrayList<Roast_2> roastList = new ArrayList<Roast_2>();
//        try {
//            if (roastJA != null && roastJA.length() > 0) {
//                int size = roastJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject roastJO = roastJA.getJSONObject(i);
//
//                    if (roastJO != null) {
//                        Roast_2 roast = getNews(roastJO);
//
//                        roastList.add(roast);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return roastList;
//    }
//
//    public static ArrayList<Roast_2> getsortedNewsList(ArrayList<Roast_2> unsortedList)
//    {
//        ArrayList<Roast_2> featuredList=new ArrayList<>();
//        ArrayList<Roast_2> unfeaturedList=new ArrayList<>();
//        ArrayList<Roast_2> NewsSortedList=null;
//
//        if(unsortedList!=null&&!unsortedList.isEmpty())
//        {
//            NewsSortedList=new ArrayList<>();
//
//            for(int i=0;i<unsortedList.size();i++)
//            {
//                if(unsortedList.get(i).newsModel.featured_news)
//                {
//                    featuredList.add(unsortedList.get(i));
//                }
//                else {
//                    unfeaturedList.add(unsortedList.get(i));
//                }
//
//            }
//            NewsSortedList.addAll(featuredList);
//            NewsSortedList.addAll(unfeaturedList);
//        }
//
//        return NewsSortedList;
//    }
//
//    public static ArrayList<User_2> getUserList(JSONArray userJA) {
//        ArrayList<User_2> userList = new ArrayList<User_2>();
//        try {
//            if (userJA != null && userJA.length() > 0) {
//                int size = userJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject userJO = userJA.getJSONObject(i);
//
//                    if (userJO != null) {
//                        User_2 user = getUser(userJO);
//
//                        userList.add(user);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return userList;
//    }
//
//    public static ArrayList<ContactsListModel> getContactUser(JSONObject object)
//    {
//        ArrayList<ContactsListModel> listModels=new ArrayList<>();
//
//        try {
//            if (object != null) {
//
//                JSONArray jsonArray=object.getJSONArray("contact_list");
//                for(int i=0;i<jsonArray.length();i++){
//                    JSONObject jsonObj=jsonArray.getJSONObject(i);
//                    ContactsListModel user=new ContactsListModel();
//
//                    user.id = Utils.decodeStr(jsonObj.optString("user_id", ""));
//                    user.user_name = Utils.decodeStr(jsonObj.optString("user_name", ""));
//                    user.name = Utils.decodeStr(jsonObj.optString("name", ""));
//                    user.image_url = Utils.decodeStr(jsonObj.optString("image_url", ""));
//                    user.mobile_no=Utils.decodeStr(jsonObj.optString("mobile_no",""));
//                    user.is_on_roast = Utils.decodeStr(jsonObj.optString("is_on_roast",""));
//                    user.user_id= Utils.decodeStr(jsonObj.optString("user_id", ""));
//                    user.actual_phone_no=Utils.decodeStr(jsonObj.optString("actual_phone_no",""));
//                    user.email=Utils.decodeStr(jsonObj.optString("email",""));
//                    listModels.add(user);
//                }
//
//               // user.is_following = (Utils.decodeStr(object.optString("is_following", "1")).equalsIgnoreCase("1"));
//               // if (object.has("is_following"))
//                    //RoastActivityManager.setFollowingStaus(user.id, (Utils.decodeStr(object.optString("is_following", "1")).equalsIgnoreCase("1")));
//                //user.is_verified = (Utils.decodeStr(object.optString("is_verified", "")).equalsIgnoreCase("1"));
//                /*if(user.is_self_user_id && object.has("is_verified")) {
//                    SharedPreference.saveMyProfileVerified(user.is_verified);
//                }*/
//               // if (object.has("is_verified"))
//                   // RoastActivityManager.setUserVerified(user.id, user.is_verified);
//            }
//        } catch (Exception e) {
//            Log.e("herre","ghfg");
//            L.printStackTrace(e);
//        }
//
//        return listModels;
//    }
//
//    public static User_2 getUser(JSONObject object) {
//        User_2 user = null;
//        try {
//            if (object != null) {
//                user = new User_2();
//                user.id = Utils.decodeStr(object.optString("user_id", ""));
//                user.email = Utils.decodeStr(object.optString("email", ""));
//                //addition for contacts
//                user.mobile_no=Utils.decodeStr(object.optString("mobile_no",""));
//                user.is_on_roast = Utils.decodeStr(object.optString("is_on_roast",""));
//
//                user.user_name = Utils.decodeStr(object.optString("user_name", ""));
//                user.name = Utils.decodeStr(object.optString("name", ""));
//                user.image_url = Utils.decodeStr(object.optString("image_url", ""));
//                user.bio = Utils.decodeStr(object.optString("bio", ""));
//                user.website_url = Utils.decodeStr(object.optString("website_url", ""));
//                user.facebook_id = Utils.decodeStr(object.optString("facebook_id", ""));
//                user.twitter_id = Utils.decodeStr(object.optString("twitter_id", ""));
//                user.google_id = Utils.decodeStr(object.optString("google_id", ""));
//                user.term_and_condition = Utils.decodeStr(object.optString("term_and_condition", ""));
//                user.created_on = Utils.decodeStr(object.optString("created_on", ""));
//                user.updated_on = Utils.decodeStr(object.optString("updated_on", ""));
//                user.status = Utils.decodeStr(object.optString("status", ""));
//                user.total_roast = Utils.stringToInt(Utils.decodeStr(object.optString("total_roast", "")));
//                user.following = Utils.stringToInt(Utils.decodeStr(object.optString("following", "")));
//                user.followed_by = Utils.stringToInt(Utils.decodeStr(object.optString("followed_by", "")));
//                user.is_following = (Utils.decodeStr(object.optString("is_following", "1")).equalsIgnoreCase("1"));
//                if (object.has("is_following"))
//                    RoastActivityManager.setFollowingStaus(user.id, (Utils.decodeStr(object.optString("is_following", "1")).equalsIgnoreCase("1")));
//                user.is_self_user_id = Utils.isSelfUserId(user.id);
//                user.is_verified = (Utils.decodeStr(object.optString("is_verified", "")).equalsIgnoreCase("1"));
//                user.otp_status=Utils.decodeStr(object.optString("otp_status",Constants.OTP_PENDING));
//                /*if(user.is_self_user_id && object.has("is_verified")) {
//                    SharedPreference.saveMyProfileVerified(user.is_verified);
//                }*/
//                if (object.has("is_verified"))
//                    RoastActivityManager.setUserVerified(user.id, user.is_verified);
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return user;
//    }
//    public static UsernamePhoneNumber getUserAfterLogin(JSONObject object) {
//        UsernamePhoneNumber user = null;
//        try {
//            if (object != null) {
//                user = new UsernamePhoneNumber();
//                user.user_id=Utils.decodeStr(object.optString("user_id",""));
//                user.user_name=Utils.decodeStr(object.optString("user_name",""));
//                user.mobile_no=Utils.decodeStr(object.optString("mobile_no",""));
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        return user;
//    }
//
//    public static HashTag_2 getHashTag(JSONObject jsonObject) {
//        HashTag_2 hashTag = null;
//        try {
//            if (jsonObject != null) {
//                hashTag = new HashTag_2();
//                hashTag.id = Utils.decodeStr(jsonObject.optString("hash_tag_id", ""));
//                hashTag.title = Utils.decodeStr(jsonObject.optString("title", ""));
//                hashTag.start_date = Utils.decodeStr(jsonObject.optString("start_date", ""));
//                hashTag.start_date_long_formatted = Utils.getFormattedDate(hashTag.start_date, "yyyy-MM-dd kk:mm:ss", "dd MMM");
//                hashTag.start_date_short_formatted = Utils.getFormattedDate(hashTag.start_date, "yyyy-MM-dd kk:mm:ss", "hh:mm aa");
//                hashTag.start_date_long = Utils.getDateLong(hashTag.start_date, "yyyy-MM-dd kk:mm:ss");
//                hashTag.end_date = Utils.decodeStr(jsonObject.optString("end_date", ""));
//                hashTag.created_on = Utils.decodeStr(jsonObject.optString("created_on", ""));
//                hashTag.status = Utils.decodeStr(jsonObject.optString("status", ""));
//                hashTag.is_today_hash_tag = (Utils.stringToInt(Utils.decodeStr(jsonObject.optString("is_today_hash_tag", ""))) == 1);
//                hashTag.is_live = Utils.decodeStr(jsonObject.optString("is_live", "")).equalsIgnoreCase("1");
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return hashTag;
//    }
//
//    public static ArrayList<HashTag_2> getHashTagList(JSONArray hashTagJA) {
//        ArrayList<HashTag_2> hashTagList = new ArrayList<HashTag_2>();
//        try {
//            if (hashTagJA != null && hashTagJA.length() > 0) {
//                int size = hashTagJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject hashTagJO = hashTagJA.getJSONObject(i);
//
//                    if (hashTagJO != null) {
//                        HashTag_2 hashTag = getHashTag(hashTagJO);
//
//                        hashTagList.add(hashTag);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return hashTagList;
//    }
//
//    public static Comment getComment(JSONObject jsonObject) {
//        Comment comment = null;
//        try {
//            if (jsonObject != null) {
//                comment = new Comment();
//                comment.comment_id = Utils.decodeStr(jsonObject.optString("comment_id", ""));
//                comment.content = Utils.decodeStr(jsonObject.optString("content", ""));
//                comment.setCreated_on(Utils.decodeStr(jsonObject.optString("created_on", "")));
//
//                JSONObject commented_by_userJO = jsonObject.optJSONObject("commented_by_user");
//                if (commented_by_userJO != null) {
//                    comment.commented_by_user = getUser(commented_by_userJO);
//                }
//
//                // HashTags
//                JSONArray hashTagsJA = jsonObject.optJSONArray("hash_tags");
//                if (hashTagsJA != null && hashTagsJA.length() > 0) {
//                    comment.hash_tags = new ArrayList<>();
//                    int size_1 = hashTagsJA.length();
//                    for (int j = 0; j < size_1; j++) {
//                        comment.hash_tags.add(getHashTag(hashTagsJA.getJSONObject(j)));
//                    }
//                }
//
//                // Tagged Users
//                JSONArray taggedUsersJA = jsonObject.optJSONArray("tagged_users");
//                if (taggedUsersJA != null && taggedUsersJA.length() > 0) {
//                    comment.tagged_users = new ArrayList<>();
//                    int size_1 = taggedUsersJA.length();
//                    for (int j = 0; j < size_1; j++) {
//                        comment.tagged_users.add(getUser(taggedUsersJA.getJSONObject(j)));
//                    }
//                }
//                comment.spannableContent = Utils.getRoastString(comment.content, comment.hash_tags, comment.tagged_users);
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return comment;
//    }
//
//    public static ArrayList<Comment> getCommentList(JSONArray commentsJA) {
//        ArrayList<Comment> commentList = new ArrayList<Comment>();
//        try {
//            if (commentsJA != null && commentsJA.length() > 0) {
//                int size = commentsJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject commentJO = commentsJA.getJSONObject(i);
//
//                    if (commentJO != null) {
//                        Comment hashTag = getComment(commentJO);
//
//                        commentList.add(hashTag);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//        return commentList;
//    }
//
//    public static Object[] getNotificationList(JSONArray notificationJA) {
//        ArrayList<RoastNotification> roastNotificationList = new ArrayList<RoastNotification>();
//        boolean has_unread_notifications = false;
//        try {
//            if (notificationJA != null && notificationJA.length() > 0) {
//                int size = notificationJA.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject notificationJO = notificationJA.getJSONObject(i);
//
//                    if (notificationJO != null) {
//                        RoastNotification roastNotification = getNotification(notificationJO);
//                        if (roastNotification != null) {
//                            roastNotificationList.add(roastNotification);
//                            if (!roastNotification.is_read)
//                                has_unread_notifications = true;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            L.printStackTrace(e);
//        }
//
//        Object[] retVal = new Object[2];
//        retVal[0] = roastNotificationList;
//        retVal[1] = has_unread_notifications;
//
//        return retVal;
//    }
//
//    public static RoastNotification getNotification(JSONObject notificationJO) {
//        RoastNotification roastNotification = null;
//
//        if (notificationJO != null) {
//            roastNotification = new RoastNotification();
//            roastNotification.id = Utils.decodeStr(notificationJO.optString("notification_id", ""));
//            roastNotification.created_on = Utils.decodeStr(notificationJO.optString("created_on", ""));
//            roastNotification.created_on_formatted = Utils.getDaysDifference(roastNotification.created_on);
//
//
//            // Action & Notitification Type
//            roastNotification.action_type = RoastNotification.ActionType.fromInteger(Utils.stringToInt(Utils.decodeStr(notificationJO.optString("action_type", ""))));
//            roastNotification.notification_type = RoastNotification.NotificationType.fromInteger(Utils.stringToInt(Utils.decodeStr(notificationJO.optString("notification_type", ""))));
//
//            // Roast
//            roastNotification.roast = getRoast(notificationJO.optJSONObject("roast"));
//            if (roastNotification.roast == null)
//                roastNotification.roast = new Roast_2();
//
//
//            // Comment
//            roastNotification.comment = getComment(notificationJO.optJSONObject("comment"));
//            if (roastNotification.comment == null)
//                roastNotification.comment = new Comment();
//
//
//            // Performer By User
//            roastNotification.performer_by_user = getUser(notificationJO.optJSONObject("performed_by"));
//            if (roastNotification.performer_by_user == null)
//                roastNotification.performer_by_user = new User_2();
//
//
//            // Performer On User
//            roastNotification.performer_on_user = getUser(notificationJO.optJSONObject("performed_on"));
//            if (roastNotification.performer_on_user == null)
//                roastNotification.performer_on_user = new User_2();
//
//            // Broadcast Message
//            roastNotification.broadcast_title = Utils.decodeStr(notificationJO.optString("title"));
//            roastNotification.broadcast_content = Utils.decodeStr(notificationJO.optString("description"));
//            roastNotification.broadcast_redirect_page = Utils.decodeStr(notificationJO.optString("redirect_page"));
//            /* */
//            roastNotification.roast.roaster_user = roastNotification.performer_by_user;
//
//            roastNotification.comment.commented_by_user = getUser(notificationJO.optJSONObject("performed_by"));
//            if (roastNotification.comment.commented_by_user == null)
//                roastNotification.comment.commented_by_user = new User_2();
//
//            roastNotification.is_read = (Utils.decodeStr(notificationJO.optString("is_read", "")).equalsIgnoreCase("1"));
//        }
//
//        return roastNotification;
//    }

}
