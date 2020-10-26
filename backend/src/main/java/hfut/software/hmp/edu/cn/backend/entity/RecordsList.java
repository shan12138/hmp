package hfut.software.hmp.edu.cn.backend.entity;


import hfut.software.hmp.edu.cn.backend.bean.Record;

import java.util.List;

public class RecordsList {
    public int totalNum;
    public List<Record> robotData;

    public RecordsList(int totalNum, List<Record> robotData) {
        this.totalNum = totalNum;
        this.robotData = robotData;
    }
}
