package org.noah.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class AuthEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private T auth;

    private Set<String> roles;

}
