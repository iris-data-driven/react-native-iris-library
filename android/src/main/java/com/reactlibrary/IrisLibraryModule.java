package com.reactlibrary;
import com.somosiris.mobileandroidsdk.SDKIris;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

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

    @ReactMethod
    public void initGeolocation() {
        Activity activity = getCurrentActivity();
        SDKIris.INSTANCE.startGeofenceService(activity);
        Log.i("Geolocation", "Service initialized");
    }

    @ReactMethod
    public void sendTag(String key, String value) {
        SDKIris.INSTANCE.sendTag(value, key);
        Log.i("Tags", "sended successfully");

    }
    @ReactMethod
    public void addCustomer(@Nullable String cpf, @Nullable String phone, @Nullable String email) {
        Context context = getReactApplicationContext();
        String userId = SDKIris.INSTANCE.getUserId(context, cpf, email, phone);
        SDKIris.INSTANCE.create
        Log.i("Id", "generated successfully");
    }
    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }
}
