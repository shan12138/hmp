package hfut.software.hmp.edu.cn.backend.service.impl;

import hfut.software.hmp.edu.cn.backend.bean.Role;
import hfut.software.hmp.edu.cn.backend.bean.User;
import hfut.software.hmp.edu.cn.backend.dao.UserAuthMapper;
import hfut.software.hmp.edu.cn.backend.dao.UserMapper;
import hfut.software.hmp.edu.cn.backend.service.UserService;
import hfut.software.hmp.edu.cn.backend.utils.EncryptPawwordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserMapper userMapper;
    private UserAuthMapper userAuthMapper;

    @Autowired
    public void setUserAuthMapper(UserAuthMapper userAuthMapper){
        this.userAuthMapper = userAuthMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public User loadUserByUsername(String username)throws UsernameNotFoundException{
        try {
            User user = userMapper.getUserByUsername(username);
            if (user == null) {
                logger.error("登录错误");
                throw new UsernameNotFoundException("用户不存在或者密码错误");
            }
            List<Role> roleList = userAuthMapper.getRoles(user.getUserId());
            user.setRoles(roleList);
            return user;
        } catch (Exception e) {
            logger.error("查询用户信息-登录{}", e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(String originalPwd, String newPwd, String userId)throws Exception {
        //查询原密码是否正确
        String oldPwd = userMapper.getOldPasswordByUserId(userId);
        if(oldPwd == null){
            throw new Exception("该用户不存在！");
        }
        Boolean isMatch = EncryptPawwordUtil.matchPassword(oldPwd, originalPwd);
        if (!isMatch){
            throw new Exception("密码错误！");
        }
        //更改密码
        newPwd = EncryptPawwordUtil.EncryptPassword(newPwd);
        int count = userMapper.updatePasswordByuserId(newPwd, userId);
        if(count != 1){
            throw new Exception("更改密码异常");
        }
    }
}
