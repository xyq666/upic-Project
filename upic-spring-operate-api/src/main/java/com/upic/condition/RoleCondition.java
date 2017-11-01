package com.upic.condition;

import com.upic.common.base.condition.BaseCondition;
import com.upic.enums.RoleStatusEnum;
import com.upic.enums.RoleTypeEnum;

/**
 * Created by zhubuqing on 2017/9/6.
 */
public class RoleCondition extends BaseCondition {
    private String roleName; //角色名

    private String content;

    private RoleStatusEnum status; //角色状态

    private RoleTypeEnum type; //角色类型

    public RoleCondition() {
        super();
    }

    public RoleCondition(String roleName, String content, RoleStatusEnum status, RoleTypeEnum type) {
        this.roleName = roleName;
        this.content = content;
        this.status = status;
        this.type = type;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RoleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoleStatusEnum status) {
        this.status = status;
    }

    public RoleTypeEnum getType() {
        return type;
    }

    public void setType(RoleTypeEnum type) {
        this.type = type;
    }
}
