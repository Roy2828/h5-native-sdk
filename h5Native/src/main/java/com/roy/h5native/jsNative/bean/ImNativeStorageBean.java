package com.roy.h5native.jsNative.bean;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2024/2/20 16:07
 * author : Roy
 * version: 1.0
 */
public class ImNativeStorageBean {
    public String type;
    public String keyName;
    public String methodName;


    public ImNativeStorageBean() {
    }

    public ImNativeStorageBean(String type, String keyName, String methodName) {
        this.type = type;
        this.keyName = keyName;
        this.methodName = methodName;
    }
}
