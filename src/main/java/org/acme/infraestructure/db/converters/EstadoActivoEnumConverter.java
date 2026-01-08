package org.acme.infraestructure.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.acme.domain.model.enums.EstadoActivoEnum;

@Converter(autoApply = false)
public class EstadoActivoEnumConverter implements AttributeConverter<EstadoActivoEnum, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(EstadoActivoEnum attribute) {
        if (attribute == null) return null;
        return attribute == EstadoActivoEnum.ACTIVO;
    }

    @Override
    public EstadoActivoEnum convertToEntityAttribute(Boolean dbData) {
        if (dbData == null) return null;
        return dbData ? EstadoActivoEnum.ACTIVO : EstadoActivoEnum.INACTIVO;
    }
}

