package hfut.software.hmp.edu.cn.backend.service;

import hfut.software.hmp.edu.cn.backend.bean.ExamData;

import java.util.List;


public interface ExaminationDataService {

    List<ExamData> getHourData(String room, String bed, String day);
    List<List<ExamData>> getDayData(String room, String bed, String startDay, String endDay);
}
