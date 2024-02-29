package com.roy.h5native.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2024/2/29 17:29
 * author : Roy
 * version: 1.0
 */
public class ParcelUtils {


    // 序列化Parcelable对象到字节数组
    public static byte[] serializeParcelable(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle(); // 释放Parcel对象资源
        return bytes;
    }

    // 从字节数组反序列化Parcelable对象
    public static <T> T deserializeParcelable(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // 重置Parcel的读取位置
        T result = creator.createFromParcel(parcel);
        parcel.recycle(); // 释放Parcel对象资源
        return result;
    }


}
