package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.bean.DrugNotice;
import hfut.software.hmp.edu.cn.backend.dao.DrugNoticeMapper;
import hfut.software.hmp.edu.cn.backend.entity.NoticeList;
import hfut.software.hmp.edu.cn.backend.service.DrugNoticeService;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DrugNoticeServiceImpl implements DrugNoticeService {
    private DrugNoticeMapper drugNoticeMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDrugNoticeMapper(DrugNoticeMapper drugNoticeMapper){
        this.drugNoticeMapper = drugNoticeMapper;
    }


    @Override
    public void setDrugNotice(DrugNotice drugNotice) {
        //String robot, String room, String bed, String drugName,
        //                              String morningTime, String morningAmount,
        //                              String noonTime, String noonAmount,
        //                              String eveningTime, String eveningAmount,
        //                              String attention, String createId
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = dateFormat.format(new Date());
        drugNoticeMapper.setDrugNotice(drugNotice.robot, drugNotice.room, drugNotice.bed, drugNotice.drugName,
                drugNotice.morningTime, Integer.parseInt(drugNotice.morningAmount),
                drugNotice.noonTime,Integer.parseInt(drugNotice.noonAmount),
                drugNotice.eveningTime, Integer.parseInt(drugNotice.eveningAmount),
                drugNotice.attention, drugNotice.releaseMen, currentTime);

    }

    @Override
    public void cancelDrugNotice(String id) {
        drugNoticeMapper.cancelDrugNoticeById(Integer.parseInt(id));

    }

    @Override
    public NoticeList listDrugNotice(int state, int currentPage, int pageSize) {
        int page = currentPage-1;
        int limit = pageSize;
        int count = drugNoticeMapper.getCountOfDrugNotice(state);
        List<DrugNotice> drugNotices = drugNoticeMapper.selectDrugNotice(state, page, limit);
        NoticeList recordsList = new NoticeList(count, drugNotices);
        return recordsList;
    }

    @Override
    public List<String> listAllDrug() {
        return drugNoticeMapper.selectAllDrug();
    }
}
