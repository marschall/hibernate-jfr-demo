package com.github.marschall.hibernate.jfr.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.marschall.hibernate.jfr.demo.jpa.SpecialFeaturesConverter;
import com.github.marschall.hibernate.jfr.demo.jpa.YearConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
  @Convert(converter = YearConverter.class)
  private Year releaseYear;

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
  @Convert(converter = SpecialFeaturesConverter.class)
  private Set<SpecialFeature> specialFeatures;

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

  public Year getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(Year releaseYear) {
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

  public Set<SpecialFeature> getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(Set<SpecialFeature> specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public enum SpecialFeature {

    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private static final Map<String, SpecialFeature> LOOKUP;

    static {
      //@formatter:off
      LOOKUP = Stream.of(SpecialFeature.values())
                     .collect(Collectors.toMap(SpecialFeature::getStorageValue, Function.identity()));
      //@formatter:on
    }

    private String storageValue;

    public String getStorageValue() {
      return storageValue;
    }

    private SpecialFeature(String specialFeature) {
      this.storageValue = specialFeature;
    }

    public static SpecialFeature ofStorageValue(String storageValue) {
      Objects.requireNonNull(storageValue, "storageValue");
      SpecialFeature specialFeature = LOOKUP.get(storageValue);
      if (specialFeature == null) {
        throw new IllegalArgumentException(storageValue + " is not a known special feature");
      }
      return specialFeature;
    }
  }

}