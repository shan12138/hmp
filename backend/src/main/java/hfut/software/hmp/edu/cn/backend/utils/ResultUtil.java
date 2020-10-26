package hfut.software.hmp.edu.cn.backend.utils;

import hfut.software.hmp.edu.cn.backend.bean.Message;

/**
 * 后端向前端返回的数据类
 * @author JinYue
 * @date 2019/2/12
 */
public class ResultUtil {

    public ResultUtil() {
    }

    /**
     * 接口请求成功(有返回数据)
     * @param object
     * @return
     */
    public static Message success(Object object){
        Message message = new Message("0000", "调用成功", object);
        return message;
    }

    /**
     * 接口请求成功（无返回数据）
     * @return
     */
    public static Message success(){
        return success(null);
    }

    /**
     * 接口请求失败
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static Message error(String errorCode, String errorMsg){
        Message message = new Message(errorCode, errorMsg, null);
        return message;
    }
}
