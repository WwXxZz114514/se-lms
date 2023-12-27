package com.szuse.f4.model;

public class Area {

  private int areaId;
  private String areaName;

  public Area() {
  }

  public Area(int areaId, String areaName) {
    this.areaId = areaId;
    this.areaName = areaName;
  }

  public Area(String areaName) {
    this.areaName = areaName;
  }

  public Area(Area area) {
    this.areaId = area.areaId;
    this.areaName = area.areaName;
  }

  public int getAreaId() {
    return areaId;
  }

  public void setAreaId(int areaId) {
    this.areaId = areaId;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

}
