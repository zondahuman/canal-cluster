package com.abin.lee.canal.svr.api.service;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

/**
 * Created by abin on 2018/1/30 13:16.
 * canal-svr
 * com.abin.lee.canal.svr.api.schema
 */
public interface DealWrapperService {


    void deal(List<CanalEntry.Column> columns, CanalEntry.Entry entry);


}
