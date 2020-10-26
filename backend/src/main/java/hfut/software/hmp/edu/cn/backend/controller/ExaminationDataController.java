package hfut.software.hmp.edu.cn.backend.controller;

import hfut.software.hmp.edu.cn.backend.bean.ExamData;
import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.service.ExaminationDataService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class ExaminationDataController {
    private ExaminationDataService examinationDataService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setExaminationDataService(ExaminationDataService examinationDataService){
        this.examinationDataService = examinationDataService;
    }

    @RequestMapping("/searchData")
    public Message loadHeartRate(@RequestBody Map<String, Object> map){
        String room = (String)map.get("room");
        String bed = (String)map.get("bed");
        String startDate = (String)map.get("startDate");
        String endDate = (String)map.get("endDate");
        if(startDate.equals(endDate)){
            List<ExamData> hourData = examinationDataService.getHourData(room, bed, startDate);

            return ResultUtil.success(hourData);
        }else{
            List<List<ExamData>> dayData = examinationDataService.getDayData(room, bed, startDate, endDate);
            return ResultUtil.success(dayData);
        }
    }

}
