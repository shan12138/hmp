package hfut.software.hmp.edu.cn.backend.controller;

import hfut.software.hmp.edu.cn.backend.bean.DistributeRobotRequestBean;
import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.service.RobotAssignmentService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class RobotAssignmentController {
    private RobotAssignmentService robotAssignmentService;

    @Autowired
    public void setRobotAssignmentService(RobotAssignmentService robotAssignmentService){
        this.robotAssignmentService = robotAssignmentService;
    }

    @PostMapping("listRobots")
    public Message loadRobots(){
        try{
            List<String> robotList = null;
            robotList = robotAssignmentService.getRobotsList();
            return ResultUtil.success(robotList);
        }catch (Exception e){
            return ResultUtil.error("0001",e.getMessage());
        }
    }

    @PostMapping("listRooms")
    public Message loadRooms(){
        try{
            List<String> roomsList = null;
            roomsList = robotAssignmentService.getRoomsList();
            return ResultUtil.success(roomsList);
        }catch (Exception e){
            return ResultUtil.error("0001",e.getMessage());
        }
    }

    @PostMapping("listBeds")
    public Message loadBeds(@RequestBody Map<String,Object> map){
        String room = (String) map.get("room");
        try{
            List<String> bedsList = null;
            bedsList = robotAssignmentService.getBedsListByRoomId(room);
            return ResultUtil.success(bedsList);
        }catch (Exception e){
            return ResultUtil.error("0001",e.getMessage());
        }
    }

    @PostMapping("/distributeRobots")
    public Message distributeRobots(@RequestBody DistributeRobotRequestBean distributeRobotRequestBean ){
        try{
            robotAssignmentService.assignRobot(distributeRobotRequestBean);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error("0003","数据错误或重复分配");
        }
    }


}
