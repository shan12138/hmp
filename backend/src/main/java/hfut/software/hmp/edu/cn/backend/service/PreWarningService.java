package hfut.software.hmp.edu.cn.backend.service;


import hfut.software.hmp.edu.cn.backend.bean.GetWarningInfoResponse;

import java.util.List;

public interface PreWarningService {
    /**
     * 获取预警信息
     */
    void monitoring()throws Exception;

    /**
     * 获得预警详细信息
     * @return
     */
    List<GetWarningInfoResponse> getWarningInfo();

    /**
     * 处理预警信息
     * @param id
     * @return
     */
    Boolean handelPrewarning(Integer id);
}
