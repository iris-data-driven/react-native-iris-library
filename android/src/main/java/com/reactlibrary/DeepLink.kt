package com.reactlibrary

import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import com.somosiris.mobileandroidsdk.SDKIris
import java.util.*


    fun setDeepLinkingListener(owner: LifecycleOwner, context: ReactContext) {
        SDKIris.viewModel.deepLink.observe(owner, Observer {
            println(it)
        })
    }



private fun sendEvent(reactContext: ReactContext,
                          eventName: String,
                          @Nullable params: WritableMap) {
        reactContext
                .getJSModule(RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
    }
    private fun irisSendEvent(context: ReactContext, params: HashMap<String, String>) {
        println("PARAMETERS: $params")
        val reactContext: ReactContext = context
        val result: HashMap<String, String> = params
        val wmap = Arguments.createMap()
        for ((key, value) in result) {
            print(result)
            wmap.putString(key, value)
        }
        println("RESULT $wmap")
        sendEvent(reactContext, "DeepLinkingReceived", wmap)
    }





