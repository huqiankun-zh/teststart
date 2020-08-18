package com.example.teststart.nio.localFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @Author huqk
 * @Date 2020/7/21 20:13
 * @Version 1.0
 */
public class TestNioLocalFile {

    private static final String READ_FILE = "D://read_file.txt";
    private static final String WRITE_FILE = "D://write_file.txt";

    public static void main(String[] args) {
        readFile();
        writeFile();
        readAndWrite();
    }

    private static final void readFile(){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(READ_FILE));
            FileChannel fc = fis.getChannel();

            int cap =1000;//使用ByteBuffer  单位即 字节
            ByteBuffer buffer = ByteBuffer.allocate(cap);
            System.out.println(buffer.limit());//读写最大限制
            System.out.println(buffer.position());//读写当前位置
            System.out.println(buffer.capacity());

            int len= -1;

            while ( (len=fc.read(buffer)) != -1 ){

                //注： clear() 方法只是想limit = capity   把position = 0， buffer中 byte[] 中的数据依然存在
                buffer.clear();
                byte[] bytes = buffer.array();

                String value = new String(bytes,0,len);

                System.out.println(value+"------");

                System.out.println("cap:"+buffer.capacity()+"   limit:"+buffer.limit()+"   position:"+buffer.position());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("there is no such file named :"+READ_FILE);
        }
        catch (IOException E){
            System.out.println("READ FILE ERROR");
            E.printStackTrace();
        }
        finally {
            if (fis != null){
                try {
                    fis.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private static final void writeFile(){
        FileOutputStream fos= null;

        try {
            fos = new FileOutputStream(new File(WRITE_FILE));

            FileChannel fc = fos.getChannel();

            String value = "你好hello你好你好你好你好你好";
            ByteBuffer buffer = Charset.forName("uft-8").encode(value);
            //缓存容量和limit是根据大小改变的
            System.out.println("cap:"+buffer.capacity()+"   limit:"+buffer.limit()+"   position:"+buffer.position());
            int len = 0;

            while ((len = fc.write(buffer)) != 0){
                //依次写入buffer
                System.out.println("write in length = "+len);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("there is no such file named :"+WRITE_FILE);
        }
        catch (IOException e){
            System.out.println("WRITE FILE ERROR");
            e.printStackTrace();
        }
        finally {
            if (fos != null){
                try {
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static final void readAndWrite(){
        FileInputStream fin = null;
        FileOutputStream fou = null;

        try {

            fin = new FileInputStream(new File(READ_FILE));
            FileChannel readChannel = fin.getChannel();
            fou = new FileOutputStream(new File(WRITE_FILE));
            FileChannel writeChannel = fou.getChannel();
            int cap =100;
            ByteBuffer buf = ByteBuffer.allocate(cap);
            int readLen = -1;

            while ((readLen = readChannel.read(buf)) != -1){

                //读写切换
                buf.flip();

                int out_len = 0;
                while ((out_len = writeChannel.write(buf)) !=0){
                    System.out.println("write in out_len = "+out_len);
                }
                buf.clear();
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (fin != null){
                try {
                    fin.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fou != null){
                try {
                    fou.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
