package org.acme.infraestructure.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.acme.domain.model.enums.EstadoReservaEnum;

@Converter(autoApply = false)
public class EstadoReservaEnumConverter implements AttributeConverter<EstadoReservaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoReservaEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public EstadoReservaEnum convertToEntityAttribute(Integer dbData) {
        return EstadoReservaEnum.fromId(dbData);
    }
}

