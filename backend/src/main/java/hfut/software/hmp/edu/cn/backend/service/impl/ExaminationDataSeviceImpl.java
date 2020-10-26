package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.bean.ExamData;
import hfut.software.hmp.edu.cn.backend.dao.ExaminationDataMapper;
import hfut.software.hmp.edu.cn.backend.service.ExaminationDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationDataSeviceImpl implements ExaminationDataService {
    private ExaminationDataMapper examinationDataMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String[] types = {"心率", "体温", "血糖", "血压"};
    @Autowired
    public void setExaminationDataMapper(ExaminationDataMapper examinationDataMapper){
        this.examinationDataMapper = examinationDataMapper;
    }


    public String getTable(String type){
        String table = "";
        switch (type){
            case "体温":
                table = "temperature_data";
                break;
            case "心率":
                table = "heart_data";
                break;
            case "血糖":
                table = "sugar_data";
                break;
            case "血压":
                table = "pressure_data";
                break;
        }
        return table;
    }

    public List<ExamData> getHourData(String room, String bed, String day) {
        List<ExamData> hourData = new ArrayList<>();
        for(String type:types){
            ExamData data = examinationDataMapper.selectHourData(room, bed, day, getTable(type));
            hourData.add(data);
        }
        return hourData;
    }

    public List<List<ExamData>> getDayData(String room, String bed, String startDay, String endDay) {
        List<List<ExamData>> dayData = new ArrayList<>();
        for(String type:types){
            List<ExamData> data = examinationDataMapper.selectDayData(room, bed, startDay, endDay, getTable(type));
            dayData.add(data);
        }
        return dayData;
    }
}
