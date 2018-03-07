package com.abin.lee.canal.svr.api.model;

import com.abin.lee.canal.svr.api.enums.TeamColumnEnums;
import com.abin.lee.canal.svr.common.util.DateUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.primitives.Ints;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by abin on 2018/1/29 17:29.
 * canal-svr
 * com.abin.lee.canal.svr.api.model
 */
@Setter
@Getter
public class Team implements Serializable {

    private Integer id;
    private String teamName;
    private Date createTime;
    private Date updateTime;
    private Integer version;


    public static Team transfer(List<CanalEntry.Column> columns) {
        Team team = new Team();
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            String name = column.getName();
            String value = column.getValue();
            TeamColumnEnums instance = TeamColumnEnums.find(name);
            switch (instance) {
                case id:
                    team.setId(Ints.tryParse(value));
                    break;
                case team_name:
                    team.setTeamName(value);
                    break;
                case create_time:
                    team.setCreateTime(DateUtil.getYMDHMSTime(value));
                    break;
                case update_time:
                    team.setUpdateTime(DateUtil.getYMDHMSTime(value));
                    break;
                case version:
                    team.setVersion(Ints.tryParse(value));
                    break;
                default:
                    break;
            }
        }
        return team;
    }
}
