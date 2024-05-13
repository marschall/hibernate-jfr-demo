package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Actor {
  
  @Id
  @Column
  private short actorId;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private LocalDate lastUpdate;

  public short getActorId() {
    return actorId;
  }

  public void setActoriId(short actoriId) {
    this.actorId = actoriId;
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