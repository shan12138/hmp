package hfut.software.hmp.edu.cn.backend.dao.provider;

import hfut.software.hmp.edu.cn.backend.bean.DistributeRobotRequestBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 机器人管理模块SQL语句生成
 */
public class RobotAssignmentProvider {
    public String insertInfoInRoomAssignment(DistributeRobotRequestBean distributeRobotRequestBean){
        return new SQL()
                .INSERT_INTO("room_assignment")
                .INTO_COLUMNS("robot_id", "room", "bed", "start_day", "end_day")
                .INTO_VALUES("#{robot}", "#{room}", "#{bed}", "#{startDate}", "#{endDate}")
                .toString();
    }

    public String insertTimeInDetail(Map<String, Object> insertTimeMap)throws Exception{
        StringBuilder insertBuilder  =
                new StringBuilder("insert into detail_time (room_assignment_id, day, start_time, end_time) values");
        int len = ((List<String>)insertTimeMap.get("dateList")).size();
        //List中元素的使用要通过数组的索引形式取出（如#{dateList[i]}）
        for (int i = 0; i< len - 1;i++){
            insertBuilder.append("(#{roomAssignmentId}," +
                                  "#{dateList["+i+"]}," +
                                  "#{startTime}," +
                                  "#{endTime}),");
        }

        insertBuilder.append("(#{roomAssignmentId}," +
                             "#{dateList["+(len-1)+"]}," +
                             "#{startTime},#{endTime});");
        return insertBuilder.toString();
    }
}
