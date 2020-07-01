package com.reactlibrary;

import com.facebook.react.bridge.ReadableMap;
import com.somosiris.mobileandroidsdk.SDKIris;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Nullable;


public class IrisLibraryModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public IrisLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "IrisLibrary";
    }

    @ReactMethod
    public void initNotifications() {
        Context context = getReactApplicationContext();
        SDKIris.INSTANCE.init(context);
        Log.i("Notification", "Service initialized");
    }

//    @ReactMethod
//    public void initGeolocation() {
//        Activity activity = getCurrentActivity();
//        SDKIris.INSTANCE.startGeofenceService(activity);
//        Log.i("Geolocation", "Service initialized");
//    }

    @ReactMethod
    public void sendTag(String key, String value) {
        SDKIris.INSTANCE.sendTag(value, key);
        Log.i("Tags", "sended successfully");

    }
    @ReactMethod
    public void addCustomer(@Nullable String cpf, @Nullable String phone, @Nullable String email, @Nullable String document) {
        Context context = getReactApplicationContext();
        SDKIris.INSTANCE.createIrisId(document, email, phone, cpf);
        Log.i("Id", "generated successfully");
    }

    @ReactMethod
    public void create(ReadableMap user) throws JSONException {
        JSONObject newUser = ReactNativeJson.convertMapToJson(user);
        final String newUserData = newUser.toString();
        SDKIris.INSTANCE.createUser(newUserData);
        Log.i("BULK", "sended User");
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
    public void irisSendEvent(HashMap params) {
        ReactContext reactContext = getReactApplicationContext();
        HashMap<String, String> result = params;
        WritableMap wmap = Arguments.createMap();
        for (HashMap.Entry<String, String> entry : result.entrySet()) {
            wmap.putString(entry.getKey(), entry.getValue());
        }
        sendEvent(reactContext, "DeepLinkingReceived", wmap);
    }
    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }



}
