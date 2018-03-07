package com.abin.lee.canal.svr.api.enums;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by abin on 2018/1/29 19:45.
 * canal-svr
 * com.abin.lee.canal.svr.api.enums
 */
public enum BusinessInfoColumnEnums {
    id,
    user_id,
    longitude,
    latitude,
    business_name,
    business_price,
    province_name,
    city_name,
    district_name,
    category,
    field1,
    field2,
    flag,
    create_time,
    update_time,
    version,;

    public static BusinessInfoColumnEnums find(String param) {
        if (Strings.isNullOrEmpty(param)) return null;
        for (BusinessInfoColumnEnums instance : values()) {
            if (StringUtils.equals(instance.name(), param))
                return instance;
        }
        return null;
    }
}

