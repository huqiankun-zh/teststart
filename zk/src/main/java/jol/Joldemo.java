package jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huqk
 * @Date 2020/8/17 19:56
 * @Version 1.0
 */
public class Joldemo {

    static Object getObj(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Integer(1));
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

    static void print(String message) {
        System.out.println(message);
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        Object obj = getObj();

        //查看对象内部信息
        print(ClassLayout.parseInstance(obj).toPrintable());

        //查看对象外部信息
        print(GraphLayout.parseInstance(obj).toPrintable());

        //获取对象总大小
        print("size : " + GraphLayout.parseInstance(obj).totalSize());
    }

}
