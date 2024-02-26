package com.roy.h5native.utils;

import com.roy.h5native.jsNative.NativeManager;
import com.vondear.rxtool.RxSPTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2024/2/20 15:52
 * author : Roy
 * version: 1.0
 */
public class H5NativeStorageUtil {

    public static void setNativeStorage(String type, String key, String value) {
        String[] tags = convert(type);
        String spKey = returnResult(tags, key);
        String spValue = returnResult(tags, value);
        RxSPTool.putString(NativeManager.Companion.getInstance().getAppContext(), spKey, spValue);
    }


    public static String getNativeStorage(String type, String keyName, String methodName) {
        String[] tags = convert(type);
        String spKey = returnResult(tags, keyName);
        return returnSPValue(spKey, methodName);
    }


    public static String returnSPValue(String key, String methodName) {
        try {
            final JSONObject json = new JSONObject();
            String spValue = RxSPTool.getString(NativeManager.Companion.getInstance().getAppContext(), key);
            json.put(key, spValue);
            json.put(methodName,methodName);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String[] convert(String str) {
        if (str.contains("?")) {
            String str1 = str.substring(str.indexOf("?"));
            if (!"".equals(str1)) {
                String[] tags = str1.split("&");
                return tags;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String returnResult(String[] tags, String type) {
        if (tags != null && tags.length > 0) {
            for (int i = 0; i < tags.length; i++) {
                String tag = tags[i];
                if (tag.contains(type)) {
                    String result = tag.substring(tag.indexOf("="));
                    result = result.replace("=", "");
                    return result;
                }
            }
        } else {
            return "";
        }

        return "";
    }
}
