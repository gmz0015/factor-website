package org.noah.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

public class AuthEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private T auth;

    private Set<String> roles;

    public AuthEntity(Long id, T auth, Set<String> roles) {
        this.id = id;
        this.auth = auth;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getAuth() {
        return auth;
    }

    public void setAuth(T auth) {
        this.auth = auth;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
