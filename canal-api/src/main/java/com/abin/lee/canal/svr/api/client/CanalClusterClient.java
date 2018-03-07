package com.abin.lee.canal.svr.api.client;

import com.abin.lee.canal.svr.api.enums.SchemaEnums;
import com.abin.lee.canal.svr.api.service.DealWrapperService;
import com.abin.lee.canal.svr.api.service.TradeWrapperService;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Canal HA:
 * http://blog.csdn.net/hackerwin7/article/details/38044327
 * http://zqhxuyuan.github.io/2017/10/10/Midd-canal/
 */
@Slf4j
@Component
public class CanalClusterClient {

    @Autowired
    DealWrapperService dealWrapperService;
    @Autowired
    TradeWrapperService tradeWrapperService;


    @PostConstruct
    public void connectCanal() {

        // 基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
//        CanalConnector connector = CanalConnectors.newClusterConnector("172.16.2.146:2181", "example", "canal", "canal");
        CanalConnector connector = CanalConnectors.newClusterConnector("172.16.2.132:3181,172.16.2.133:3181,172.16.2.134:3181", "example", "canal", "canal");
        long batchId = 0;
        while (true) {
            try {
                System.out.println("----------------------------Before connector.connect() --------------------------");
                connector.connect();
                System.out.println("----------------------------Middle connector.connect() --------------------------");
//                connector.subscribe("deal" + "." + "business_info");
                connector.subscribe("deal.business_info,deal.team");
                System.out.println("----------------------------After connector.connect() --------------------------");
//                connector.rollback();
                while (true) {
                    Message messages = connector.getWithoutAck(1000);
                    batchId = messages.getId();
                    int size = messages.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        try {
                            Thread.sleep(5000);
                            connector.ack(batchId); // 提交确认
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("No DATA!!!!!!!!!!!!!!!!!!!!!!!!");
                    } else {
                        printEntry(messages.getEntries());
                        connector.ack(batchId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("============================================================connect crash");
                // 处理失败, 按偏移量回滚数据
                connector.rollback(batchId);
            } finally {
//                connector.disconnect();
            }
        }
    }


    private void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList(), entry);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList(), entry);
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList(), entry);
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList(), entry);
                }
            }
        }
    }


    private void printColumn(List<CanalEntry.Column> columns, CanalEntry.Entry entry) {
        System.out.println("entry.getHeader().getTableName()=" + entry.getHeader().getTableName());
        String schemaName = entry.getHeader().getSchemaName();
        SchemaEnums schemaEnums = SchemaEnums.find(schemaName);
        switch (schemaEnums) {
            case deal:
                this.dealWrapperService.deal(columns, entry);
                break;
            case trade:
                this.tradeWrapperService.trade(columns, entry);
                break;
            default:
                for (CanalEntry.Column column : columns) {
                    System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
                }
                break;
        }
    }


}

