USE CLIMATESYSTEM;

CREATE TABLE climateinfo.ControlParameterSet (
    ControlParameterSetID INT IDENTITY(1, 1) NOT NULL,
    TempStandard REAL NOT NULL,
    TempTolerance REAL NOT NULL,
    RelHumidityStandard REAL NOT NULL,
    RelHumidityTolerance REAL NOT NULL,
    CONSTRAINT PK_ControlParameterSetID PRIMARY KEY CLUSTERED (ControlParameterSetID),
    CONSTRAINT CHK_TempToleranceNonNegative CHECK (TempTolerance >= 0),
    CONSTRAINT CHK_RelHumidityToleranceNonNegative CHECK (RelHumidityTolerance >= 0),
    CONSTRAINT CHK_RelHumidityStandard CHECK ((RelHumidityStandard >= 0) AND (RelHumidityStandard <= 100))
);

CREATE TABLE climateinfo.Location (
    LocationID INT IDENTITY(1, 1) NOT NULL,
    LocationDescription NVARCHAR(50) NOT NULL,
    ControlParameterSetID INT NOT NULL,
    CONSTRAINT PK_LocationID PRIMARY KEY CLUSTERED (LocationID),
    CONSTRAINT FK_Location_ControlParameterSet FOREIGN KEY (ControlParameterSetID)
        REFERENCES climateinfo.ControlParameterSet(ControlParameterSetID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE climateinfo.Sensor (
    SensorID INT IDENTITY(1, 1) NOT NULL,
    Passkey INT NOT NULL,
    DeviceCode INT NOT NULL,
    TempUnit NVARCHAR(1) NOT NULL,
    LocationID INT NOT NULL,
    CONSTRAINT PK_SensorID PRIMARY KEY CLUSTERED (SensorID),
    CONSTRAINT FK_Sensor_Location FOREIGN KEY (LocationID)
        REFERENCES climateinfo.Location(LocationID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE climateinfo.SensorReading (
    SensorReadingID INT IDENTITY(1, 1) NOT NULL,
    Temperature REAL NOT NULL,
    RelHumidity REAL NOT NULL,
    ReadingTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    SensorID INT NOT NULL,
    CONSTRAINT PK_SensorReadingID PRIMARY KEY CLUSTERED (SensorReadingID),
    CONSTRAINT FK_Sensor_SensorReading FOREIGN KEY (SensorID)
        REFERENCES climateinfo.Sensor(SensorID)
    ON UPDATE CASCADE
);

CREATE TABLE climateinfo.ReadingWarning (
    ReadingWarningID INT IDENTITY(1, 1) NOT NULL,
    SensorReadingID INT NOT NULL,
    Active INT DEFAULT 1 NOT NULL,
    Info NVARCHAR(100),
    CONSTRAINT PK_SensorReadingWarningID PRIMARY KEY CLUSTERED (ReadingWarningID),
    CONSTRAINT FK_SensorReading_SensorReadingWarning FOREIGN KEY (SensorReadingID)
        REFERENCES climateinfo.SensorReading(SensorReadingID)
    ON UPDATE CASCADE
);
