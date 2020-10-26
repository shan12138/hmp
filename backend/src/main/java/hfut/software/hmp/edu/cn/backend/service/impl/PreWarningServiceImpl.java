package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.WebSocket.Prewarning;
import hfut.software.hmp.edu.cn.backend.bean.GetWarningInfoResponse;
import hfut.software.hmp.edu.cn.backend.bean.PreWarningInfo;
import hfut.software.hmp.edu.cn.backend.dao.PreWarningMapper;
import hfut.software.hmp.edu.cn.backend.service.PreWarningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreWarningServiceImpl implements PreWarningService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PreWarningMapper preWarningMapper;
    private Prewarning prewarning = new Prewarning();
    @Autowired
    public void setPreWarningMapper(PreWarningMapper preWarningMapper){
        this.preWarningMapper = preWarningMapper;
    }

    /**
     * 查询数据库是否存在危险警报信息
     * @throws Exception
     */
    @Override
    public void monitoring()throws Exception {
        logger.info("预警信息检查开始……");
        List<PreWarningInfo> warningInfoList = null;
        int count = 0;
        //数据库查询预警信息是否有变化，一旦发生了变化且为危险值时将信息发给前端
        warningInfoList = preWarningMapper.getWarningInfo();
        count = warningInfoList.size();
        if (count > 0) {
            //    发送信息给webSocket的客户端
            String msg = count == 1 ? warningInfoList.get(0).toString() : "信息过多前往监控中心内查看";
            prewarning.sendMessageToAll(msg);
        }
    }

    @Override
    public List<GetWarningInfoResponse> getWarningInfo(){
        List<GetWarningInfoResponse> warningList = preWarningMapper.getWarningInfoList();
        return warningList;
    }

    @Override
    public Boolean handelPrewarning(Integer id){
        if (id == null){
            logger.info("机器人数据编码为空");
            return false;
        }
        int updateCount = preWarningMapper.updateWarningInfoByid(id);
        return (updateCount > 0 ? true : false);
    }
}
