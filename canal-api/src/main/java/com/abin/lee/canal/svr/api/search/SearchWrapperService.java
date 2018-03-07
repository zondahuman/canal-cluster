package com.abin.lee.canal.svr.api.search;

import com.abin.lee.canal.svr.api.model.BusinessInfo;
import com.abin.lee.canal.svr.common.util.DateUtil;
import com.abin.lee.canal.svr.common.util.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/1/30 13:36.
 * canal-svr
 * com.abin.lee.canal.svr.api.search
 */
public interface SearchWrapperService {


    void createIndex(BusinessInfo businessInfo);





}
