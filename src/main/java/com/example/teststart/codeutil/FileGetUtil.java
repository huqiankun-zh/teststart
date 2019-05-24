package com.example.teststart.codeutil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class FileGetUtil {
    public static String filedir = "D://CODEFILE/";


    public static void createDao(String tablename, Map<String, String> clmNameMa, Map<String, Integer> clmSize){
        StringBuffer buffer = new StringBuffer();
        String[] strs = tablename.split("_");
        if (strs!=null&&strs.length>0){
            Arrays.stream(strs).forEach(str->buffer.append(getUppercaseChar(str)));
        }
        try{
            CreateUtil.getEntity(buffer.toString(),tablename,clmNameMa,clmSize);
            CreateUtil.getMapper(buffer.toString());
            CreateUtil.getService(buffer.toString());
            CreateUtil.getServiceImpl(buffer.toString());
            CreateUtil.getController(buffer.toString());
            CreateUtil.getXml(buffer.toString());
        }
        catch (Exception e){

        }


    }



    public static  String getUppercaseChar(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
