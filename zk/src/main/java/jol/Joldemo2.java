package jol;


import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author huqk
 * @Date 2020/8/17 20:12
 * @Version 1.0
 */
public class Joldemo2 {

    static Joldemo joldemo = new Joldemo();
    public static void main(String[] args) {
        short a = 1;
        byte b =1;
        boolean c = true;
        int d = 2;
        double e = 2.0;
        float f = 1.2f;
        long g = 1;
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        System.out.println(ClassLayout.parseInstance(d).toPrintable());
        System.out.println(ClassLayout.parseInstance(e).toPrintable());
        System.out.println(ClassLayout.parseInstance(f).toPrintable());
        System.out.println(ClassLayout.parseInstance(g).toPrintable());
    }
}
