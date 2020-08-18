package com.example.teststart.math;

/**
 * @Author huqk
 * @Date 2020/7/25 17:06
 * @Version 1.0
 */
public class Sort {


    public static void main(String[] args) {
        int[] arr = {1,2,4,5,6,7,8,4,5,12,24,5,6,7};

        Sort.out(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    //冒泡
    private static void out(int[] arr){
        for (int i = 0; i <arr.length ; i++) {
            boolean flag =false;
            for (int j = 0; j <arr.length-1-i ; j++) {
                if (arr[j]>arr[j+1]){
                    int tem = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tem;
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
    }

    //选择
    private static void cha(int[] arr){

        for (int i = 0; i <arr.length ; i++) {
            int min = arr[i];
            int index = i;

            for (int j = i+1; j <arr.length ; j++) {

                if (min>arr[j]){
                    min=arr[j];
                    index = j;
                }

            }
            int temp = arr[i];
            arr[i] = min;
            arr[index] = temp;
        }
    }

    //插入
    private static void insert(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j >0 ; j--) {
                if (arr[j]<arr[j-1]){
                    int temp = arr[j];
                    arr[j]= arr[j-1];
                    arr[j-1]= temp;
                }
                else {
                    break;
                }

            }
        }
    }
}
