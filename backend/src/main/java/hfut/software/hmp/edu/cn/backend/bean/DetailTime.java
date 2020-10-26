package hfut.software.hmp.edu.cn.backend.bean;

import lombok.Data;

@Data
/**
 * 对应数据库中表detail_time
 */
public class DetailTime {
    private String id;
    /**
     * 对应数据库中day
     */
    private String date;
    /**
     * 对应数据库中start_time
     */
    private String startTime;
    /**
     * 对应数据库中end_time
     */
    private String endTime;
}
