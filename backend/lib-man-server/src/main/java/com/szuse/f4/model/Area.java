package com.szuse.f4.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Area {

  @JsonProperty("area_id")
  private int areaId;
  @JsonProperty("area_name")
  private String areaName;
  @JsonProperty("row_num")
  private int areaRows;
  @JsonProperty("col_num")
  private int areaCols;

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

  public int getAreaRows() {
    return areaRows;
  }

  public void setAreaRows(int areaRows) {
    this.areaRows = areaRows;
  }

  public int getAreaCols() {
    return areaCols;
  }

  public void setAreaCols(int areaCols) {
    this.areaCols = areaCols;
  }

}
