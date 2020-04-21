package com.gra.local.persistence.domain;

import org.springframework.security.core.GrantedAuthority;

public enum CurrencyEnum implements GrantedAuthority {
    EURO("Euro", 0),
    RON("Ron", 1);

    private String name;
    private int index;

    CurrencyEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getAuthority() {
        return name();
    }

    public int getIndex() {
        return index;
    }

    public static CurrencyEnum getByTypeId(int typeId) {
        for (CurrencyEnum value : values()) {
            if (value.getIndex() == typeId) {
                return value;
            }
        }
        return null;
    }
}
