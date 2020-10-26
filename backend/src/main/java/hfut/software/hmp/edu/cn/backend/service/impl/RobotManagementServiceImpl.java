package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.bean.DetailTime;
import hfut.software.hmp.edu.cn.backend.bean.Record;
import hfut.software.hmp.edu.cn.backend.dao.RobotManagementMapper;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;
import hfut.software.hmp.edu.cn.backend.service.RobotManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RobotManagementServiceImpl implements RobotManagementService {
    private RobotManagementMapper robotManagementMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public void setRobotManagementMapper(RobotManagementMapper robotManagementMapper){
        this.robotManagementMapper = robotManagementMapper;
    }

    @Override
    public RecordsList getRecords(int state, int page, int limit) {
        List<Record> recordList = null;
        page = (page - 1) * limit;
        recordList = robotManagementMapper.selectRecords(state, page, limit);
        int count = robotManagementMapper.getCountOfRecord(state);
        RecordsList resultRecordsList = new RecordsList(count, recordList);
        return resultRecordsList;
    }

    @Override
    public RecordsList getRecordsByRobot(String robot, int state,
                                         int page, int limit) {
        List<Record> recordList = null;
        page = (page - 1) * limit;
        recordList = robotManagementMapper.selectRecordsByRobot(robot, state, page, limit);
        int count = robotManagementMapper.getCountOfRecordByRobot(robot,state);
        RecordsList resultRecordsList = new RecordsList(count, recordList);
        return resultRecordsList;
    }

    @Override
    public RecordsList getRecordsByPatient(String queryCondition, int state,
                                           int currentPage, int pageSize) {
        List<Record> recordList = new ArrayList<>();
        int page = currentPage-1;
        int limit = pageSize;
        String patient = robotManagementMapper.selectNumberOfPatient(queryCondition);
        recordList = robotManagementMapper.selectRecordsByPatient(patient, state,page, limit);
        int totalCount = robotManagementMapper.getCountOfRecordByPatient(patient, state);
        RecordsList recordsList = new RecordsList(totalCount, recordList);
        return recordsList;
    }

    @Override
    public RecordsList getAllRecordsByPatient(int state, int currentPage, int pageSize) {
        List<Record> recordList = new ArrayList<>();
        int page = currentPage-1;
        int limit = pageSize;
        recordList = robotManagementMapper.selectAllRecordsByPatient(state,page, limit);
        int totalCount = robotManagementMapper.getAllCountOfRecordByPatient(state);
        RecordsList recordsList = new RecordsList(totalCount, recordList);
        return recordsList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void unbind(String recordId) {
        robotManagementMapper.deleteDetailTimeByRoomAssignmentId(Integer.parseInt(recordId));
        robotManagementMapper.updateRecord(Integer.parseInt(recordId));

    }

    @Override
    public List<DetailTime> getDetailTime(String recordId) {
        List<DetailTime> detailTimeList = new ArrayList<DetailTime>();
        detailTimeList = robotManagementMapper.selectDetailTime(Integer.parseInt(recordId));
        return detailTimeList;
    }

      @Override
    public void changeDetailTime(String id, String startTime, String endTime) {
        robotManagementMapper.changeDetailTimeById(Integer.parseInt(id), startTime, endTime);
    }

    @Override
    public void deleteDetailTime(String recordId) {
        robotManagementMapper.deleteDetailTimeById(Integer.parseInt(recordId));
    }
}
