package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "HashAlgorithm", schema = "userauth")
public class HashAlgorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HashAlgorithmID")
    private int hashAlgorithmId;

    @Column(name = "HashAlgorithmName")
    @NotNull
    private String hashAlgorithmName;
}
