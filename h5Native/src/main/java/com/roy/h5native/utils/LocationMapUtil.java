package com.roy.h5native.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2022/6/24 13:44
 * author : Roy
 * version: 1.0
 */
public class LocationMapUtil {
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    LocationListener mListener;

    AMapLocationListener aMapLocationListener =   new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                          /*  amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                            amapLocation.getLatitude();//获取纬度
                            amapLocation.getLongitude();//获取经度
                            amapLocation.getAccuracy();//获取精度信息*/
                          if(mListener!=null) {
                              mListener.locationSuccess(amapLocation);
                          }

                } else {
                       /*     //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                            Log.e("AmapError","location Error, ErrCode:"
                                    + amapLocation.getErrorCode() + ", errInfo:"
                                    + amapLocation.getErrorInfo());*/
                       if(mListener !=null) {
                           mListener.locationError();
                       }
                }
            }
        }
    };


    public AMapLocationClient startLocation(Context context,LocationListener mListener) {
        this.mListener = mListener;
        if (mlocationClient == null) {

            mlocationClient = new AMapLocationClient(context.getApplicationContext());
            mLocationOption = new AMapLocationClientOption();

            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            //设置定位监听
            mlocationClient.setLocationListener(aMapLocationListener);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        }
        mlocationClient.startLocation();
        return mlocationClient;
    }


    public void destroy() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
        if (aMapLocationListener != null && mlocationClient != null) {
            mlocationClient.unRegisterLocationListener(aMapLocationListener);
        }
    }


   public interface  LocationListener{
        void locationSuccess(AMapLocation amapLocation);
        void locationError();
    }

}
