--liquibase formatted sql

--changeset JohannesJyrgenson20240903:1
DROP TABLE IF EXISTS userauth.Account;

CREATE TABLE userauth.Account (
    AccountId INT IDENTITY(1, 1) NOT NULL,
    AccountType NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_Account_AccountId PRIMARY KEY CLUSTERED (AccountId)
);

--changeset JohannesJyrgenson20290903:2
INSERT INTO userauth.Account (AccountType)
VALUES ('Administrator');
INSERT INTO userauth.Account (AccountType)
VALUES ('Regular');
INSERT INTO userauth.Account (AccountType)
VALUES ('View only');
