package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UserAccountLoginData", schema = "userauth")
public class UserAccountLoginData {
    @Id
    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    @MapsId
    private UserAccountInfo userAccountInfo;

    @Column(name = "Email", unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "PasswordHash")
    @NotNull
    private String passwordHash;

    @Column(name = "PasswordSalt")
    @NotNull
    private String passwordSalt;

    @JoinColumn(name = "HashAlgorithmID")
    @ManyToOne
    private HashAlgorithm hashAlgorithm;
}
