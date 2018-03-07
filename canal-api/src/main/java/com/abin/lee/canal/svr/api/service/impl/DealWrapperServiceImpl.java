package com.abin.lee.canal.svr.api.service.impl;

import com.abin.lee.canal.svr.api.enums.TableEnums;
import com.abin.lee.canal.svr.api.model.BusinessInfo;
import com.abin.lee.canal.svr.api.model.Team;
import com.abin.lee.canal.svr.api.search.SearchWrapperService;
import com.abin.lee.canal.svr.api.service.DealWrapperService;
import com.abin.lee.canal.svr.common.util.JsonUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by abin on 2018/1/30 13:16.
 * canal-svr
 * com.abin.lee.canal.svr.api.schema
 */
@Slf4j
@Service
public class DealWrapperServiceImpl implements DealWrapperService {

    @Resource
    SearchWrapperService searchWrapperService;

    public void deal(List<CanalEntry.Column> columns, CanalEntry.Entry entry) {
        String tableName = entry.getHeader().getTableName();
        TableEnums tableEnums = TableEnums.find(tableName);
        switch (tableEnums) {
            case team:
                Team team = Team.transfer(columns);
                System.out.println("team=" + JsonUtil.toJson(team));
                break;
            case business_info:
                BusinessInfo businessInfo = BusinessInfo.transfer(columns);
                System.out.println("businessInfo=" + JsonUtil.toJson(businessInfo));
                this.searchWrapperService.createIndex(businessInfo);
                break;
            default:
                for (CanalEntry.Column column : columns) {
                    System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
                }
                break;
        }
    }

}
