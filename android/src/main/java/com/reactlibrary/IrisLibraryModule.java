package com.reactlibrary;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.google.gson.Gson;

import com.somosiris.mobileandroidsdk.SDKIris;
import com.somosiris.mobileandroidsdk.services.pushmetrics.handler.NotificationOpenedHandler;
import com.somosiris.mobileandroidsdk.services.pushmetrics.handler.NotificationReceivedHandler;
import com.somosiris.mobileandroidsdk.utils.IrisNotificationOpenedHandler;
import com.somosiris.mobileandroidsdk.utils.IrisNotificationReceivedHandler;

import android.content.Context;

import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.somosiris.mobileandroidsdk.database.IrisNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSPermissionState;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OSSubscriptionState;
import com.onesignal.OSEmailSubscriptionState;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class IrisLibraryModule extends ReactContextBaseJavaModule implements LifecycleEventListener, IrisNotificationReceivedHandler, IrisNotificationOpenedHandler {


    private ReactApplicationContext mReactApplicationContext;
    private ReactContext mReactContext;

    private boolean irisInitDone;
    private boolean registeredEvents = false;

    private OSNotificationOpenResult coldStartNotificationResult;
    private boolean hasSetNotificationOpenedHandler = false;

    private NotificationOpenedHandler openedHandler;
    private NotificationReceivedHandler receivedHandler;



    public IrisLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactApplicationContext = reactContext;
        mReactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
        initIris();
        openedHandler =  SDKIris.INSTANCE.getOpenedHandler();
        receivedHandler = SDKIris.INSTANCE.getReceivedHandler();
        openedHandler.setHandlerInterface(this);
        receivedHandler.setHandlerInterface(this);
    }

    @Override
    public String getName() {
        return "IrisLibrary";
    }

    @ReactMethod
    public void initIris() {
        OneSignal.sdkType = "react";
        initNotifications();
    }

    private void initNotifications() {
        Context context = mReactApplicationContext.getCurrentActivity();
        if (irisInitDone) {
            Log.e("IrisLibrary", "Already initialized the Iris React-Native SDK");
            return;
         }
   
         irisInitDone = true;
        if (context == null) {
            // in some cases, especially when react-native-navigation is installed,
            // the activity can be null, so we can initialize with the context instead
            context = mReactApplicationContext.getApplicationContext();
        }

        SDKIris.INSTANCE.init(context);
        Log.i("IrisLibrary", "Notification service initialized");
    }


    @ReactMethod
    public void sendTag(String key, String value) {
        SDKIris.INSTANCE.sendTag(key, value);
        Log.i("IrisLibrary", "Tag sended successfully");

    }
    @ReactMethod
    public void addCustomer(@Nullable String cpf, @Nullable String phone, @Nullable String email, @Nullable String document) {
        Context context = getReactApplicationContext();
        SDKIris.INSTANCE.createIrisId(document, email, phone, cpf);
        Log.i("IrisLibrary", "Id-iris generation");
    }

    @ReactMethod
    public void create(ReadableMap user) throws JSONException {
        JSONObject newUser = ReactNativeJson.convertMapToJson(user);
        final String newUserData = newUser.toString();
        SDKIris.INSTANCE.createUser(newUserData);
        Log.i("IrisLibrary", "Bulk user sended");
    }

    @ReactMethod
    public void getNotificationList(Callback callback) throws JSONException {
        List<IrisNotification> notificationList = SDKIris.INSTANCE.getAllIrisNotifications();
        Collections.reverse(notificationList);
        Gson gson = new Gson();
        WritableArray notificationListRN = new WritableNativeArray();
        for ( IrisNotification item : notificationList){
            JSONObject jsonItem = new JSONObject(gson.toJson(item));
            WritableMap wm = ReactNativeJson.convertJsonToMap(jsonItem);
            notificationListRN.pushMap(wm);
        }
        Log.i("IrisLibrary", "Notification list");
        callback.invoke(notificationListRN);
    }

    @ReactMethod
    public void updateNotification(ReadableMap notification) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonNotification = ReactNativeJson.convertMapToJson(notification);
        IrisNotification newNotification = gson.fromJson(String.valueOf(jsonNotification), IrisNotification.class);
        SDKIris.INSTANCE.updateIrisNotification(newNotification);
    }

    @ReactMethod
    public void deleteNotification(ReadableMap notification) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonNotification = ReactNativeJson.convertMapToJson(notification);
        IrisNotification newNotification = gson.fromJson(String.valueOf(jsonNotification), IrisNotification.class);
        SDKIris.INSTANCE.deleteIrisNotification(newNotification);
    }

    @ReactMethod
    public void deleteAllNotifications(){
        SDKIris.INSTANCE.deleteAllIrisNotifications();
    }


    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }


    @Override
    public void onHostResume() {
        initIris();
    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
    @ReactMethod
    public void initNotificationOpenedHandlerParams() {
        this.hasSetNotificationOpenedHandler = true;
        if (this.coldStartNotificationResult != null) {
            this.notificationOpened(this.coldStartNotificationResult);
            this.coldStartNotificationResult = null;
        }

    }
    @Override
    public void notificationReceived(OSNotification notification) {
        Log.d("IrisLibrary", "Notification Received");
        this.sendEvent("Iris-remoteNotificationReceived", ReactNativeJson.convertJsonToMap(notification.toJSONObject()));
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        Log.d("IrisLibrary", "Notification Opened");
        if (!this.hasSetNotificationOpenedHandler) {
            this.coldStartNotificationResult = result;
            return;
        }
        this.sendEvent("Iris-remoteNotificationOpened", ReactNativeJson.convertJsonToMap(result.toJSONObject()));
    }

    private void sendEvent(String eventName, Object params) {
        Log.d(eventName, params.toString());
        mReactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @ReactMethod
    public void getPermissionSubscriptionState(final Callback callback) {
        OSPermissionSubscriptionState state = OneSignal.getPermissionSubscriptionState();

        if (state == null)
            return;

        OSPermissionState permissionState = state.getPermissionStatus();
        OSSubscriptionState subscriptionState = state.getSubscriptionStatus();
        OSEmailSubscriptionState emailSubscriptionState = state.getEmailSubscriptionStatus();

        // Notifications enabled for app? (Android Settings)
        boolean notificationsEnabled = permissionState.getEnabled();

        // User subscribed? (automatically toggles with notificationsEnabled)
        boolean subscriptionEnabled = subscriptionState.getSubscribed();

        // User's original subscription preference (regardless of notificationsEnabled)
        boolean userSubscriptionEnabled = subscriptionState.getUserSubscriptionSetting();

        try {
            JSONObject result = new JSONObject();

            result.put("notificationsEnabled", notificationsEnabled)
                    .put("subscriptionEnabled", subscriptionEnabled)
                    .put("userSubscriptionEnabled", userSubscriptionEnabled)
                    .put("pushToken", subscriptionState.getPushToken())
                    .put("userId", subscriptionState.getUserId())
                    .put("emailUserId", emailSubscriptionState.getEmailUserId())
                    .put("emailAddress", emailSubscriptionState.getEmailAddress());

            Log.d("IrisLibrary", "permission subscription state: " + result.toString());

            callback.invoke(ReactNativeJson.convertJsonToMap(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
