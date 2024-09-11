--liquibase formatted sql

--changeset JohannesJyrgenson20240911:1
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