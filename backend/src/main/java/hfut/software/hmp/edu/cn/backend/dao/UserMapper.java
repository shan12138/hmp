package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Select("select account as username, user_id as userId, password, " +
            "real_name as realName from user where account = #{username}")
    User getUserByUsername(String username);

    /**
     * 查找用户密码
     * @param userId
     * @return
     */
    @Select("select password from user where user_id = #{userId}")
    String getOldPasswordByUserId(String userId);

    /**
     * 修改用户密码
     * @param newPwd
     * @return
     */
    @Update("update user set password = #{newPwd} where user_id = #{userId}")
    int updatePasswordByuserId(@Param("newPwd") String newPwd, @Param("userId") String userId);
}
