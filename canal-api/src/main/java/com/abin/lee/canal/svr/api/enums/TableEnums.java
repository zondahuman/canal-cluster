package com.abin.lee.canal.svr.api.enums;

import com.google.common.base.Strings;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by abin on 2018/1/29 19:12.
 * canal-svr
 * com.abin.lee.canal.svr.api.enums
 */
@Getter
public enum TableEnums {

    team,
    business_info,;


    public static TableEnums find(String param) {
        if (Strings.isNullOrEmpty(param)) return null;
        for (TableEnums instance : values()) {
            if (StringUtils.equals(instance.name(), param))
                return instance;
        }
        return null;
    }

}
