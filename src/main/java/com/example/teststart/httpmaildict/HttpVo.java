package com.example.teststart.httpmaildict;

import java.io.Serializable;
import java.util.List;

public class HttpVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer http_code;
    private Integer status;
    private String info;
    private List result;

    public Integer getHttp_code() {
        return http_code;
    }

    public void setHttp_code(Integer http_code) {
        this.http_code = http_code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
