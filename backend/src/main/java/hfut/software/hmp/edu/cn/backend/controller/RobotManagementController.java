package hfut.software.hmp.edu.cn.backend.controller;

import hfut.software.hmp.edu.cn.backend.bean.DetailTime;
import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;
import hfut.software.hmp.edu.cn.backend.service.RobotManagementService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class RobotManagementController {
    private RobotManagementService robotManagementService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public void setRobotManagementService(RobotManagementService robotManagementService){
        this.robotManagementService = robotManagementService;
    }

    @PostMapping("listRecords")
    public Message loadRecords(@RequestBody Map<String,Object> map){
        int state = (Integer) map.get("state");
        int currentPage = (Integer) map.get("currentPage");
        int pageSize = (Integer)map.get("pageSize");
        try{
            RecordsList recordsList = null;
            recordsList = robotManagementService.getRecords(state, currentPage, pageSize);
            return ResultUtil.success(recordsList);
        }catch (Exception e){
            return ResultUtil.error("001",e.getMessage());
        }
    }

    @PostMapping("listRecordsByRobot")
    public Message loadRecordsByRobot(@RequestBody Map<String, Object> map){
        String queryCondition = (String)map.get("queryCondition");
        int state = (Integer)map.get("state");
        int currentPage = (Integer)map.get("currentPage");
        int pageSize = (Integer)map.get("pageSize");
        try{
            RecordsList recordList = null;
            recordList = robotManagementService.getRecordsByRobot(queryCondition, state, currentPage, pageSize);
            return ResultUtil.success(recordList);
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }

    @RequestMapping("listRecordsByPatient")
    public Message loadRecordsByPatient(@RequestBody Map<String, Object> map){
        String queryCondition = (String)map.get("queryCondition");
        int state = (Integer)map.get("state");
        int currentPage = (Integer)map.get("currentPage");
        int pageSize = (Integer)map.get("pageSize");
        try{
            RecordsList recordList = null;
            if(queryCondition==""){
                recordList = robotManagementService.getAllRecordsByPatient(state, currentPage, pageSize);
            }else{
                recordList = robotManagementService.getRecordsByPatient(queryCondition, state, currentPage, pageSize);
            }
            return ResultUtil.success(recordList);
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }

    @PostMapping("unbind")
    public Message unbind(@RequestBody Map<String, Object> map){
        String id = (String)map.get("id");
        try{
            robotManagementService.unbind(id);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }

    @PostMapping("detailTime")
    public Message detailTime(@RequestBody Map<String, Object> map){
        String id = (String)map.get("id");
        try{
            List<DetailTime> detailTimeList = robotManagementService.getDetailTime(id);
            return ResultUtil.success(detailTimeList);
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }

    @PostMapping("deleteDetailTime")
    public Message deleteDetailTime(@RequestBody Map<String, Object> map){
        String id = (String)map.get("id");
        try{
            robotManagementService.deleteDetailTime(id);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }

    @PostMapping("changeDetailTime")
    public Message changeDetailTime(@RequestBody Map<String, Object> map){
        String id = (String)map.get("id");
        String startTime = (String)map.get("startTime");
        String endTime = (String)map.get("endTime");
        try{
            robotManagementService.changeDetailTime(id, startTime, endTime);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error("001", e.getMessage());
        }
    }


}
