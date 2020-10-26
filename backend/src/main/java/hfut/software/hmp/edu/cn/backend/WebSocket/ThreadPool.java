package hfut.software.hmp.edu.cn.backend.WebSocket;

import hfut.software.hmp.edu.cn.backend.service.PreWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 配置Java线程池来进行数据库轮询业务
 */
@Component
public class ThreadPool {
    private ScheduledExecutorService executorService;
    //service层在spring中都是单例模式只能注入一次，
    // 但是该类是可多次实力话的所以讲service更改为static类型，
    // 让service归类所有
    private static PreWarningService preWarningService;

    @Autowired
    public void setPreWarningService(PreWarningService preWarningService){
        this.preWarningService = preWarningService;
    }

    public ThreadPool(){
        executorService = Executors.newScheduledThreadPool(10);
    }


    /**
     * 向线程仓库提交定时任务
     */
    public void monitorPreWarningInfo(){
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    preWarningService.monitoring();
                }catch (Exception error){
                    System.out.println("线程中断异常！");
                    System.out.println(error.getMessage());
                }
            }
        },0,20000, TimeUnit.MILLISECONDS);
    }

    /**
     * 线程池关闭
     */
    public void shutdownNow(){
        System.out.println("线程池关闭");
        executorService.shutdownNow();
    }
}
