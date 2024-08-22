package com.climate_monitoring_system.domain.userauth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Gender", schema = "userauth")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenderID")
    private int genderId;

    @NotNull
    @Column(name = "GenderName")
    private String genderName;
}
