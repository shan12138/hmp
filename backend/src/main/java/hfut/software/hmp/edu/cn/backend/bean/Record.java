package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;

@Data
/**
 * 对应数据中表robot_assignment
 */
public class Record {
    /**
     * 对应数据库中的id
     */
    private String id;
    /**
     * 对应数据库中robot_id
     */
    private String robot;
    /**
     * 对应数据库中bed
     */
    private String bed;
    /**
     * 对应数据库中room
     */
    private String room;
    /**
     * 对应数据库中病人姓名
     */
    private String name;
    private String telephone;
    private String idcard;
    /**
     * 对应数据库中start_day
     */
    private String startDate;
    /**
     * 对应数据库中end_day
     */
    private String endDate;

    public Record(){

    }

    public Record(String id, String robot, String bed, String room, String startDay, String endDay) {
        this.id = id;
        this.robot = robot;
        this.bed = bed;
        this.room = room;
        this.startDate = startDay;
        this.endDate = endDay;
    }

    public Record(String id, String robot, String bed, String room,
                  String name, String telephone, String idcard,
                  String startDate, String endDate) {
        this.id = id;
        this.robot = robot;
        this.bed = bed;
        this.room = room;
        this.name = name;
        this.telephone = telephone;
        this.idcard = idcard;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
