package com.example.teststart.codeutil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CodeUtil {
    public static void main(String[] args){
        FileUtil.mkd();
        Map<String,Map> map = JdbcUtil.getByTableName("p_course_extend");
        Map<String,String> clmNameMa = map.get("type");
        Map<String,Integer> clmSize =  map.get("size");
        Map<String,Integer> clmNullAble =  map.get("nullable");

        FileGetUtil.createDao("p_course_extend",clmNameMa,clmSize);
    }


}
