CREATE DATABASE CLIMATESYSTEM
ON PRIMARY (
    NAME = climate_system_database,
    FILENAME = 'X:\koolipraktika\climate_monitoring_system_ver1\database\data\climate_system_data.mdf',
    SIZE = 10MB,
    MAXSIZE = 100MB,
    FILEGROWTH = 10MB
)
LOG ON (
    NAME = climate_system_database_log,
    FILENAME = 'X:\koolipraktika\climate_monitoring_system_ver1\database\log\climate_system_database_log.ldf',
    SIZE = 5MB,
    MAXSIZE = 50MB,
    FILEGROWTH = 5MB
);