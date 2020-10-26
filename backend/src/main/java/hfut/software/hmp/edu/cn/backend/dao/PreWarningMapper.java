package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.GetWarningInfoResponse;
import hfut.software.hmp.edu.cn.backend.bean.PreWarningInfo;
import hfut.software.hmp.edu.cn.backend.bean.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PreWarningMapper {
    /**
     * 查询预警信息中的具体信息
     * @param robotId
     * @return
     */
    @Select("select name, age, bed from patient where" +
            " number in (select patient_number from room_assignment where robot_id = #{robotId})")
    UserInfo getUserInfo(String robotId);

    /**
     * 预警处理完后，手动设置完成标志
     * @param id
     * @return
     */
    @Update("update pre_warning set status = 11 where id = #{id}")
    int updateWarningInfoByid(Integer id);

    /**
     * 获取当前的有效预警信息的数量
     * 改进：补充创建时间在当前查询时间三分钟以内的
     * @return
     */
    @Select("select id,robot_id, room, crt_date from pre_warning where status=10")
    @Results(id = "WarningInfoMap", value = {
            @Result(property = "robotId",column = "robot_id"),
            @Result(property = "crtDate", column = "crt_date")
    })
    List<PreWarningInfo> getWarningInfo();


    @Select("select id, robot_id, room, crt_date from pre_warning where status=10")
    @Results(id = "WarningInfoList", value = {
            @Result(property = "robotId",column = "robot_id"),
            @Result(property = "crtDate", column = "crt_date"),
            @Result(property = "userInfo", column = "robot_id", javaType = UserInfo.class
            , one = @One(select = "hfut.software.hmp.edu.cn.backend.dao.PreWarningMapper.getUserInfo"))
    })
    List<GetWarningInfoResponse>  getWarningInfoList();
}
