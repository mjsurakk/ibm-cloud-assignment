package com.etteplanmore.assignment.store;

import com.etteplanmore.assignment.model.Equipment.Status;
import java.io.Serializable;
import java.util.Date;

public class EquipmentDocument implements Serializable {

  private String _id;
  private String _rev;
  private String address;
  private Date contractStartDate;
  private Date contractEndDate;
  private Status status;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String get_rev() {
    return _rev;
  }

  public void set_rev(String _rev) {
    this._rev = _rev;
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
