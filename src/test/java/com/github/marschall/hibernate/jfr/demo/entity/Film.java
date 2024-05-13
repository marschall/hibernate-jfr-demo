package com.github.marschall.hibernate.jfr.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Film {

  @Id
  @Column
  private short filmId;

  @Column
  private String title;

  @Column
  private String description;

  @Column
  private LocalDate releaseYear;

  @Column
  private Byte languageId;
  
  @Column
  private Byte rentalDuration;

  @Column
  private BigDecimal rentalRate;

  @Column
  private Short length;

  @Column
  private BigDecimal replacementCost;

  @Column
  private String rating;

  @Column
  private String specialFeatures;

  @Column
  private LocalDateTime lastUpdate;


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

  public LocalDate getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(LocalDate releaseYear) {
    this.releaseYear = releaseYear;
  }

  public Byte getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Byte languageId) {
    this.languageId = languageId;
  }

  public Byte getRentalDuration() {
    return rentalDuration;
  }

  public void setRentalDuration(Byte rentalDuration) {
    this.rentalDuration = rentalDuration;
  }

  public BigDecimal getRentalRate() {
    return rentalRate;
  }

  public void setRentalRate(BigDecimal rentalRate) {
    this.rentalRate = rentalRate;
  }

  public Short getLength() {
    return length;
  }

  public void setLength(Short length) {
    this.length = length;
  }

  public BigDecimal getReplacementCost() {
    return replacementCost;
  }

  public void setReplacementCost(BigDecimal replacementCost) {
    this.replacementCost = replacementCost;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(String specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}