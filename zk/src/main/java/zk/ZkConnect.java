package zk;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author huqk
 * @Date 2020/8/14 10:10
 * @Version 1.0
 */
public class ZkConnect {
    private static Logger log= LogManager.getLogger(ZkConnect.class);
    public static final String zkServerClusterConnect = "192.168.159.129:2181,192.168.159.129:2182,192.168.159.129:2183";
    public static final String zkServerSingleConnect = "192.168.159.129:2181";
    //超时毫秒数
    public static final int timeout = 3000;

    public static ZooKeeper connet() throws IOException,InterruptedException {

        CountDownLatch cdl = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper(zkServerClusterConnect, timeout, new Watcher(cdl,"建立连接"));
        cdl.await();
        return zk;
    }

    public static void create(ZooKeeper zk,String nodePath,String nodeData) throws KeeperException, InterruptedException{
        log.info("开始创建节点：{"+nodePath+"}， 数据：{"+nodeData+"}");
        List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        CreateMode createMode = CreateMode.PERSISTENT;
        String result = zk.create(nodePath, nodeData.getBytes(), acl, createMode);
        //创建节点有两种，上面是第一种，还有一种可以使用回调函数及参数传递，与上面方法名称相同。
        log.info("创建节点返回结果：{"+result+"}");
    }

    /**
     * 查询结构
     * @param zk
     * @param nodePath
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static Stat queryStat(ZooKeeper zk, String nodePath) throws KeeperException, InterruptedException{
        log.info("准备查询节点Stat，path：{"+nodePath+"}" );
        Stat stat = zk.exists(nodePath, false);
        log.info("结束查询节点Stat，path：{"+nodePath+"}，version：{"+stat.getVersion()+"}");
        return stat;
    }

    /***
     *
     * @param zk
     * @param nodePath
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String queryData(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException{
        log.info("准备查询节点Data,path：{"+nodePath+"}");
        String data = new String(zk.getData(nodePath, false, queryStat(zk, nodePath)));
        log.info("结束查询节点Data,path：{"+nodePath+"}，Data：{"+data+"}");
        return data;
    }


    /**
     *
     * @param zk
     * @param nodePath
     * @param nodeData
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     * 重点：每次修改节点的version版本号都会变更，所以每次修改都需要传递节点原版本号，以确保数据的安全性。
     */
    public static Stat update(ZooKeeper zk,String nodePath,String nodeData) throws KeeperException, InterruptedException{
        //修改节点前先查询该节点信息
        Stat stat = queryStat(zk, nodePath);
        Stat newStat = zk.setData(nodePath, nodeData.getBytes(), stat.getVersion());
        //修改节点值有两种方法，上面是第一种，还有一种可以使用回调函数及参数传递，与上面方法名称相同。
        //zk.setData(path, data, version, cb, ctx);
        return stat;
    }

    /**
     *
     * @param zk
     * @param nodePath
     * @throws InterruptedException
     * @throws KeeperException
     */
    public static void delete(ZooKeeper zk,String nodePath) throws InterruptedException, KeeperException{
        //删除节点前先查询该节点信息
        Stat stat = queryStat(zk, nodePath);
        zk.delete(nodePath, stat.getVersion());
        //修改节点值有两种方法，上面是第一种，还有一种可以使用回调函数及参数传递，与上面方法名称相同。
        //zk.delete(path, version, cb, ctx);
    }
}

