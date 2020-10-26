package hfut.software.hmp.edu.cn.backend.service;

import hfut.software.hmp.edu.cn.backend.bean.DrugNotice;
import hfut.software.hmp.edu.cn.backend.entity.NoticeList;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;

import java.util.List;

public interface DrugNoticeService {
    void setDrugNotice(DrugNotice drugNotice);
    //String robot, String room, String bed, String drugName,
    //                       String morningTime, String morningAmount,
    //                       String noonTime, String noonAmount,
    //                       String eveningTime, String eveningAmount,
    //                       String attention, String createId
    void cancelDrugNotice(String id);
    NoticeList listDrugNotice(int state, int currentPage, int pageSize);
    List<String> listAllDrug();
}
