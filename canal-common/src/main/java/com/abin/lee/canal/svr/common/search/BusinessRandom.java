package com.abin.lee.canal.svr.common.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by abin on 2018/1/25 18:36.
 * elasticsearch-svr
 * com.abin.lee.elasticsearch.svr.api.util
 */
public class BusinessRandom {
    private static Map<String, String> businessMap = Maps.newHashMap();
    private static List<String> businessList = Lists.newCopyOnWriteArrayList();

    static {
        businessList.add("六福珠宝");
        businessList.add("高岛屋日本桥店");
        businessList.add("海底捞");
        businessList.add("沙县小吃");
        businessList.add("小新饭桌");
        businessList.add("清和怀石");
        businessList.add("玛琪雅朵漫妙屋");
        businessList.add("靓之榭");
        businessList.add("小红帽木屋");
        businessList.add("金屋藏娇");
        businessList.add("非凡女廊");
        businessList.add("香水百合轩曼妙屋丽人行");
    }


    public static String findBusiness() {
        String businessName = businessList.get(RandomUtils.nextInt(0, businessList.size()));
        return businessName;
    }


    public static String findBusiness(Integer num) {
        String businessName = businessList.get(RandomUtils.nextInt(0, num));
        return businessName;
    }


    public static void main(String[] args) {
        String businessName = findBusiness();
        System.out.println("businessName="+businessName);
        String businessName3 = findBusiness(3);
        System.out.println("businessName3="+businessName3);
    }
}