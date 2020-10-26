package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;

@Data
public class Role {
    /**
     * 数据库字段 role_id
     */
    private int roleId;
    /**
     * 数据库字段 role_code
     */
    private String roleCode;
    /**
     * 数据库字段 role_name
     */
    private String roleName;

    public Role() {
    }

    public Role(int roleId, String roleCode, String roleName) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
