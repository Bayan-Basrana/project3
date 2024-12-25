package com.example.bank2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(30) not null unique")
    private String username;

    @Column(columnDefinition = "varchar(200) not null ")
    private String password;

    @Column(columnDefinition = "varchar(20) not null ")
    private String name;
    @Email(message = "invalid email")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN")
    private String role;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Employee employee;


    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Customer customer;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
