package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.bean.DistributeRobotRequestBean;
import hfut.software.hmp.edu.cn.backend.dao.RobotAssignmentMapper;
import hfut.software.hmp.edu.cn.backend.service.RobotAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RobotAssignmentServiceImpl implements RobotAssignmentService {
    private RobotAssignmentMapper robotAssignmentMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRobotAssignmentMapper(RobotAssignmentMapper robotAssignmentMapper){
        this.robotAssignmentMapper = robotAssignmentMapper;
    }

    @Override
    public List<String> getRobotsList(){
        List<String> robotsList = null;
        robotsList = robotAssignmentMapper.selectAllRobots();
        return robotsList;
    }

    @Override
    public List<String> getRoomsList() {
        List<String> roomsList = null;
        roomsList = robotAssignmentMapper.selectAllRooms();
        return roomsList;
    }

    @Override
    public List<String> getBedsListByRoomId(String roomId) {
        List<String> bedsList = null;
        bedsList = robotAssignmentMapper.selectBedByRoomId(roomId);
        return bedsList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void assignRobot(DistributeRobotRequestBean distributeRobotRequestBean) {
        robotAssignmentMapper.insertInfoInRoomAssignment(distributeRobotRequestBean);
        String roomAssignmentIdStr =
                robotAssignmentMapper.selectRoomAssignmentIdByRobotIdAndRoomAndBed(distributeRobotRequestBean.getRobot(),
                distributeRobotRequestBean.getRoom(), distributeRobotRequestBean.getBed());
        Integer roomAssignmentId = Integer.parseInt(roomAssignmentIdStr);
        Map<String, Object> insertTimeMap = new HashMap();
        try {
            List<String> dateList =
                    getDateList(distributeRobotRequestBean.getStartDate(),distributeRobotRequestBean.getEndDate());
            insertTimeMap.put("roomAssignmentId",roomAssignmentId);
            insertTimeMap.put("dateList",dateList);
            insertTimeMap.put("startTime",distributeRobotRequestBean.getStartTime());
            insertTimeMap.put("endTime", distributeRobotRequestBean.getEndTime());

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        robotAssignmentMapper.insertTimeInDetail(insertTimeMap);
    }

    /**
     * 获得时间列表
     * @param beginDateStr
     * @param endDateStr
     * @return
     * @throws Exception
     */
    private List<String> getDateList(String beginDateStr, String endDateStr)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(beginDateStr);
        Date endDate = sdf.parse(endDateStr);
        List<String> dateList = new ArrayList<>();
        dateList.add(sdf.format(startDate));
        //设置起始时间
        Calendar beginCalender = Calendar.getInstance();
        beginCalender.setTime(startDate);
        //设置结束时间
        Calendar endCalender = Calendar.getInstance();
        endCalender.setTime(startDate);
        //测试此日期是否在指定日期之后
        while(endDate.after(beginCalender.getTime())){
            beginCalender.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(sdf.format(beginCalender.getTime()));
        }
        return dateList;
    }
}
