package com.szuse.f4.model;

import java.util.Date;

public class Seat {

  private String username;
  private String tel;
  private String password;
  private Integer id;
  private Date createTime;

  public Seat() {}

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getTel() { return tel; }
  public void setTel(String tel) { this.tel = tel; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public Date getCreateTime() { return createTime; }

  public Integer getId() { return id; }
}
