package com.abin.lee.canal.svr.common.search;


import com.abin.lee.canal.svr.common.enums.GeographyPosition;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by abin on 2018/1/24 16:13.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.util
 */
public class LantiRandom {

    /**
     * String start="116.134058,39.999443";
     * String end="116.764166,39.766016";
     *
     * @param args
     */
    public static void main(String[] args) {
        double minLatitude = 39.766016;   //最小纬度
        double maxLatitude = 39.999443;  //最大纬度
        double minLongitude = 116.134058; //最小经度
        double maxLongitude = 116.764166; //最大经度

        String result = randomLonLat(minLongitude, maxLongitude, minLatitude, maxLatitude, GeographyPosition.LONGITUDE);
        System.out.println("result=" + result);
        String result1 = randomLonLat(minLongitude, maxLongitude, minLatitude, maxLatitude);
        System.out.println("result1=" + result1);

    }

    public static String randomObject(GeographyPosition geographyPosition) {
        double minLatitude = 39.766016;   //最小纬度
        double maxLatitude = 39.999443;  //最大纬度
        double minLongitude = 116.134058; //最小经度
        double maxLongitude = 116.764166; //最大经度

        String result = randomLonLat(minLongitude, maxLongitude, minLatitude, maxLatitude, geographyPosition);
        return result;
    }

    /**
     * @param MinLon：最小经度 MaxLon： 最大经度   MinLat：最小纬度   MaxLat：最大纬度    type：设置返回经度还是纬度
     * @return
     * @throws
     * @Title: randomLonLat
     * latitude ：纬度，   longitude：经度
     * @Description: 在矩形内随机生成经纬度
     */
    public static String randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat, GeographyPosition type) {
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        if (type == GeographyPosition.LONGITUDE) {
            return lon;
        } else {
            return lat;
        }
    }

    /**
     * @param MinLon：最小经度 MaxLon： 最大经度   MinLat：最小纬度   MaxLat：最大纬度    type：设置返回经度还是纬度
     * @return
     * @throws
     * @Title: randomLonLat
     * latitude ：纬度，   longitude：经度
     * @Description: 在矩形内随机生成经纬度
     */
    public static String randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat) {
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        return lon + "," + lat;
    }

}
