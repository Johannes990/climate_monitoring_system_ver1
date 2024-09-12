--liquibase formatted sql

--changeset JohannesJyrgenson20240911:1
CREATE TABLE Location (
    LocationId INT IDENTITY(1, 1) NOT NULL,
    LocationDescription NVARCHAR(MAX),
    ControlParameterSetId INT,
    CONSTRAINT PK_Location_LocationId PRIMARY KEY CLUSTERED (LocationId),
    CONSTRAINT FK_Location_ControlParameterSet FOREIGN KEY (ControlParameterSetId)
                      REFERENCES climatedata.ControlParameterSet(ControlParameterSetId)
                      ON UPDATE CASCADE
                      ON DELETE SET NULL
);

--changeset JohannesJyrgenson20240911:2
DROP TABLE IF EXISTS Location;
CREATE TABLE climatedata.Location (
                          LocationId INT IDENTITY(1, 1) NOT NULL,
                          LocationDescription NVARCHAR(MAX),
                          ControlParameterSetId INT,
                          CONSTRAINT PK_Location_LocationId PRIMARY KEY CLUSTERED (LocationId),
                          CONSTRAINT FK_Location_ControlParameterSet FOREIGN KEY (ControlParameterSetId)
                              REFERENCES climatedata.ControlParameterSet(ControlParameterSetId)
                              ON UPDATE CASCADE
                              ON DELETE SET NULL
);

--changeset JohannesJyrgenson20240912:3
INSERT INTO climatedata.Location (LocationDescription, ControlParameterSetId)
VALUES ('Saal 1', 1),
       ('SMT Fridge', 2),
       ('EPO', 3),
       ('Saal 2', 1);
