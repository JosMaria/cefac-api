package com.lievasoft.cefac.entity;

import lombok.Getter;

@Getter
public enum Permission {
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
