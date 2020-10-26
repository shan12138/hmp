package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;

/**
 *
 * 分配机器人
 */
@Data
public class DistributeRobotRequestBean {
    private String robot;
    private String room;
    private String bed;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public DistributeRobotRequestBean() {
    }

    public DistributeRobotRequestBean(String robot, String room, String bed, String startDate, String endDate, String startTime, String endTime) {
        this.robot = robot;
        this.room = room;
        this.bed = bed;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
