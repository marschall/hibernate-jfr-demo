package com.github.marschall.hibernate.jfr.demo.jpa;

import static java.util.stream.Collectors.joining;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.github.marschall.hibernate.jfr.demo.entity.Film.SpecialFeature;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SpecialFeaturesConverter implements AttributeConverter<Set<SpecialFeature>, String> {

  @Override
  public String convertToDatabaseColumn(Set<SpecialFeature> attribute) {
    if (attribute == null) {
      return null;
    }
    //@formatter:off
    return attribute.stream()
                    .map(SpecialFeature::getStorageValue)
                    .collect(joining(","));
    //@formatter:on
  }

  @Override
  public Set<SpecialFeature> convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    //@formatter:off
    List<SpecialFeature> values = Stream.of(dbData.split(","))
                                        .map(SpecialFeature::ofStorageValue)
                                        .toList();
    //@formatter:on
    return EnumSet.copyOf(values);
  }

}
