package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "AppUser", schema = "userauth")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private long userId;

    @Column(name = "FirstName")
    @NotNull
    private String firstName;

    @Column(name = "LastName")
    @NotNull
    private String lastName;

    @Column(name = "UserName")
    @NotNull
    private String userName;

    @Column(name = "Email")
    @NotNull
    @Email
    private String email;

    @Column(name = "PasswordHash")
    @NotNull
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "AccountId")
    private Account accountId;
}
