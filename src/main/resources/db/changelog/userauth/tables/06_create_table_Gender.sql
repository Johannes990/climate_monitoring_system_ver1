--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.Gender;
CREATE TABLE userauth.GENDER (
    GenderID INT IDENTITY(1, 1) NOT NULL,
    GenderName NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_Gender_GenderID PRIMARY KEY CLUSTERED (GenderID)
);

--changeset JohannesJyrgenson20240828:2
DROP TABLE userauth.GENDER;
CREATE TABLE userauth.Gender (
    GenderID INT IDENTITY(1, 1) NOT NULL,
    GenderName NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_Gender_GenderID PRIMARY KEY CLUSTERED (GenderID)
);
