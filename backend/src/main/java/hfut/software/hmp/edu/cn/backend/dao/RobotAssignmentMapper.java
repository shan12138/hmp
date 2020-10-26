package hfut.software.hmp.edu.cn.backend.dao;

import hfut.software.hmp.edu.cn.backend.bean.DistributeRobotRequestBean;
import hfut.software.hmp.edu.cn.backend.dao.provider.RobotAssignmentProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RobotAssignmentMapper {
    /**
     * 获取机器人列表
     * @return
     */
    @Select("select robot_id from robot")
    List<String> selectAllRobots();

    /**
     * 获取房间列表
     * @return
     */
    @Select("select room_id from room")
    List<String> selectAllRooms();

    /**
     * 获取当前房间的床位号列表
     * @param roomId
     * @return
     */
    @Select("select * from bed where room_id = #{roomId}")
    List<String> selectBedByRoomId(String roomId);

    /**
     * 存储机器人分配的信息
     */
    @InsertProvider(type = RobotAssignmentProvider.class,method = "insertInfoInRoomAssignment")
    void insertInfoInRoomAssignment(DistributeRobotRequestBean distributeRobotRequestBean);

    /**
     * 获取room_assignment的id
     * @param robot
     * @param room
     * @param bed
     * @return
     */
    @Select("select id from room_assignment where robot_id = #{robot} and room = #{room} and bed = #{bed}")
    String selectRoomAssignmentIdByRobotIdAndRoomAndBed(@Param("robot") String robot,@Param("room") String room, @Param("bed")String bed);

    @InsertProvider(type = RobotAssignmentProvider.class, method = "insertTimeInDetail")
    void insertTimeInDetail(Map<String,Object> insertTimeMap);
}
