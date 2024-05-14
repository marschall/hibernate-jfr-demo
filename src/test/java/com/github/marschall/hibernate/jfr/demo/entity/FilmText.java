package com.github.marschall.hibernate.jfr.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FilmText {

  @Id
  @Column
  private short filmId;

  @Column
  private String title;

  @Column
  private String description;


  public short getFilmId() {
    return filmId;
  }

  public void setFilmId(short filmId) {
    this.filmId = filmId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}