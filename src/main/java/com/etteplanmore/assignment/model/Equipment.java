package com.etteplanmore.assignment.model;

import java.io.Serializable;
import java.util.Date;

public class Equipment implements Serializable {

  public enum Status {
    Running, Stopped
  }

  private String equipmentNumber;
  private String address;
  private Date contractStartDate;
  private Date contractEndDate;
  private Status status;

  public String getEquipmentNumber() {
    return equipmentNumber;
  }

  public void setEquipmentNumber(String equipmentNumber) {
    this.equipmentNumber = equipmentNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getContractStartDate() {
    return contractStartDate;
  }

  public void setContractStartDate(Date contractStartDate) {
    this.contractStartDate = contractStartDate;
  }

  public Date getContractEndDate() {
    return contractEndDate;
  }

  public void setContractEndDate(Date contractEndDate) {
    this.contractEndDate = contractEndDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
