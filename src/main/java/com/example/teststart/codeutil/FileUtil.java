package com.example.teststart.codeutil;

import java.io.File;

public class FileUtil {
    public static String filedir = "D://CODEFILE/";

    public static void mkd(){
        File file = new File(filedir+"/entity");
        if (!file.exists()){
            System.out.println(file.getAbsolutePath());
            file.mkdir();
        }
        File dao = new File(filedir+"/dao");
        if (!dao.exists())dao.mkdir();
        File service = new File(filedir+"/service/impl");
        if (!service.exists())service.mkdirs();
        File controller = new File(filedir+"/controller");
        if (!controller.exists())controller.mkdir();
        File xml = new File(filedir+"/xml");
        if (!xml.exists())xml.mkdir();
    }
}
