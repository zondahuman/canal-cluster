package com.abin.lee.canal.svr.api.enums;

import com.google.common.base.Strings;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by abin on 2018/1/29 19:45.
 * canal-svr
 * com.abin.lee.canal.svr.api.enums
 */
@Getter
public enum TeamColumnEnums {

    id,
    team_name,
    create_time,
    update_time,
    version,;

    public static TeamColumnEnums find(String param) {
        if (Strings.isNullOrEmpty(param)) return null;
        for (TeamColumnEnums instance : values()) {
            if (StringUtils.equals(instance.name(), param))
                return instance;
        }
        return null;
    }


}


