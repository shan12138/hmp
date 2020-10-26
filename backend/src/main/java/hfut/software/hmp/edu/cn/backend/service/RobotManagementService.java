package hfut.software.hmp.edu.cn.backend.service;

import hfut.software.hmp.edu.cn.backend.bean.DetailTime;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;

import java.util.List;

public interface RobotManagementService {

    RecordsList getRecords(int state, int currentPage, int pageSize);
    RecordsList getRecordsByRobot(String robot, int state, int currentPage, int pageSize);
    RecordsList getRecordsByPatient(String patient, int state, int currentPage, int pageSize);
    RecordsList getAllRecordsByPatient(int state, int currentPage, int pageSize);
    void unbind(String recordId);
    List<DetailTime> getDetailTime(String recordId);
    void  deleteDetailTime(String id);
    void changeDetailTime(String id, String startTime, String endTime);
}
