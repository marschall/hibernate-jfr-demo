package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Table(name = "ACTOR")
public class Actor {
  @Id
  @Column
//  @GeneratedValue
  private short actoriId;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private LocalDate lastUpdate;

  public short getActoriId() {
    return actoriId;
  }

  public void setActoriId(short actoriId) {
    this.actoriId = actoriId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDate lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}