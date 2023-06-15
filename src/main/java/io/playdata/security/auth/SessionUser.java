package io.playdata.security.auth;

import io.playdata.security.login.model.AccountDTO;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String role;

    public SessionUser(AccountDTO user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRoleKey();
    }

    // getter, setter 등 필요한 메소드 추가
}