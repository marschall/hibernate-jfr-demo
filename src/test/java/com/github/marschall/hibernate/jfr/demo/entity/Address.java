package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {
  
  @Id
  @Column
  private short actorId;

  @Column
  private String address;
  
  @Column
  private String address2;

  @Column
  private String district;
  
  @Column
  private short cityId;
  
  @Column
  private String postalCode;
  
  @Column
  private String phone;

  @Column
  private LocalDateTime lastUpdate;

  public short getActorId() {
    return actorId;
  }

  public void setActorId(short actorId) {
    this.actorId = actorId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public short getCityId() {
    return cityId;
  }

  public void setCityId(short cityId) {
    this.cityId = cityId;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}