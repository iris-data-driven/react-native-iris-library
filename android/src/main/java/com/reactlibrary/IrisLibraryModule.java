package com.reactlibrary;
import com.somosiris.mobileandroidsdk.SDKIris;

import android.app.Activity;
import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;


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
    }

    @ReactMethod
    public void initGeolocation() {
        Activity activity = getCurrentActivity();
        SDKIris.INSTANCE.startGeofenceService(activity);
    }

    @ReactMethod
    public void sendTag(String key, String value){
        SDKIris.INSTANCE.sendTag(value, key);

    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
