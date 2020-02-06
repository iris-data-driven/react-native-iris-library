package com.reactlibrary;


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
        SDKIris.init(context: Context)
    }

    @ReactMethod
    public void initGeolocation() {
        SDKIris.startGeofenceService(this: Activity)
    }

    @ReactMethod
    public void sendTag(key: String, value: String){
        SDKIris.sendTag(value: value, tag: key)
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
