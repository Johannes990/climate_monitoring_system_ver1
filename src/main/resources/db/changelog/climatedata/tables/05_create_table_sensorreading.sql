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

--changeset JohannesJyrgenson20240912:3
DROP TABLE IF EXISTS climatedata.SensorReading;
CREATE TABLE climatedata.SensorReading (
                                           SensorReadingId INT IDENTITY(1, 1) NOT NULL,
                                           Temperature REAL,
                                           RelHumidity REAL,
                                           ReadingTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                                           SensorId INT NOT NULL,
                                           CONSTRAINT PK_SensorReading_SensorReadingId PRIMARY KEY CLUSTERED(SensorReadingId),
                                           CONSTRAINT FK_SensorReading_Sensor FOREIGN KEY (SensorId)
                                               REFERENCES climatedata.Sensor(SensorId)
);

--changeset JohannesJyrgenson20240915:4
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (20.8, 48.0, 1),
    (19.1, 40.3, 2);

--changeset JohannesJyrgenson20240915:5
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (21.3, 47.6, 1),
    (19.2, 40.7, 2),
    (13.2, 20.1, 3),
    (26.1, 44.0, 4);

--changeset JohannesJyrgenson20240915:6
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (22.0, 50.1, 1),
    (19.0, 40.1, 2),
    (14.1, 28.3, 3),
    (24.9, 43.0, 4);

--changeset JohannesJyrgenson20240915:7
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (21.5, 52.1, 1),
    (18.5, 39.9, 2),
    (12.9, 31.1, 3),
    (27.8, 47.1, 4);

--changeset JohannesJyrgenson20240915:8
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (20.9, 49.3, 1),
    (19.2, 40.1, 2),
    (12.5, 26.7, 3),
    (30.2, 51.2, 4);

--changeset JohannesJyrgenson20240915:9
INSERT INTO climatedata.SensorReading (Temperature, RelHumidity, SensorId)
VALUES
    (20.1, 39.8, 2);
