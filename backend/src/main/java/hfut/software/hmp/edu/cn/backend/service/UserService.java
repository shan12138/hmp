package hfut.software.hmp.edu.cn.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 修改密码
     * @param originalPwd
     * @param newPwd
     * @param userId
     */
    void updatePassword(String originalPwd,String newPwd, String userId)throws Exception;
}
