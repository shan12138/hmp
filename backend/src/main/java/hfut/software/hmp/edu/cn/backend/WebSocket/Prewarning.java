package hfut.software.hmp.edu.cn.backend.WebSocket;

import hfut.software.hmp.edu.cn.backend.utils.SpringSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 创建webSocket的服务端
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Component
@ServerEndpoint(value = "/preWarning/webSocket")
public class Prewarning {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //记录当前的Session,为了后期后端向前端发送数据的时候使用
    private Session session;
    //将所有的用户存储起来，便于后期发送信息
    private static CopyOnWriteArraySet<Prewarning> prewarnings = new CopyOnWriteArraySet<>();
    //创建任务轮询线程
    private ThreadPool threadPool = new ThreadPool();

    /**
     * 连接成功调用的方法
     * @param session 可选参数 session为与某个客户端的连接会话
     */
    @OnOpen
    public void open(Session session){
        this.session = session;
        this.prewarnings.add(this);
        this.threadPool.monitorPreWarningInfo();
        logger.info("用户登录系统。");
    }

    /**
     * 客户端給服务端发送消息的时候调用
     * @param msg 接收到客户端的消息
     * @param session 可选参数
     */
    @OnMessage
    public void message(String msg, Session session){
        logger.info("收到来自前端的消息{}",msg);
    //    考虑是否 需要返回数据
    //    if(msg != null){
    //        this.sendMessage("Living……");
    //    }
    }

    /**
     * 连接关闭时调用的方法
     * @param session 可选参数
     */
    @OnClose
    public void close(Session session){
        this.prewarnings.remove(this);
        this.threadPool.shutdownNow();
        logger.info("用户退出");
    }

    /**
     * 发生错误时调用
     * @param session
     * @param errors
     */
    @OnError
    public void error(Session session, Throwable errors){
        logger.info("WebSocket 链接异常{}",errors.getStackTrace());
    }

    /**
     * 广播
     * 该方法不需要注解，是根据需求自己定义的方法
     * @param msg 要发给客户端的信息 1：表示有危险信息
     */
    public void sendMessageToAll(String msg){
        logger.info("广播信息开始……");
        for(Prewarning prewarning : prewarnings){
            try{
                prewarning.session.getBasicRemote().sendText(msg);
            }catch (IOException ioError){
                logger.error("信息发送失败{}",ioError.getMessage());
            }
        }
    }

    /**
     * 单用户发送
     * @param msg
     */
    public void sendMessage(String msg){
        try{
            this.session.getBasicRemote().sendText(msg);
        }catch (IOException ioError){
            logger.error("信息发送失败{}",ioError.getMessage());
        }
    }

}
