package com.github.marschall.hibernate.jfr.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {

  @Id
  @Column
  private short categoryId;

  @Column
  private String name;

  @Column
  private String description;

  public short getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(short categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}