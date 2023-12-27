package com.szuse.f4.common;

public class ResponseJSON {
    private int code;
    private String msg;
    // In JSON
    private Object body;

    public ResponseJSON(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseJSON(int code, String msg, Object body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setBody(Object body) { this.body = body; }
    public Object getBody() { return body; }

}
