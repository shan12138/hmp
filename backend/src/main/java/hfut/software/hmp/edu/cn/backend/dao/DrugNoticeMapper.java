package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.DrugNotice;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface DrugNoticeMapper {

    /**
     * 设置用药提醒
     * @param robot
     * @param room
     * @param bed
     * @param drugName
     * @param morningTime
     * @param morningAmount
     * @param noonTime
     * @param noonAmount
     * @param eveningTime
     * @param eveningAmount
     * @param attention
     * @param createId
     * @param currentTime
     */
    @Insert("insert into drug_notice(robot, room, bed, drugName, morningTime, morningAmount, " +
            "noonTime, noonAmount, eveningTime, eveningAmount,attention, releaseMen, " +
            "releaseTime, state) values(#{robot}, #{room}, #{bed}, #{drugName}, #{morningTime}, #{morningAmount}, " +
            "#{noonTime}, #{noonAmount}, #{eveningTime}, #{eveningAmount}, #{attention}, #{createId}, #{currentTime}, 1)")
    void setDrugNotice(@Param("robot")String robot, @Param("room")String room,
                       @Param("bed")String bed, @Param("drugName")String drugName,
                       @Param("morningTime") String morningTime, @Param("morningAmount") int morningAmount,
                       @Param("noonTime") String noonTime, @Param("noonAmount") int noonAmount,
                       @Param("eveningTime") String eveningTime, @Param("eveningAmount") int eveningAmount,
                       @Param("attention") String attention, @Param("createId") String createId,
                       @Param("currentTime")String currentTime
                       );

    @Update("update drug_notice set state = 0 where id = #{id}")
    void cancelDrugNoticeById(@Param("id")int id);

    @Select("select count(*) from drug_notice where state != #{state}")
    int getCountOfDrugNotice(@Param("state")int state);


    @Select("select drug_notice.*, user.real_name from drug_notice, user where state != #{state} " +
            "and drug_notice.releaseMen = user.user_id" +
            " limit #{page}, #{limit}")
    List<DrugNotice> selectDrugNotice(@Param("state")int state,
                                      @Param("page")int page, @Param("limit")int limit);

    @Select("select name from drug")
    List<String> selectAllDrug();
}
