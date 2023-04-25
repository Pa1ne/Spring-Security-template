package com.paine.boot.securuty.entities;


import org.springframework.security.core.GrantedAuthority;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public final class Role extends AbstractEntity implements GrantedAuthority {
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(Long id) {
        this.setId(id);
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Role: id = %d, name = %s", this.getId(), name);
    }
}