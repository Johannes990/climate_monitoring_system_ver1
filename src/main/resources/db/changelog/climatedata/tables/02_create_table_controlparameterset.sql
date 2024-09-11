--liquibase formatted sql

--changeset JohannesJyrgenson20240911:1
CREATE TABLE climatedata.ControlParameterSet (
    ControlParameterSetId INT IDENTITY(1, 1) NOT NULL,
    TempNorm REAL NOT NULL,
    TempTolerance REAL NOT NULL,
    RelHumidityNorm REAL NOT NULL,
    RelHumidityTolerance REAL NOT NULL,
    CONSTRAINT PK_ControlParameterSet_ControlParameterSetId PRIMARY KEY CLUSTERED (ControlParameterSetId)
);
