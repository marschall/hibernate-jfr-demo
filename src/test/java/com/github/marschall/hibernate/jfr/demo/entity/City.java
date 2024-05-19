package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class City {

  @Id
  @Column
  private short cityId;

  @Column
  private String city;
  
  @Column
  private short countryId;

  @Column
  private LocalDateTime lastUpdate;

  public short getCityId() {
    return cityId;
  }

  public void setCityId(short cityId) {
    this.cityId = cityId;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public short getCountryId() {
    return countryId;
  }

  public void setCountryId(short countryId) {
    this.countryId = countryId;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}