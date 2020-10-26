package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 获取当前预警的全部信息
 */
@Data
public class GetWarningInfoResponse extends PreWarningInfo {
    private UserInfo userInfo;

    public GetWarningInfoResponse() {
    }

    public GetWarningInfoResponse(String id, String uId, String room, Timestamp crtDate, UserInfo userInfo) {
        super(id, uId, room, crtDate);
        this.userInfo = userInfo;
    }
}
