package hfut.software.hmp.edu.cn.backend.controller;

import hfut.software.hmp.edu.cn.backend.bean.DrugNotice;
import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.entity.NoticeList;
import hfut.software.hmp.edu.cn.backend.service.DrugNoticeService;
import hfut.software.hmp.edu.cn.backend.entity.RecordsList;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class DrugNoticeController {
    private DrugNoticeService drugNoticeService;

    @Autowired
    public void setDrugNoticeService(DrugNoticeService drugNoticeService){
        this.drugNoticeService = drugNoticeService;
    }

    @RequestMapping("setMedicineRemind")
    public Message setDrugNotice(@RequestBody DrugNotice drugNotice) {
        drugNoticeService.setDrugNotice(drugNotice);
        return ResultUtil.success();

    }

    @RequestMapping("deleteMedicineRemind")
    public Message cancelDrugNotice(@RequestBody Map<String, Object> map){
        String id = (String)map.get("id");
        drugNoticeService.cancelDrugNotice(id);
        return ResultUtil.success();
    }

    @RequestMapping("queryMedicineRemind")
    public Message loadDrugNotice(@RequestBody Map<String, Object> map){
        String queryCondition = (String)map.get("queryCondition");
        int state = (Integer)map.get("state");
        int currentPage = (Integer)map.get("currentPage");
        int pageSize = (Integer)map.get("pageSize");
        NoticeList drugNoticeList = drugNoticeService.listDrugNotice(state, currentPage, pageSize);
        return ResultUtil.success(drugNoticeList);
    }

    @RequestMapping("listMedicines")
    public Message ListeningTime(){
        List<String> drugList = drugNoticeService.listAllDrug();
        if(drugList!=null){
            return ResultUtil.success(drugList);
        }else{
            return ResultUtil.error("0001","没有药品");
        }
    }
}
