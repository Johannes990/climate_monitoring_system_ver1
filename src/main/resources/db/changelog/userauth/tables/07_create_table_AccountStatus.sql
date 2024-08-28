--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.AccountStatus;
CREATE TABLE userauth.AccountStatus (
    StatusID INT IDENTITY(1, 1) NOT NULL,
    StatusName NVARCHAR(50) UNIQUE NOT NULL,
    StatusDescription NVARCHAR(MAX),
    CONSTRAINT PK_AccountStatus_StatusID PRIMARY KEY CLUSTERED (StatusID)
);
