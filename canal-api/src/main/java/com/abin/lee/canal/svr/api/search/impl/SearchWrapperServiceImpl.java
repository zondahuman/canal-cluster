package com.abin.lee.canal.svr.api.search.impl;

import com.abin.lee.canal.svr.api.model.BusinessInfo;
import com.abin.lee.canal.svr.api.search.SearchWrapperService;
import com.abin.lee.canal.svr.common.util.DateUtil;
import com.abin.lee.canal.svr.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/30 13:36.
 * canal-svr
 * com.abin.lee.canal.svr.api.search
 */
@Slf4j
@Service
public class SearchWrapperServiceImpl implements SearchWrapperService {

    @Autowired
    Environment environment;


    public void createIndex(BusinessInfo businessInfo) {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String id = businessInfo.getId() + "";
            String userId = businessInfo.getUserId() + "";
            nvps.add(new BasicNameValuePair("id", id));
            nvps.add(new BasicNameValuePair("userId", userId));
            nvps.add(new BasicNameValuePair("longitude", businessInfo.getLongitude()));
            nvps.add(new BasicNameValuePair("latitude", businessInfo.getLatitude()));
//            nvps.add(new BasicNameValuePair("businessName", "清香"+ RandomUtils.nextInt(1, 10)));
            nvps.add(new BasicNameValuePair("businessName", businessInfo.getBusinessName()));
            nvps.add(new BasicNameValuePair("businessPrice", businessInfo.getBusinessPrice() + ""));
            nvps.add(new BasicNameValuePair("provinceName", businessInfo.getProvinceName()));
            nvps.add(new BasicNameValuePair("cityName", businessInfo.getCityName()));
            nvps.add(new BasicNameValuePair("districtName", businessInfo.getDistrictName()));
            nvps.add(new BasicNameValuePair("category", businessInfo.getCategory()));
            nvps.add(new BasicNameValuePair("field1", businessInfo.getField1()));
            nvps.add(new BasicNameValuePair("field2", businessInfo.getField2()));
            nvps.add(new BasicNameValuePair("flag", businessInfo.getFlag()));
            if(null != businessInfo.getCreateTime())
                nvps.add(new BasicNameValuePair("createTime", DateUtil.getYMDHMSTime(businessInfo.getCreateTime())));
            else
                nvps.add(new BasicNameValuePair("createTime",  ""));
            if(null != businessInfo.getCreateTime())
                nvps.add(new BasicNameValuePair("updateTime", DateUtil.getYMDHMSTime(businessInfo.getUpdateTime())));
            else
                nvps.add(new BasicNameValuePair("updateTime", ""));

            String httpURL = environment.getProperty("search.index.address");

            HttpPost httpPost = new HttpPost(httpURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
