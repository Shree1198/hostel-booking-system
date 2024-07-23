package com.shree.hostelbooking.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status == null ? null : status.getDisplayName();
    }

    @Override
    public Status convertToEntityAttribute(String displayName) {
        return displayName == null ? null : Status.valueOf(displayName);
    }
}