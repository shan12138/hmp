package hfut.software.hmp.edu.cn.backend.controller;


import hfut.software.hmp.edu.cn.backend.bean.Message;
import hfut.software.hmp.edu.cn.backend.service.UserService;
import hfut.software.hmp.edu.cn.backend.utils.ResultUtil;
import hfut.software.hmp.edu.cn.backend.utils.SpringSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class UserController extends BaseController{

    private final Logger logger  = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/noLogin")
    @ResponseBody
    public Message userNotLogin() {
        return ResultUtil.error("NO_USER", "用户未登陆");
    }

    @PostMapping("/login")
    public void userLogin() {
     }

    @PostMapping("/logout")
    public void userLogout() {
    }

    /**
     * 修改密码
     * @param strMap
     * @return
     */
    @PostMapping("/changePassword")
    public Message changePassword(@RequestBody Map<String, String> strMap, HttpSession session) {
        String originalPwd = strMap.get("originalPwd").trim();
        String newPwd = strMap.get("newPwd").trim();
        if (originalPwd == null || originalPwd.length() == 0) {
            return ResultUtil.error("0002", "新密码项不能为空");
        }
        if (newPwd == null || newPwd.length() == 0) {
            return ResultUtil.error("0002", "旧密码不能为空");
        }
        String userId = SpringSecurityUtil.getCurrentUser(session);
        if (userId == null || userId.length() == 0) {
            ResultUtil.error("0002", "系统错误，请重新登录！");
        }
        try {
            userService.updatePassword(originalPwd, newPwd, userId);
            return ResultUtil.success();
        } catch (Exception e) {
            logger.error("修改密码发生异常： {}", e.getMessage());
            return ResultUtil.error("0003", "未知错误请重试！");
        }
    }
}
