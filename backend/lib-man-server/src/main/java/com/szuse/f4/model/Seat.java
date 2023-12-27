package com.szuse.f4.model;

public class Seat {

  private int seatId;
  private int seatRow;
  private int seatCol;
  private int areaId;

  public Seat() {}
  
  public Seat(int seatId, int seatRow, int seatCol, int areaId) {
    this.seatId = seatId;
    this.seatRow = seatRow;
    this.seatCol = seatCol;
    this.areaId = areaId;
  }

  public Seat(Seat seat) {
    this.seatId = seat.seatId;
    this.seatRow = seat.seatRow;
    this.seatCol = seat.seatCol;
    this.areaId = seat.areaId;
  }

  public int getSeatId() { return seatId; }
  public void setSeatId(int seatId) { this.seatId = seatId; }
  public int getSeatRow() { return seatRow; }
  public void setSeatRow(int seatRow) { this.seatRow = seatRow; }
  public int getSeatCol() { return seatCol; }
  public void setSeatCol(int seatCol) { this.seatCol = seatCol; }
  public int getAreaId() { return areaId; }
  public void setAreaId(int areaId) { this.areaId = areaId; }

}
