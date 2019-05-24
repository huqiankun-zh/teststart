package com.example.teststart.codeutil;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class CreateUtil {
    public static String filedir = "D://CODEFILE/";
    private static final String RT_1 = "\r\n";
    private static final String RT_2 = RT_1+RT_1;
    private static final String BLANK_1 =" ";
    private static final String BLANK_2 =BLANK_1+BLANK_1;
    private static final String BLANK_3 =BLANK_2+BLANK_1;
    private static final String PACKAGEURL="com.gaodun.product.";


    private static final String ANNOTATION_AUTHOR_PARAMTER = "@author ";
    private static final String ANNOTATION_AUTHOR_NAME ="huqiankun";
    private static final String ANNOTATION_AUTHOR = ANNOTATION_AUTHOR_PARAMTER + ANNOTATION_AUTHOR_NAME;
    private static final String ANNOTATION_DATE = "@date ";
    private static final String ANNOTATION = "/**"+RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_AUTHOR +RT_1+BLANK_1+"*"+BLANK_1+ANNOTATION_DATE +getDate()+RT_1+BLANK_1+"*/"+RT_1;
    private static  Map<String,String> typemap;
    private static  Map<String,Integer> sizemap;
    private static String tablename;

    public static void setTypemap(Map<String, String> typemap) {
        CreateUtil.typemap = typemap;
    }
    public static void setTablename(String tablename) {
        CreateUtil.tablename = tablename;
    }

    public static void setSizemap(Map<String, Integer> sizemap) {
        CreateUtil.sizemap = sizemap;
    }

    //创建entity
    public static void getEntity(String classname,String tablename, Map<String,String> typemap, Map<String,Integer> sizemap)throws Exception{
        setTypemap(typemap);
        setSizemap(sizemap);
        setTablename(tablename);
        String filename = filedir+"entity/"+classname+".java";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("package  ").append(PACKAGEURL).append("entity"+";")
              .append(RT_2).append("import java.io.Serializable;")
              .append(RT_1).append("import java.util.Date;")
              .append(RT_2)
              .append(RT_2).append(ANNOTATION).append("public class ")
              .append(classname).append("  implements Serializable {")
              .append(RT_1).append("private static final long serialVersionUID = 1L;")
              .append(RT_2);
        typemap.entrySet().stream().forEach(enty->{
            buffer.append(RT_1).append(getClmString(enty));
        });
        typemap.entrySet().stream().forEach(entry->{
            buffer.append(RT_1).append(getAndSetStr(entry));
        });
        buffer.append(RT_2).append("}");
        fw.write(buffer.toString());
        fw.flush();
        fw.close();
        System.out.println(classname+".java创建完成");
    }

    //生成mapper接口
    public static void getMapper(String classname)throws Exception{
        String filename = filedir+"dao/"+classname+"Mapper.java";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("package  ").append(PACKAGEURL).append("dao"+";")
                .append(RT_2).append("import ").append(PACKAGEURL).append("entity.").append(classname).append(";")
                .append(RT_1).append("import org.apache.ibatis.annotations.Param;")
                .append(RT_1).append("import org.springframework.stereotype.Repository;")
                .append(RT_2).append("import java.util.List;")
                .append(RT_2).append(ANNOTATION)
                .append(RT_1).append("@Repository").append(RT_1)
                .append("public interface "+classname+"Mapper {")
                .append(RT_1).append(classname).append(" get(@Param(\"id\")short id);")
                .append(RT_1).append("List<").append(classname).append("> getAll();")
                .append(RT_1).append("Integer insert(@Param(\"vo\")")
                .append(classname).append(" vo);")
                .append(RT_1).append("Integer update(@Param(\"vo\")")
                .append(classname).append(" vo);")
                .append(RT_1).append("Integer delete(@Param(\"id\")short id);");
        buffer.append(RT_2).append("}");
        fw.write(buffer.toString());

        fw.flush();
        fw.close();
        System.out.println(classname+"Mapper.java创建完成");
    }

    //生成service
    public static void getService(String classname) throws Exception{
        String filename = filedir+"service/I"+classname+"Service.java";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("package  ").append(PACKAGEURL).append("service"+";")
                .append(RT_2).append("import ").append(PACKAGEURL).append("entity.").append(classname).append(";")
                .append(RT_2).append("import java.util.List;")
                .append(RT_2).append(ANNOTATION)
                .append(RT_1).append("public interface ")
                .append("I"+classname).append("Service {")
                .append(RT_1).append(BLANK_1).append(classname)
                .append(" get(int id);")
                .append(RT_1).append(BLANK_1).append("List<")
                .append(classname).append("> ")
                .append("getAll();")
                .append(RT_1).append(BLANK_1).append("Integer ")
                .append("insert(").append(classname).append(" vo);")
                .append(RT_1).append(BLANK_1).append("Integer ")
                .append("update(").append(classname).append(" vo);")
                .append(RT_1).append(BLANK_1).append("Integer ")
                .append("delete(short id);");
        buffer.append(RT_2).append("}");
        fw.write(buffer.toString());

        fw.flush();
        fw.close();
        System.out.println(classname+"Service.java创建完成");
    }



    //生成controller
    public static void getController(String classname)throws Exception{
        String filename = filedir+"controller/"+classname+"Controller.java";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        String service = "i"+classname+"Service";
        buffer.append("package  ").append(PACKAGEURL).append("controller" + ";")
                .append(RT_2).append("import ").append(PACKAGEURL).append("entity.").append(classname).append(";")
                .append(RT_1).append("import ").append(PACKAGEURL).append("service.I").append(classname).append("Service;")
                .append(RT_1).append("import org.slf4j.Logger;")
                .append(RT_1).append("import org.slf4j.LoggerFactory;")
                .append(RT_1).append("import org.springframework.http.HttpStatus;")
                .append(RT_1).append("import org.springframework.http.ResponseEntity;")
                .append(RT_1).append("import org.springframework.stereotype.Controller;")
                .append(RT_1).append("import org.springframework.web.bind.annotation.PathVariable;")
                .append(RT_1).append("import org.springframework.web.bind.annotation.PostMapping;")
                .append(RT_1).append("import org.springframework.web.bind.annotation.RequestBody;")
                .append(RT_1).append("import org.springframework.web.bind.annotation.RequestMapping;")
                .append(RT_2).append("import javax.annotation.Resource;")
                .append(RT_1).append("import java.util.HashMap;")
                .append(RT_1).append("import java.util.List;")
                .append(RT_1).append("import java.util.Map;")
                .append(RT_2).append(ANNOTATION).append(RT_1)
                .append("@Controller").append(RT_1)
                .append("@RequestMapping(\"/").append(classname).append("\")").append(RT_1)
                .append("public class ").append(classname).append("Controller {")
                .append(RT_1).append(BLANK_1).append("private Map<String,Object>")
                .append(" resultMap = new HashMap<String,Object>();")
                .append(RT_2).append(BLANK_1)
                .append("Logger logger = LoggerFactory.getLogger(getClass());")
                .append(RT_1).append(BLANK_1)
                .append("@Resource")
                .append(RT_1).append(BLANK_1)
                .append("private ").append(getFistUp("I"+classname+"Service"))
                .append(" "+"i"+classname+"Service;")
                .append(RT_2).append(BLANK_1).append("@PostMapping(\"/get/{id}\")")
                .append(RT_1).append(BLANK_1)
                .append("public ResponseEntity<").append(classname).append("> ")
                .append("getById(@PathVariable(\"id\")Short id){")
                .append(RT_1).append(BLANK_2).append("try {")
                .append(RT_1).append(BLANK_3)
                .append("logger.info(\"method:getById接收参数是：\"+id);")
                .append(RT_1).append(BLANK_3)
                .append(classname).append(" ").append(getFistLow(classname))
                .append(" = ").append(service).append(".get(id);")
                .append(RT_1).append(BLANK_3)
                .append("return ResponseEntity.ok(").append(getFistLow(classname)).append(");")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("catch (Exception e) {")
                .append(RT_1).append(BLANK_3)
                .append("logger.error(\"method:getById调用失败,id=\" + id, e);")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1)
                .append(" @PostMapping(\"/getAll\")")
                .append(RT_1).append(BLANK_1)
                .append("public ResponseEntity<List<").append(classname).append(">> getAll(){")
                .append(RT_1).append(BLANK_2).append("try {")
                .append(RT_1).append(BLANK_3)
                .append("List<").append(classname).append("> list =").append(service).append(".getAll();")
                .append(RT_1).append(BLANK_3)
                .append("return ResponseEntity.ok(list);")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("catch (Exception e) {")
                .append(RT_1).append(BLANK_3).append("logger.error(\"method:getAll调用失败\", e);")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1).append("@PostMapping(\"/insert\")")
                .append(RT_1).append(BLANK_1)
                .append("public Map<String,Object> insert(@RequestBody ")
                .append(classname).append(" vo){")
                .append(RT_1).append(BLANK_2).append("if(vo == null) {return null;}")
                .append(RT_1).append(BLANK_2).append("try {")
                .append(RT_1).append(BLANK_3)
                .append("logger.info(\"method:insert接收参数是：\"+vo);")
                .append(RT_1).append(BLANK_3)
                .append("Integer result = ").append(service).append(".insert(vo);")
                .append(RT_1).append(BLANK_3)
                .append("resultMap.put(\"result\",result==1?true:false);")
                .append(RT_1).append(BLANK_3)
                .append("logger.info(\"insert调用成功,vo:{}\"+vo);")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("catch (Exception e) {")
                .append(RT_1).append(BLANK_3).append("resultMap.put(\"msg\",e.getMessage());")
                .append(RT_1).append(BLANK_3).append("resultMap.put(\"result\",false);")
                .append(RT_1).append(BLANK_3).append("logger.error(\"method:insert调用失败,vo=\" + vo, e);")
                .append(RT_1).append(BLANK_2).append("}")
                .append(RT_1).append(BLANK_2).append("return resultMap;")
                .append(RT_1).append(BLANK_2).append("}");
        buffer.append(RT_2).append("}");
        fw.write(buffer.toString());

        fw.flush();
        fw.close();
        System.out.println(classname+"Controller.java创建完成");

    }


    //生成serviceImpl
    public static void getServiceImpl(String classname)throws Exception{
        String filename = filedir+"service/impl/"+classname+"ServiceImpl.java";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("package  ").append(PACKAGEURL).append("service.impl" + ";")
                .append(RT_2).append("import ").append(PACKAGEURL).append("entity.").append(classname).append(";")
                .append(RT_1).append("import org.springframework.beans.factory.annotation.Autowired;")
                .append(RT_1).append("import org.springframework.stereotype.Service;")
                .append(RT_1).append("import ").append(PACKAGEURL).append("dao.").append(classname).append("Mapper;")
                .append(RT_1).append("import ").append(PACKAGEURL).append("service.I").append(classname).append("Service;")
                .append(RT_2).append("import java.util.List;")
                .append(RT_2).append(ANNOTATION).append(RT_1)
                .append("@Service").append(RT_1)
                .append("public class ").append(classname).append("ServiceImpl")
                .append(" extends BaseServiceImpl implements I").append(classname).append("Service {")
                .append(RT_1).append(BLANK_1).append("@Autowired")
                .append(RT_1).append(BLANK_1).append("private ")
                .append(classname).append("Mapper ").append(getFistLow(classname)).append("Mapper;")
                .append(RT_2).append(BLANK_1).append("@Override")
                .append(RT_1).append(BLANK_1).append("public ").append(classname)
                .append(" get(short id) {").append(RT_1).append(BLANK_1)
                .append("return ").append(getFistLow(classname)).append("Mapper.get(id);")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1).append("@Override")
                .append(RT_1).append(BLANK_1).append("public List<").append(classname)
                .append("> getAll(){").append(RT_1).append(BLANK_1)
                .append("return ").append(getFistLow(classname)).append("Mapper.getAll();")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1).append("@Override")
                .append(RT_1).append(BLANK_1).append("public Integer insert(")
                .append(classname).append(" vo){")
                .append(RT_1).append(BLANK_1).append("return ")
                .append(getFistLow(classname)).append("Mapper.insert(vo);")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1).append("@Override")
                .append(RT_1).append(BLANK_1).append("public Integer update(")
                .append(classname).append(" vo){")
                .append(RT_1).append(BLANK_1).append("return ")
                .append(getFistLow(classname)).append("Mapper.update(vo);")
                .append(RT_1).append(BLANK_1).append("}")
                .append(RT_2).append(BLANK_1).append("@Override")
                .append(RT_1).append(BLANK_1).append("public Integer delete(short id){")
                .append(RT_1).append(BLANK_1).append("return ")
                .append(getFistLow(classname)).append("Mapper.delete(id);")
                .append(RT_1).append(BLANK_1).append("}");
        buffer.append(RT_2).append("}");
        fw.write(buffer.toString());

        fw.flush();
        fw.close();
        System.out.println(classname+"ServiceImpl.java创建完成");

    }
    //生产xml文件
    public static void getXml(String classname)throws Exception{
        String filename = filedir+"xml/"+classname+"Mapper.xml";
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append(RT_1).append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">")
                .append(RT_1).append("<mapper namespace=\"").append(PACKAGEURL).append("dao.")
                .append(classname).append("Mapper\">")
                .append(RT_1).append(BLANK_1).append("<sql id=\"columnlist\">")
                .append(getFieds()).append(RT_1).append(BLANK_1).append("</sql>")
                .append(RT_2).append(BLANK_1)
                .append("<select id=\"get\"  resultType=\"").append(PACKAGEURL)
                .append("entity.").append(classname).append("\">").append(RT_1)
                .append("SELECT <include refid=\"columnlist\" /> FROM ").append(tablename)
                .append(" t").append(RT_1).append(BLANK_2).append("WHERE  t.id=#{id}")
                .append(RT_1).append(BLANK_1).append("</select>")
                .append(RT_2).append(BLANK_1)
                .append("<select id=\"getAll\"  resultType=\"").append(PACKAGEURL)
                .append("entity.").append(classname).append("\">").append(RT_1)
                .append("SELECT <include refid=\"columnlist\" /> FROM ").append(tablename)
                .append(RT_1).append(BLANK_1).append("</select>")
                .append(RT_2).append(BLANK_1)
                .append("<insert id=\"insert\" parameterType=\"").append(PACKAGEURL).append("entity.")
                .append(classname).append("\" keyProperty=\"id\" useGeneratedKeys=\"true\">")
                .append(getInsertSql())
                .append(RT_2).append(BLANK_1)
                .append("<update id=\"update\" parameterType=\"").append(PACKAGEURL).append("entity.")
                .append(classname).append("\">")
                .append(RT_1).append(BLANK_2).append("update ").append(tablename)
                .append(getUpdateSql())
                .append(RT_1).append(BLANK_1).append("</update>")
                .append(RT_1).append("</mapper>");
        fw.write(buffer.toString());

        fw.flush();
        fw.close();
        System.out.println(classname+"Mapper.xml创建完成");
    }

    public static String getFieds(){
        StringBuffer buff=new StringBuffer();
        if (typemap!=null&&typemap.size()>0){
            Set<String> set = typemap.keySet();
            set.stream().forEach(str->
                buff.append(RT_1).append(BLANK_1).append(str).append(",")
            );
        }
        return buff.toString().substring(0,buff.toString().length()-1);
    }
    public static String getUpdateSql(){
        StringBuffer buff=new StringBuffer();
        if (typemap!=null&&typemap.size()>0){
            buff.append(RT_1).append(BLANK_2).append("<set>");
            Set<String> set = typemap.keySet();
            set.stream().forEach(str->
                buff.append(RT_1).append(BLANK_3).append("<if test=\"vo.")
                    .append(getClmname(str)).append(" !=null\"> ")
                    .append(str).append("= #{vo.").append(getClmname(str))
                    .append("},</if>")
                    );
            buff.append(RT_1).append(BLANK_2).append("</set>")
                .append(RT_1).append(BLANK_2).append("where id=#{vo.id}");
        }
        return buff.toString();
    }

    public static String getInsertSql(){
        StringBuffer buff=new StringBuffer();
        if (typemap!=null&&typemap.size()>0){
            buff.append(RT_1).append(BLANK_2).append("insert into ").append(tablename).append("(");
            Set<String> set = typemap.keySet();
            set.stream().forEach(str->
                    buff.append(RT_1).append(BLANK_2).append("<if test=\"vo.")
                            .append(getClmname(str)).append("!= null\">")
                            .append(str).append(",</if>")
            );
            buff.append(RT_1).append(BLANK_2).append(")")
                    .append(RT_1).append(BLANK_2).append("values (");
            set.stream().forEach(str->
                    buff.append(RT_1).append(BLANK_2).append("<if test=\"vo.")
                            .append(getClmname(str)).append("!= null\">")
                            .append("#{vo.").append(getClmname(str)).append("},</if>")
                    );
            buff.append(RT_1).append(BLANK_2).append(")")
                    .append(RT_1).append(BLANK_1).append("</insert>");
        }
        return buff.toString();
    }
    //生成日期
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    //example :public Interger getId(){return this.Id;}
    //example :public void setId(Integer id){this.id = id; }
    public static String getAndSetStr(Map.Entry entry){
        String key = entry.getKey().toString();
        String value = entry.getValue().toString();
        int size = sizemap.get(key);
        StringBuffer buf = new StringBuffer();
        buf.append(RT_1).append(BLANK_1).append("public ")
                .append(getType(value))
                .append(" get").append(getFistUp(getClmname(key)))
                .append("(){return this.")
                .append(getFistLow(getClmname(key))).append(";}")
                .append(RT_1).append(BLANK_1).append("public void ")
                .append(" set").append(getFistUp(getClmname(key)))
                .append("(").append(getType(value)).append(BLANK_1)
                .append(getFistLow(getClmname(key)))
                .append("){this.").append(getFistLow(getClmname(key)))
                .append("=").append(getFistLow(getClmname(key)))
                .append(";}");
        return buf.toString();
    }

    //example: private Integer Id;
    public static String getClmString(Map.Entry entry){
        String key = entry.getKey().toString();
        String value = entry.getValue().toString();
        int size = sizemap.get(key);
        StringBuffer buf = new StringBuffer();
        buf.append(BLANK_1).append("private ")
                .append(getType(value)).append(BLANK_1)
                .append(getFistLow(getClmname(key))).append(";");
        return buf.toString();
    }

    //根据字段type获取类型type
    public static String getType(String type){
        String typeresult = "String";
        if (type!=null&&type.length()>0){
            if (type.contains("SMALLINT")||type.contains("TINYINT")
            ||type.contains("MEDIUMINT")||type.contains("BOOLEAN")){
            return "Integer";
            }
            if (type.contains("VARCHAR")||type.contains("CHAR")||type.contains("TEXT")){
             return "String";
            }
            if (type.contains("BLOB")){
                return "byte[]";
            }
            if (type.contains("INTEGER")||type.contains("ID")){
                return "Long";
            }
            if (type.contains("BIT")){return "byte";}
            if (type.contains("BIGINT")){return "BigInteger";}
            if (type.contains("FLOAT")){return "Float";}
            if (type.contains("DOUBLE")){return "Double";}
            if (type.contains("DECIMAL")){return "BigDecimal";}
            if (type.contains("DATE")||type.contains("YEAR")){return "Date";}
            if (type.contains("TIME")){return "Time";}
            if (type.contains("DATETIME")||type.contains("TIMASTAMP")){
                return "Timestamp";
            }

        }
        return typeresult;
    }

    //根据表字段名获取属性name
    public static String getClmname(String str){
        String[] strs = str.toLowerCase().split("_");
        String result = "";
        if (strs!=null&&str.length()>0){
             result = strs[0];
            if (strs.length>1){
                for (int i=1;i<strs.length;i++){
                    result = result+ getFistUp(strs[i]);
                }
            }
        }
        return result;
    }

    //首字母大写
    public static String getFistUp(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
    //首字母小写
    public static String getFistLow(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }
}
