--liquibase formatted sql

--changeset JohannesJyrgenson20240903:1
DROP TABLE IF EXISTS userauth.AppUser;

CREATE TABLE userauth.AppUser (
    UserId INT IDENTITY(1, 1) NOT NULL,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    UserName NVARCHAR(50) NOT NULL,
    Email NVARCHAR(254) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(MAX) NOT NULL,
    AccountId INT NOT NULL,
    CONSTRAINT PK_AppUser_UserID PRIMARY KEY CLUSTERED (UserId),
    CONSTRAINT FK_AppUser_Account FOREIGN KEY (AccountId)
        REFERENCES userauth.Account(AccountId)
);