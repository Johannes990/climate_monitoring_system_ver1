package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "UserAccountInfo", schema = "userauth")
public class UserAccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int UserId;

    @NotNull
    @JoinColumn(name = "AccountStatus")
    @ManyToOne
    private AccountStatus accountStatus;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @JoinColumn(name = "GenderID")
    @OneToOne
    private Gender gender;

    @JoinColumn(name = "RoleID")
    @OneToOne
    private UserRole role;
}
