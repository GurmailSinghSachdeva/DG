package com.example.lenovo.discountgali.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.lenovo.discountgali.utility.Syso;

/**
 * Created by AND-02 on 25-08-2016.
 */
public class UploadContactIntentService extends IntentService{
    boolean isContactUploading = false;
    private int limit = 20;


//    public static void startContactUpload(Context context){
//        // Check first user is logged-in or not
//        if(UserPreference.getInstance(context).getPkAccount()>0)
//            context.startService(new Intent(context, UploadContactIntentService.class));
//    }


    public UploadContactIntentService(){
        super("New IntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadContactIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Syso.print("---------- in onHandleIntent : ");
//        if(!isContactUploading) {
//            isContactUploading = true;
//            Syso.print("---------- in inside onHandleIntent : ");
//            int totalCount = FetchContacts.getAllContactSize(getApplicationContext());
////            totalCount = 20;
//            Syso.print("total count >"+totalCount);
//            for (int i = 0; i < totalCount/limit; i++) {
//                ArrayList<ValueTypeModel> emailList = FetchContacts.readNumberAndEmail(getApplicationContext(), null, limit, i * limit);
//                Syso.print("-----------------------------------------------------------------------------------");
//                Syso.print("i=" + i + " >> " + emailList.size() + ": " + emailList);
//                if(UserPreference.getInstance(this).getPkAccount() > 0) {
//                    if (emailList.size() > 0 )
//                        uploadContacts(emailList);
//                }else {
//                    break;
//                }
//            }
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isContactUploading = false;
        Syso.print("----- in onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Syso.print("----- in onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Syso.print("----- in onBind");
        return super.onBind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Syso.print("----- in onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Syso.print("----- in onDestroy");
    }



//    private void uploadContacts(ArrayList<ValueTypeModel> emailList) {
//        final UploadContactsApiCall loginApiCall = new UploadContactsApiCall(UserPreference.getInstance(this).getPkAccountString(),emailList);
//        HttpRequestHandler.getInstance(this).executeRequest(loginApiCall, new ApiCall.OnApiCallCompleteListener() {
//            @Override
//            public void onComplete(Exception e) {
//                if (e == null) {
//                    ServerResponseList response = (ServerResponseList) loginApiCall.getResult();
//                    switch (response.getCode()) {
//                        case Code.UPLOAD_CONTACT_SUCCESS:
//                            ArrayList<Contact> contacts = (ArrayList<Contact>) response.getData();
//                            Syso.print("------------- contact received list>>>" + contacts);
//                            if(contacts!=null && contacts.size()>0) {
//                                DbController dbController = DbController.getInstance();
//                                dbController.saveContacts(getApplicationContext(), contacts,false);
//                            }
//                            break;
//                    }
//                } else {
//                    e.printStackTrace();
//                    Syso.print("$$$$$$$$$$$ contacts failed ");
//                }
//            }
//        }, false);
//    }

}
