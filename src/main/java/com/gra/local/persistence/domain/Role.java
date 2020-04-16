package com.gra.local.persistence.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("Admin", 0),
    VENDOR("Vendor", 1);

    private String name;
    private int index;

    Role(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getAuthority() {
        return name();
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static Role getByTypeId(int typeId) {
        for (Role value : values()) {
            if (value.index == typeId) {
                return value;
            }
        }
        return null;
    }
}
