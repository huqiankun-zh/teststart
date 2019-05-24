package com.example.teststart.httpmaildict;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class GetUtil {

    public static void main(String[] args){
        String system_env = System.getenv("SYSTEM_ENV");
        int type=1602;
        try {
            switch (system_env){
                case "dev":setDataSourceLocal("t-maldives.gaodunwangxiao.com",type);break;
                case "test":setDataSourceLocal("t-maldives.gaodunwangxiao.com",type);break;
                case "prepare":setDataSourceLocal("pre-maldives.gaodunwangxiao.com",type);break;
                case "prudction":setDataSourceLocal("maldives.gaodunwangxiao.com",type);break;
                default:throw new IllegalArgumentException("数据库配置读取错误");
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("consul配置读取失败");
        }
    }

    public static void setDataSourceLocal(String url,int type){
        StringBuffer buffer = new StringBuffer("http://");
        buffer.append(url).append("/api/v1/dict/getdict?type=").append(type);
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(buffer.toString());
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("请求出错: "+ getMethod.getStatusLine());
            }


            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers){
                System.out.println(h.getName() + "------------ " + h.getValue());
            }
            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            response = new String(responseBody);
            System.out.println("----------response:" + response);
            JSONObject obs = JSONObject.fromObject(response);
            int http_code=Integer.parseInt(obs.get("http_code").toString());
            int status=Integer.parseInt(obs.get("status").toString());
            String info = obs.get("info").toString();
            JSONArray jrr = obs.getJSONArray("result");
            List list = new ArrayList(jrr.size());

            jrr.stream().forEach(jr->list.add(jr));
            System.out.println(list
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
