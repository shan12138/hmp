package hfut.software.hmp.edu.cn.backend.controller;

import hfut.software.hmp.edu.cn.backend.bean.GetWarningInfoResponse;
import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.service.PreWarningService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class PreWarningController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PreWarningService preWarningService;

    @Autowired
    public void setPreWarningService(PreWarningService preWarningService){
        this.preWarningService = preWarningService;
    }

    @PostMapping("/getWarningInfo")
    public Message getWarningInfo(){
        try{
            List<GetWarningInfoResponse> getWarningInfoResponses = preWarningService.getWarningInfo();
            return ResultUtil.success(getWarningInfoResponses);
        }catch (Exception e){
            return ResultUtil.error("0003", "后台异常，请联系管理员！");
        }
    }

    @PostMapping("/handelWarning")
    public Message handelWarningInfo(@RequestBody Map<String, Integer> map){
        Integer id = map.get("id");
        try{
            Boolean isSuccess = preWarningService.handelPrewarning(id);
            return ResultUtil.success(isSuccess);
        }catch (Exception e){
            return ResultUtil.error("0003","发生非预期错误");
        }
    }
}
