package com.paine.boot.securuty.entities;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.*;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public final class User extends AbstractEntity implements UserDetails {
    @NotEmpty(message = "The name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 to 30 characters")
    private String firstName;
    @NotEmpty(message = "The name should not be empty")
    private String lastName;
    @Column(unique = true)
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "The password should not be empty")
    private String password;
    @Min(value = 0, message = "Age should be greater than 0")
    private byte age;

    @ManyToMany
    @JoinTable(name = "users_roles")
    private Set<Role> roles = new HashSet<>();

    @Transient
    public boolean isNew() {
        return null == this.getId();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String toString() {
        return String.format("User: id = %d, firstName = %s, lastName = %s, email = %s, password = %s, roles = (%s)",
                this.getId(), firstName, lastName, email, password, Collections.singletonList(roles));
    }
}
