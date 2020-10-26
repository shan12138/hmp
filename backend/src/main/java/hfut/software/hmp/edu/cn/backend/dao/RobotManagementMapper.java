package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.DetailTime;
import hfut.software.hmp.edu.cn.backend.bean.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Mapper
public interface RobotManagementMapper {

    @Select("select count(*) from room_assignment where state != #{state}")
    int getCountOfRecord(int state);

    @Select("select count(*) from room_assignment where robot_id = #{robot} and state != #{state}")
    int getCountOfRecordByRobot(@Param("robot") String robot, @Param("state")int state);

    @Select("select count(*) from room_assignment where patient_number = #{patient} and state != #{state}")
    int getCountOfRecordByPatient(@Param("patient") String patient, @Param("state")int state);

    @Select("select count(*) from room_assignment where state != #{state}")
    int getAllCountOfRecordByPatient(@Param("state")int state);


    @Select("select id, robot_id as robot, bed, room, start_day as startDate, end_day as endDate " +
            "from room_assignment where state != #{state} limit #{page}, #{limit}")
    List<Record> selectRecords(@Param("state")int state,
                               @Param("page")int page, @Param("limit")int limit);

    @Select("select id, robot_id as robot, bed, room, start_day as startDate, end_day as endDate " +
            "from room_assignment where robot_id = #{robot} and state != #{state} limit #{page}, #{limit}")
    List<Record> selectRecordsByRobot(@Param("robot")String robot, @Param("state")int state,
                                      @Param("page")int page, @Param("limit")int limit);

    @Select("select room_assignment.id, robot_id as robot, room_assignment.bed, room_assignment.room, " +
            "name, telephone, idcard, " +
            "start_day as startDate, end_day as endDate " +
            "from room_assignment,patient where patient_number=number and patient_number = #{patient} and " +
            "state != #{state} limit #{page}, #{limit}")
    List<Record> selectRecordsByPatient(@Param("patient") String patient, @Param("state")int state,
                                        @Param("page") int page, @Param("limit") int limit);

    @Select("select room_assignment.id, robot_id as robot, room_assignment.bed, room_assignment.room, " +
            "name, telephone, idcard, " +
            "start_day as startDate, end_day as endDate " +
            "from room_assignment,patient where patient_number=number and state != #{state} limit #{page}, #{limit}")
    List<Record> selectAllRecordsByPatient(@Param("state")int state,
                                        @Param("page") int page, @Param("limit") int limit);

    @Select("select number from patient where number = #{queryCondition} or name = #{queryCondition} " +
            "or id_card = #{queryCondition} or telephone = #{queryCondition}")
    String selectNumberOfPatient(@Param("queryCondition")String queryCondition);


    @Update("update room_assignment " +
            "set state = 0 " +
            "where id = #{recordId}")
    void updateRecord(@Param("recordId")int recordId);


    @Select("select id, day as date, start_time as startTime, end_time as endTime from detail_time " +
            "where room_assignment_id = #{recordId}")
    List<DetailTime> selectDetailTime(@Param("recordId")int recordId);

    @Update("UPDATE detail_time " +
            "SET start_time = #{startTime}, end_time = #{endTime} " +
            "WHERE id = #{id}")
    void changeDetailTimeById(@Param("id")int id,
                              @Param("startTime")String startTime, @Param("endTime")String endTime);

    @Delete("delete from detail_time where id = #{id}")
    void deleteDetailTimeById(@Param("id")int id);

    @Delete("delete from detail_time where room_assignment_id = #{roomAssignmentId}")
    void deleteDetailTimeByRoomAssignmentId(@Param("roomAssignmentId")int roomAssignmentId);


}
