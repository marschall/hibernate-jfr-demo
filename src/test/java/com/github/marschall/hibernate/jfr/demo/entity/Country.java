package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Country {

  @Id
  @Column
  private short countryId;

  @Column
  private String country;

  @Column
  private LocalDateTime lastUpdate;

  public short getCountryId() {
    return countryId;
  }

  public void setCountryId(short countryId) {
    this.countryId = countryId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}