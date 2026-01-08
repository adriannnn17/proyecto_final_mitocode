package org.acme.domain.model.enums;

public enum EstadoReservaEnum {
    CREADA(1, "CREADA"),
    CANCELADA(2, "CANCELADA"),
    COMPLETADA(3, "COMPLETADA");

    private final int id;
    private final String label;

    EstadoReservaEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static EstadoReservaEnum fromId(Integer id) {
        if (id == null) return null;
        for (EstadoReservaEnum e : values()) {
            if (e.id == id) return e;
        }
        throw new IllegalArgumentException("Unknown EstadoReservaEnum id: " + id);
    }
}

