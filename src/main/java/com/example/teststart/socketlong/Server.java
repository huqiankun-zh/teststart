package com.example.teststart.socketlong;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    /**
     * 要处理客户端发来的对象，并返回一个对象，可实现该接口。
     */
    public interface ObjectAction{
        Object doAction(Object rev, Server server);
    }

    public static final class DefaultObjectAction implements ObjectAction{
        public Object doAction(Object rev,Server server) {
            System.out.println("处理并返回："+rev);
            return rev;
        }
    }

    public static void main(String[] args) {
        int port = 65432;
        Server server = new Server(port);
        server.start();
    }

    private int port;
    private volatile boolean running=false;
    private long receiveTimeDelay=3000;//超时未使用连接的时间

    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class,ObjectAction>();
    private Thread connWatchDog;

    public Server(int port) {
        this.port = port;
    }

    public void start(){
        if(running)return;
        running=true;
        connWatchDog = new Thread(new ConnWatchDog());
        connWatchDog.start();
    }

    @SuppressWarnings("deprecation")
    public void stop(){
        if(running)running=false;
        if(connWatchDog!=null)connWatchDog.stop();
    }

    public void addActionMap(Class<Object> cls,ObjectAction action){
        actionMapping.put(cls, action);
    }

    class ConnWatchDog implements Runnable{
        public void run(){
            try {
                //backlog  连接请求队列数（当大于操作系统允许的最大数时以操作系统为准）
                ServerSocket ss = new ServerSocket(port,5);
                while(running){
                    Socket s = ss.accept();   //获取客户端连接，启线程处理
                    new Thread(new SocketAction(s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.this.stop();
            }

        }
    }

    class SocketAction implements Runnable{
        Socket s;//客户端连接
        boolean run=true;
        long lastReceiveTime = System.currentTimeMillis();//最后一次接收时间
        public SocketAction(Socket s) {
            this.s = s;
        }
        public void run() {
            while(running && run){
                if(System.currentTimeMillis()-lastReceiveTime>receiveTimeDelay){  //当前时间减去上次连接时间，获得的时间间隔大于了超时未使用连接的时间
                    overThis();
                }else{
                    try {
                        InputStream in = s.getInputStream();
                        if(in.available()>0){//sockect  io  传入的参数
                            ObjectInputStream ois = new ObjectInputStream(in);
                            Object obj = ois.readObject();
                            lastReceiveTime = System.currentTimeMillis();//最后接收时间置为当前时间
                            System.out.println("接收：\t"+obj);
                            ObjectAction oa = actionMapping.get(obj.getClass());
                            oa = oa==null?new DefaultObjectAction():oa;
                            Object out = oa.doAction(obj,Server.this);
                            if(out!=null){//结果 通过sokect 返回
                                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                                oos.writeObject(out);
                                oos.flush();
                            }
                        }else{
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        overThis();
                    }
                }
            }
        }

        private void overThis() {//关闭此client 的socket连接
            if(run)run=false;
            if(s!=null){
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("关闭："+s.getRemoteSocketAddress());
        }

    }

}