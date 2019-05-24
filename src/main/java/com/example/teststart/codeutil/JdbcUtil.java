package com.example.teststart.codeutil;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcUtil {

    public static Map<String,Map> getByTableName(String tablename){
        Map<String,Map> map = new HashMap<>();
        Map<String,String> clmNameMa = new HashMap<>();
        Map<String,Integer> clmSize = new HashMap<>();
        Map<String,Integer> clmNullAble = new HashMap<>();
        Connection conn=null;
        String driver="com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://dev.mysql.gaodunwangxiao.com:3306/new_zt_product";
        String user ="gdtest";
        String password = "gdmysql_221";
        ResultSet set=null;
        try {

            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
            if (!conn.isClosed()){
                System.out.println("数据库连接成功");
            }
            DatabaseMetaData m_data= conn.getMetaData();
            if (m_data!=null){
                 set =m_data.getColumns(null,"%",tablename,"%");
                while (set.next()){
                 String columnname = set.getString("COLUMN_NAME");
                 String columnntype = set.getString("TYPE_NAME");
                 int datasize = set.getInt("COLUMN_SIZE");
                 int nullable = set.getInt("NULLABLE");
                    clmNameMa.put(columnname,columnntype);
                    clmSize.put(columnname,datasize);
                    clmNullAble.put(columnname,nullable);
                    System.out.println(columnntype+"---"+columnname+"----"+datasize+"---"+nullable);
                }
                map.put("type",clmNameMa);
                map.put("size",clmSize);
                map.put("nullable",clmNullAble);
                set.close();
            }
            conn.close();
        }
        catch (Exception E){
            E.printStackTrace();
        }

        finally {
            try {
                if (set!=null){
                    set.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (conn!=null){
                    conn.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
          return map;
        }
    }
}
