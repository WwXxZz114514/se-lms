package com.szuse.f4.common;

public class ResponseJSON {
  private int code;
  private String msg;
  // In JSON
  private Object data;

  public ResponseJSON(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public ResponseJSON(int code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public void setdata(Object data) {
    this.data = data;
  }

  public Object getdata() {
    return data;
  }

}
