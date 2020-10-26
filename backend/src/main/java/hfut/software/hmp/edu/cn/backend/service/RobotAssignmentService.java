package hfut.software.hmp.edu.cn.backend.service;

import hfut.software.hmp.edu.cn.backend.bean.DistributeRobotRequestBean;

import java.util.List;

public interface RobotAssignmentService {
    List<String> getRobotsList();
    List<String> getRoomsList();
    List<String> getBedsListByRoomId(String roomId);
    void assignRobot(DistributeRobotRequestBean distributeRobotRequestBean)throws Exception;
}
