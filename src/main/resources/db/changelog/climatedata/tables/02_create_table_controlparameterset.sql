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

--changeset JohannesJyrgenson20240912:2
INSERT INTO climatedata.ControlParameterSet (TempNorm,
                                             TempTolerance,
                                             RelHumidityNorm,
                                             RelHumidityTolerance)
VALUES (22.0, 4.0, 45.0, 15.0),
       (8.0, 4.0, 50.0, 50.0),
       (30.0, 10.0, 50.0, 50.0);
