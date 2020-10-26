package hfut.software.hmp.edu.cn.backend.utils;

import hfut.software.hmp.edu.cn.backend.bean.User;
import org.springframework.security.core.context.SecurityContextImpl;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpSession;

/**
 * 工具类
 * @author JinYue
 */
public class SpringSecurityUtil {
    /**
     * 获取当前的用户ID
     * @param session
     * @return
     */
    public static String getCurrentUser(HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        User user = (User) securityContext.getAuthentication().getPrincipal();
        return user.getUserId();
    }
}
