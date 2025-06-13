package com.lievasoft.cefac.entity.user;

import lombok.Getter;

@Getter
public enum Permission {
    ME_WHITE("me:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
