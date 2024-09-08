package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Account", schema = "userauth")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private long accountId;

    @Column(name = "AccountType")
    @NonNull
    private String accountType;

    @OneToMany(mappedBy = "accountId")
    private List<AppUser> appUsers;
}
