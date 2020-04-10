package com.gra.local.persistence.domain;

import org.springframework.security.core.GrantedAuthority;

public enum QuantityType implements GrantedAuthority {
    GRAMS("Grams", 0),
    KG("Kg", 1),
    Miligrams("ml", 2),
    Liter("L", 3),
    ITEM("item", 4);

    private String name;
    private int index;

    QuantityType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getAuthority() {
        return name();
    }

    public int getIndex() {
        return index;
    }

    public static QuantityType getByTypeId(int typeId) {
        for (QuantityType value : values()) {
            if (value.getIndex() == typeId) {
                return value;
            }
        }
        return null;
    }
}
