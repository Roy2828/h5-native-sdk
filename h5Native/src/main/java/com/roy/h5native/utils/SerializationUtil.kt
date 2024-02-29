package com.roy.h5native.utils

import android.os.Bundle
import android.os.Parcelable
import com.vondear.rxtool.RxEncodeTool
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *    desc   : 数据序列化工具
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/2/23 10:42
 *    author : Roy
 *    version: 1.0
 */
object SerializationUtil {


    /**
     * 序列化对象
     * @param wash T
     * @return T?
     */
    fun <T : Serializable> washObject(wash: T): T? {
        try {
            return readObject(getByteArrayInputStream(wash))
        } catch (e: Exception) {
            return null
        }
    }


    /**
     * 序列化集合
     * @param washListSerializable ArrayList<T>
     * @return ArrayList<T>?
     */
    fun <T : Serializable> washListSerializableObject(washListSerializable: ArrayList<T>): ArrayList<T>? {
        try {
            return readObjectList(getByteArrayInputStream(washListSerializable))
        } catch (e: Exception) {
            return null
        }
    }


    fun <T : Parcelable> washParcelObject(washParcel: T, creator: Parcelable.Creator<T>): T? {
        try {
            return ParcelUtils.deserializeParcelable(ParcelUtils.serializeParcelable(washParcel), creator)
        } catch (e: Exception) {
            return null
        }
    }


    private fun <T> getByteArrayInputStream(wash: T): ByteArrayInputStream {
        val baos = ByteArrayOutputStream()
        ObjectOutputStream(baos).use { oos ->
            oos.writeObject(wash)
            oos.flush()
        }
        val bais = ByteArrayInputStream(baos.toByteArray())
        return bais
    }


    private fun <T> readObject(bais: ByteArrayInputStream): T? {
        return ObjectInputStream(bais).use { ois ->
            ois.readObject() as? T
        }
    }


    private fun <T> readObjectList(bais: ByteArrayInputStream): ArrayList<T>? {
        return ObjectInputStream(bais).use { ois ->
            ois.readObject() as? ArrayList<T>
        }
    }

}