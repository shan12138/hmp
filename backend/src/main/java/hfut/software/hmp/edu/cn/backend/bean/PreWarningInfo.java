package hfut.software.hmp.edu.cn.backend.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreWarningInfo {
    private String id;
    private String robotId;
    private String room;
    private Timestamp crtDate;

    @Override
    public String toString() {
        String msg = "请注意：住在"+room+" 床的老人于"+ crtDate+"身体可能出现异常信息，请尽快处理！";
        return msg;
    }
}
