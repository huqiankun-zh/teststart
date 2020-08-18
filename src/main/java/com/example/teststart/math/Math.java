package com.example.teststart.math;

/**
 * 常见算法
 *
 * @Author huqk
 * @Date 2020/7/25 14:43
 * @Version 1.0
 */
public class Math {
    public static void main(String[] args) {

        for (int i = 1; i <20; i++) {
            System.out.println(f1(i));
        }

        for (int i=101;i<200;i++){
            if (f2(i)){
                System.out.println(i);
            }
        }

        for (int i=101;i<=999;i++){
            if (f3(i)){
                System.out.println(i);
            }
        }

        System.out.println(f4(79));

        int a=23,b=32;
        int c=f5(a,b);
        System.out.println("公倍数："+a*b/c+"公约数"+c);



    }

    //题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
    // 规律  兔子的规律为数列1,1,2,3,5,8,13,21....
    private static int f1(int x){
        if (x==1||x==2){return 1;}
        else {
            return  f1(x-1)+f1(x-2);
        }
    }

    //判断101-200之间有多少个素数，并输出所有素数。
    //程序分析：判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除，则表明此数不是素数，反之是素数。
    private static boolean f2(int x){
        for (int i = 2; i < x/2; i++) {
            if (x%i==0){
                return false;
            }
        }
        return true;
    }

    //打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
    private static boolean f3(int x){
        if (x<100||x>999){return false;}
        int a = x/100;
        int b = (x%100)/10;
        int c = x%10;

        if (x==(a*a*a+b*b*b+c*c*c)){
            return true;
        }
        return false;
    }

    //利用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示。
    private static String f4(int x){
        return x>=90?"A":(x>60?"B":"C");
    }

    //输入两个正整数m和n，求其最大公约数和最小公倍数。
    private static int f5(int m,int n){
        while (true){
            if ((m=m%n)==0){
                return n;
            }
            if ((n=n%m)==0){
                return m;
            }
        }
    }

    //一个数如果恰好等于它的因子之和，这个数就称为 "完数 "。例如6=1＋2＋3.编程   找出1000以内的所有完数
    private static void f6(){
        for (int i = 1; i < 1000; i++) {
            int s=0;
            for (int j = 1; j < i; j++) {
                if (i%j==0){s+=j;}
                if (s==i){
                    System.out.println("i="+i);
                }
            }
        }
    }

    //：有1、2、3、4个数字，能组成多少个互不相同且无重复数字的三位数？都是多少？
    private static void f7(){
        int d= 0;
        for (int a = 1; a <=4 ; a++) {
            for (int b = 1; b <=4 ; b++){
                for (int c = 1; c <=4 ; c++){
                    if (a!=b&&b!=c&&a!=c){
                        d+=1;
                        System.out.println(a*100+b*10+c);
                    }
                }
            }
        }
    }

    //猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不瘾，又多吃了一个   第二天早上又将剩下的桃子吃掉一半，又多吃了一个。以后每天早上都吃了前一天剩下   的一半零一个。到第10天早上想再吃时，见只剩下一个桃子了。求第一天共摘了多少
    private static  int f8(int day){
        //system.out.print(f8(1));
        if (day==10){
            return 1;
        }
        else {
            return (f8(day+1)+1)*2;
        }
    }

    //：求1+2!+3!+...+20!的和
    private static void f9(){
        int sum = 0;
        int fac = 1;

        for (int i=1;i<=20;i++){
            fac *=i;
            sum +=fac;
        }
    }

    //题目：求100之内的素数
    private static void f10(){
        int sum,i;
        for ( sum =2;sum<=100;sum++){
            for (i=2;i<sum/2;i++){
                if (sum%i==0){
                    break;
                }
            }
            if (i>sum/2){
                System.out.println("素数："+sum);
            }
        }
    }
    //求一个3*3矩阵对角线元素之和   利用双重for循环控制输入二维数组，再将a[i][i]累加后输出。
    private static void f11(){
        long sum =0;
        int arr[][]={{1,2,3},{2,3,4},{8,18,2}};
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <3 ; j++) {
                if (i==j){
                    sum+=arr[i][j];
                }
            }
        }
    }

    //排序
    private static void f12(){
        int[] arr = {1,5,2,4,6,7,8};
        for (int len=arr.length;--len>=0;){
            for (int i = 0; i <len ; i++) {
                if (arr[i]>arr[i+1]){
                    int tem = arr[i];
                    arr[i]=arr[i+1];
                    arr[i+1]=tem;
                }
            }
        }
    }

    //输入数组，最大的与第一个元素交换，最小的与最后一个元素交换，输出数组。
    private static void f13(){
        int[] arr = {1,2,31,23,42,3,4,5,4,6,7};
        int max=0,min=0;
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i]>arr[max]){max=i;}
            if (arr[i]<arr[min]){min=i;}
        }
    }
}
