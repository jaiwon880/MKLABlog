package io.playdata.security.login.model;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String roleKey;

    Role(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getKey() {
        return this.roleKey;
    }
    public String getRoleKey() {
        return this.roleKey;
    }
}
