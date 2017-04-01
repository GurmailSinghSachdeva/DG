package com.gurmail.singh.discountgali.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.support.v4.util.LruCache;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gurmail.singh.discountgali.network.api.APIException;
import com.gurmail.singh.discountgali.network.api.ApiCall;
import com.gurmail.singh.discountgali.utility.Syso;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestHandler {
    public static final String DEFAULT_REQUEST_TAG = "";
    private static final int CACHE_SIZE = 10 * 1024 * 1024 ; // 10MB cap

    private static HttpRequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private Cache mCache;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    public static synchronized HttpRequestHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpRequestHandler(context);
        }
        return mInstance;
    }

    private HttpRequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public Context getContext(){
        return mCtx;
    }

//    public void executeRequest(final LoginApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
//        executeRequest(apiCall, onApiCallCompleteListener, true);
//    }

    public void executeRequest(final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener, boolean shouldCache) {
        Request request = null;


        try {
            switch (apiCall.getRequestType()) {
                case JSON_ARRAY_REQUEST:
                    request = new JsonArrayRequest(apiCall.getServiceURL(getContext()), new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if(apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else
                                return apiCall.getHeaders();
                        }
                    };
                    break;

                case JSON_OBJECT_REQUEST:
                    request = new JsonObjectRequest(apiCall.getServiceURL(getContext()), (JSONObject)apiCall.getRequest(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Syso.print(">>>>> onResponse : "+response);
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Syso.print(">>>>> onErrorResponse : "+error);
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            if(apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
                                return super.getHeaders();
                            else {
//                                Syso.print("Header >>>>"+apiCall.getHeaders());
                                return apiCall.getHeaders();
                            }
                        }
                    };

                    break;
                case STRING_REQUEST:
                    request = new StringRequest(Request.Method.POST, apiCall.getServiceURL(getContext()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            handleResponse(response, apiCall, onApiCallCompleteListener);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleError(error, onApiCallCompleteListener);
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/soap+xml; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return apiCall.getStringRequest() == null ? null : apiCall.getStringRequest().getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
//                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }


//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            if(apiCall.getHeaders() == null || apiCall.getHeaders().size() == 0)
//                                return super.getHeaders();
//                            else {
//                                Syso.print("HELLO >>>>"+apiCall.getHeaders());
//                                return apiCall.getHeaders();
//                            }                        }

                        //                        @Override
//                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                            String responseString = "";
//                            if (response != null) {
//                                responseString = String.valueOf(response.statusCode);
//                                // can get more details such as response.headers
//                            }
//                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                        }
                    };
                    Syso.print(">>>>> Url : "+apiCall.getServiceURL(getContext()));
                    Syso.print(">>>>> Request : "+apiCall.getRequest());
                    break;
            }

            if(request != null) {
                request = request.setShouldCache(shouldCache);
                request = request.setUseOfflineCache(shouldCache);
                request.setRetryPolicy(new DefaultRetryPolicy(30000,
                        0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                addToRequestQueue(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            handleError(e, onApiCallCompleteListener);
        }
    }

    private void handleResponse(Object response, final ApiCall apiCall, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
        if(response != null) {
            try {
                if(checkForceUpdate(response)) {
                    apiCall.populateFromResponse(response);
                    if (apiCall.getErrorCode() != null && !apiCall.getErrorCode().trim().isEmpty() /*|| (!apiCall.getErrorCode().equalsIgnoreCase(Constants.RESPONSE_CODES.successLogin )&& apiCall.getServiceURL().contains("apps")) ||((!apiCall.getErrorCode().equalsIgnoreCase(Constants.RESPONSE_CODES.successSignUp )&& apiCall.getServiceURL().contains("Signup")))*/) {
                        handleError(new APIException(apiCall.getErrorCode(), apiCall.getErrorDesc()), onApiCallCompleteListener);
                    } else if (apiCall.getResult() == null) {
                        handleError(new NullPointerException("Response is null"), onApiCallCompleteListener);
                    } else if (onApiCallCompleteListener != null) {
                        onApiCallCompleteListener.onComplete(null);
                    }
                }else {
                    handleError(new NullPointerException("Response is null"), onApiCallCompleteListener);
                }
            } catch (Exception e) {
                handleError(e, onApiCallCompleteListener);
            }
        } else {
            handleError(new NullPointerException("Response is null"), onApiCallCompleteListener);
        }
    }

    private boolean checkForceUpdate(Object response) {
//        try {
//            JSONObject object = (JSONObject) response;
//            int code = object.getInt("code");
//            if(code == Code.FORCE_UPDATE)
//            {
//                getContext().startActivity(ForceUpdateActivity.getStartIntent(getContext()));
//                return false;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return true;
    }

    private void handleError(Exception error, final ApiCall.OnApiCallCompleteListener onApiCallCompleteListener) {
        if(onApiCallCompleteListener != null) {
            if(error instanceof VolleyError) {
                VolleyError volleyError = (VolleyError) error;
                if(volleyError.networkResponse != null
                        && (volleyError.networkResponse.statusCode < 200 || volleyError.networkResponse.statusCode > 200)) {

                    // Response Body
                    String responseBody = null;
                    if(volleyError.networkResponse.data != null) {
                        responseBody = new String(volleyError.networkResponse.data);

                        Syso.print("HELLO " +  " apiecseption " + responseBody);
                        // Parse response
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);

//                            String errorCode = DecodeUtills.getStringFromBase64(jsonObject.optString("error_code", ""));
//                            String errorDesc = DecodeUtills.getStringFromBase64(jsonObject.optString("Message", ""));
                            String errorCode = (jsonObject.optString("error_code", ""));
                            String errorDesc = (jsonObject.optString("Message", ""));
                            if (!errorDesc.trim().isEmpty()) {
                                onApiCallCompleteListener.onComplete(new APIException(errorCode, errorDesc));
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Complete callback with an ApiException with Response Body
                    APIException apiException = new APIException(""+volleyError.networkResponse.statusCode, "", responseBody);
                    onApiCallCompleteListener.onComplete(apiException);
                    return;
                }
            }
            onApiCallCompleteListener.onComplete(error);
        }
    }

    public void cancelRequest(ApiCall apiCall) {
        cancelRequest(apiCall.getTag());
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Instantiate the cache
            mCache = new DiskBasedCache(mCtx.getCacheDir(), CACHE_SIZE);

            HttpStack stack;
            // If the device is running a version >= Gingerbread...
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                // use HttpURLConnection for stack.
                stack = new HurlStack(mCtx);
            } else {
                // use AndroidHttpClient for stack.
                String userAgent = "volley/0";
                try {
                    String packageName = mCtx.getPackageName();
                    PackageInfo info = mCtx.getPackageManager().getPackageInfo(packageName, 0);
                    userAgent = packageName + "/" + info.versionCode;
                } catch (NameNotFoundException e) {
                }
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
            Network network = new BasicNetwork(stack);

            // Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(mCache, network);

            // Start the queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(DEFAULT_REQUEST_TAG);
        getRequestQueue().add(req);
    }

    public void cancelRequest(Object tag) {
        if(tag != null) {
            getRequestQueue().cancelAll(tag);
        }
    }

    public void stop() {
        getRequestQueue().cancelAll(DEFAULT_REQUEST_TAG);
        getRequestQueue().stop();
        mInstance = null;
    }

    public void clearVolleyCache(Context context) {
        mCache.clear();
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void checkBase64(JSONObject jsonObject){
        //TODO: will do it later
        try {
            if (jsonObject != null) {
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Object o = jsonObject.get(key);
                }
            }
        }catch (Exception e){

        }
    }

}
