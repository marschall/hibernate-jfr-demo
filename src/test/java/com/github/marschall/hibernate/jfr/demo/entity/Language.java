package com.github.marschall.hibernate.jfr.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Language {

  @Id
  @Column
  private short languageId;

  @Column
  private String name;

  @Column
  private LocalDateTime lastUpdate;

  public short getLanguageId() {
    return languageId;
  }

  public void setLanguageId(short languageId) {
    this.languageId = languageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}