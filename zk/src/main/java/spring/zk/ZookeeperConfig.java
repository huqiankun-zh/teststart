package spring.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.*;

/**
 * @Author huqk
 * @Date 2020/8/14 10:35
 * @Version 1.0
 */
@Configuration
public class ZookeeperConfig {

    private static final Logger logger = getLogger(ZookeeperConfig.class);

    @Value("${zookeeper.address}")
    private    String connectString;

    @Value("${zookeeper.timeout}")
    private  int timeout;

    @Bean("commonZkClient")
    public ZooKeeper getZookeeper(){
        ZooKeeper zooKeeper=null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Watcher.Event.KeeperState.SyncConnected==event.getState()){
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();

            logger.info("【初始化ZooKeeper连接状态....】={}",zooKeeper.getState());
        }
        catch (Exception e){

            ReentrantLock lock = new ReentrantLock();
            lock.lock();;
            lock.unlock();
            Condition condition = lock.newCondition();
        }
        return zooKeeper;
    }
}
