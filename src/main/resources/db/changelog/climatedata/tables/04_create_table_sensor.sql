--liquibase formatted sql

--changeset JohannesJyrgenson20240911:1
CREATE TABLE Sensor (
    SensorId INT IDENTITY(1, 1) NOT NULL,
    PassKey NVARCHAR(20) NOT NULL,
    DeviceCode NVARCHAR(20) NOT NULL,
    TempUnit NVARCHAR(1) NOT NULL,
    LocationId INT,
    CONSTRAINT PK_Sensor_SensorId PRIMARY KEY CLUSTERED (SensorId),
    CONSTRAINT FK_Sensor_Location FOREIGN KEY (LocationId)
                    REFERENCES climatedata.Location(LocationId)
                    ON UPDATE CASCADE
                    ON DELETE SET NULL
);

--changeset JohannesJyrgenson20240911:2
DROP TABLE IF EXISTS Sensor;
CREATE TABLE climatedata.Sensor (
                        SensorId INT IDENTITY(1, 1) NOT NULL,
                        PassKey NVARCHAR(20) NOT NULL,
                        DeviceCode NVARCHAR(20) NOT NULL,
                        TempUnit NVARCHAR(1) NOT NULL,
                        LocationId INT,
                        CONSTRAINT PK_Sensor_SensorId PRIMARY KEY CLUSTERED (SensorId),
                        CONSTRAINT FK_Sensor_Location FOREIGN KEY (LocationId)
                            REFERENCES climatedata.Location(LocationId)
                            ON UPDATE CASCADE
                            ON DELETE SET NULL
);

--changeset JohannesJyrgenson20240912:3
INSERT INTO climatedata.Sensor (PassKey, DeviceCode, TempUnit, LocationId)
VALUES ('15961202', '3510', 'C', 1),
       ('15961868', '3510', 'C', 4),
       ('18961817', '3510', 'C', 2),
       ('18941697', '3510', 'C', 3);
