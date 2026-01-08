package org.acme.domain.model.enums;

public enum EstadoActivoEnum {
    INACTIVO(0),
    ACTIVO(1);

    private final int value;

    EstadoActivoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EstadoActivoEnum fromValue(Integer v) {
        if (v == null) return null;
        for (EstadoActivoEnum e : values()) {
            if (e.value == v) return e;
        }
        throw new IllegalArgumentException("Unknown EstadoActivoEnum value: " + v);
    }
}

