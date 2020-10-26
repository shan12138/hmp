package hfut.software.hmp.edu.cn.backend.entity;

import hfut.software.hmp.edu.cn.backend.bean.DrugNotice;

import java.util.List;

public class NoticeList {
    public int totalNum;
    public List<DrugNotice> robotData;

    public NoticeList(int totalNum, List<DrugNotice> robotData) {
        this.totalNum = totalNum;
        this.robotData = robotData;
    }
}
