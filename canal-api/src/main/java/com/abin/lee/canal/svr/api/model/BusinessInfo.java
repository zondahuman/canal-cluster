package com.abin.lee.canal.svr.api.model;

import com.abin.lee.canal.svr.api.enums.BusinessInfoColumnEnums;
import com.abin.lee.canal.svr.common.util.DateUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.primitives.Ints;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class BusinessInfo implements Serializable {
    private Integer id;
    private String userId;
    private String longitude;
    private String latitude;
    private String businessName;
    private BigDecimal businessPrice;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String category;
    private String field1;
    private String field2;
    private String flag;
    private Date createTime;
    private Date updateTime;
    private Integer version;

    public static BusinessInfo transfer(List<CanalEntry.Column> columns) {
        BusinessInfo businessInfo = new BusinessInfo();
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            String name = column.getName();
            String value = column.getValue();
            BusinessInfoColumnEnums instance = BusinessInfoColumnEnums.find(name);
            switch (instance) {
                case id:
                    businessInfo.setId(Ints.tryParse(value));
                    break;
                case user_id:
                    businessInfo.setUserId(value);
                    break;
                case longitude:
                    businessInfo.setLongitude(value);
                    break;
                case latitude:
                    businessInfo.setLatitude(value);
                    break;
                case business_name:
                    businessInfo.setBusinessName(value);
                    break;
                case business_price:
                    businessInfo.setBusinessPrice(BigDecimal.valueOf(Double.valueOf(value)));
                    break;
                case province_name:
                    businessInfo.setProvinceName(value);
                    break;
                case city_name:
                    businessInfo.setCityName(value);
                    break;
                case district_name:
                    businessInfo.setDistrictName(value);
                    break;
                case category:
                    businessInfo.setCategory(value);
                    break;
                case field1:
                    businessInfo.setField1(value);
                    break;
                case field2:
                    businessInfo.setField2(value);
                    break;
                case flag:
                    businessInfo.setFlag(value);
                    break;
                case create_time:
                    businessInfo.setCreateTime(DateUtil.getYMDHMSTime(value));
                    break;
                case update_time:
                    businessInfo.setUpdateTime(DateUtil.getYMDHMSTime(value));
                    break;
                case version:
                    businessInfo.setVersion(Ints.tryParse(value));
                    break;
                default:
                    break;
            }
        }
        return businessInfo;
    }


}