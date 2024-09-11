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