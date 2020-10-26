package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.ExamData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ExaminationDataMapper {

    @Select("SELECT 1nd, 2nd, 3nd, 4nd, 5nd, 6nd, 7nd, 8nd " +
            "FROM ${table} " +
            "WHERE room = #{room} AND bed = #{bed} AND day = #{day}")
    ExamData selectHourData(@Param("room")String room, @Param("bed")String bed,
                            @Param("day")String day, @Param("table")String table);


    @Select("select day, (1nd+2nd+3nd+4nd+5nd+6nd+7nd+8nd)/8 as avg " +
            "from ${table} " +
            "where room = #{room} and bed = #{bed} and " +
            "day >= #{startDay} and day <= #{endDay}")
    List<ExamData>  selectDayData(@Param("room")String room, @Param("bed")String bed,
                                  @Param("startDay")String startDay, @Param("endDay")String endDay,
                                  @Param("table")String table);

}
