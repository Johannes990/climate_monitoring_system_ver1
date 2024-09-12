--liquibase formatted sql

--changeset JohannesJyrgenson20240911:1
CREATE TABLE SensorReading (
    SensorReadingId INT IDENTITY(1, 1) NOT NULL,
    Temperature REAL,
    RelHumidity REAL,
    ReadingTime DATETIME,
    SensorId INT NOT NULL,
    CONSTRAINT PK_SensorReading_SensorReadingId PRIMARY KEY CLUSTERED(SensorReadingId),
    CONSTRAINT FK_SensorReading_Sensor FOREIGN KEY (SensorId)
                           REFERENCES climatedata.Sensor(SensorId)
);

--changeset JohannesJyrgenson20240911:2
DROP TABLE IF EXISTS SensorReading;
CREATE TABLE climatedata.SensorReading (
                                           SensorReadingId INT IDENTITY(1, 1) NOT NULL,
                                           Temperature REAL,
                                           RelHumidity REAL,
                                           ReadingTime DATETIME,
                                           SensorId INT NOT NULL,
                                           CONSTRAINT PK_SensorReading_SensorReadingId PRIMARY KEY CLUSTERED(SensorReadingId),
                                           CONSTRAINT FK_SensorReading_Sensor FOREIGN KEY (SensorId)
                                               REFERENCES climatedata.Sensor(SensorId)
);