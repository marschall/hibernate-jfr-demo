package com.github.marschall.hibernate.jfr.demo.jpa;

import java.time.LocalDate;
import java.time.Year;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class YearConverter implements AttributeConverter<Year, LocalDate> {

  @Override
  public LocalDate convertToDatabaseColumn(Year attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.atDay(1);
  }

  @Override
  public Year convertToEntityAttribute(LocalDate dbData) {
    if (dbData == null) {
      return null;
    }
    if (dbData.getDayOfYear() != 1) {
      throw new IllegalArgumentException("must be start of year");
    }
    return Year.from(dbData);
  }
  
}