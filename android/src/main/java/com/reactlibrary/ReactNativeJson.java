package com.reactlibrary;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ReactNativeJson {
    public static WritableMap convertJsonToMap(JSONObject jsonObject){
        WritableMap map = new WritableNativeMap();

        if (jsonObject == null) {
            return null;
        }

        Iterator<String> iterator = jsonObject.keys();
        if (!iterator.hasNext()) {
            return null;
        }

        while (iterator.hasNext()) {
            String key = iterator.next();

            try {
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    map.putMap(key, convertJsonToMap((JSONObject) value));
                } else if (value instanceof JSONArray) {
                    map.putArray(key, convertJsonToArray((JSONArray) value));
                } else if (value instanceof  Boolean) {
                    map.putBoolean(key, (Boolean) value);
                } else if (value instanceof  Integer) {
                    map.putInt(key, (Integer) value);
                } else if (value instanceof  Double) {
                    map.putDouble(key, (Double) value);
                } else if (value instanceof String)  {
                    map.putString(key, (String) value);
                } else {
                    map.putString(key, value.toString());
                }
            } catch(JSONException e) {

            }
        }
        return map;
    }

    public static WritableArray convertJsonToArray(JSONArray jsonArray){
        WritableArray array = new WritableNativeArray();
        
        if (jsonArray == null) {
            return null;
        }

        if (jsonArray.length() <= 0) {
            return null;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object value = jsonArray.get(i);
                if (value instanceof JSONObject) {
                    array.pushMap(convertJsonToMap((JSONObject) value));
                } else if (value instanceof  JSONArray) {
                    array.pushArray(convertJsonToArray((JSONArray) value));
                } else if (value instanceof  Boolean) {
                    array.pushBoolean((Boolean) value);
                } else if (value instanceof  Integer) {
                    array.pushInt((Integer) value);
                } else if (value instanceof  Double) {
                    array.pushDouble((Double) value);
                } else if (value instanceof String)  {
                    array.pushString((String) value);
                } else {
                    array.pushString(value.toString());
                }
            } catch (JSONException e) {

            }
        }
        return array;
    }

    public static JSONObject convertMapToJson(ReadableMap readableMap){
        JSONObject object = new JSONObject();
        if (readableMap == null) {
            return null;
        }
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        if (!iterator.hasNextKey()) {
            return null;
        }
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            ReadableType readableType = readableMap.getType(key);

            try {
                switch (readableType) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertArrayToJson(readableMap.getArray(key)));
                    break;
            }
            } catch (JSONException e) {

            }
        }
        return object;
    }

    public static JSONArray convertArrayToJson(ReadableArray readableArray){
        JSONArray array = new JSONArray();
        if (readableArray == null){
            return null;
        }

        for (int i = 0; i < readableArray.size(); i++) {
            ReadableType readableType = readableArray.getType(i);
            try {
                switch (readableType) {
                case Null:
                    break;
                case Boolean:
                    array.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.put(readableArray.getDouble(i));
                    break;
                case String:
                    array.put(readableArray.getString(i));
                    break;
                case Map:
                    array.put(convertMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertArrayToJson(readableArray.getArray(i)));
                    break;
                }
            } catch (JSONException e) {

            }
            
        }
        return array;
    }
}