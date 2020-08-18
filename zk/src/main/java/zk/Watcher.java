package zk;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Author huqk
 * @Date 2020/8/14 10:13
 * @Version 1.0
 */
public class Watcher implements org.apache.zookeeper.Watcher {
    private static final Logger log = LoggerFactory.getLogger(Watcher.class);

    private CountDownLatch cdl;

    private String mark;

    public Watcher(CountDownLatch cdl,String mark) {
        this.cdl = cdl;
        this.mark = mark;
    }

    public void process(WatchedEvent event) {
        log.info(mark+" watcher监听事件：{}",event);
        cdl.countDown();
    }
    Enum a;
    Class b;
    private void test(){
        Test.values();
        b.getEnumConstants();
    }
}
