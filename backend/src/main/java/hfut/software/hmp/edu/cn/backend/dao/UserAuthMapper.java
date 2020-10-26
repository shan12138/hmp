package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserAuthMapper {
    @Select("select r.role_code as roleCode from role as r where r.role_id in (" +
            "select u.role_id from user_auth as u where user_id = #{userId} and status > 0) ")
    List<Role> getRoles(String userId);
}
