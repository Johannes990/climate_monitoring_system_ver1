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


--changeset JohannesJyrgenson20240903:2
INSERT INTO userauth.AppUser (FirstName, LastName, UserName, Email, PasswordHash, AccountId)
VALUES ('Johannes',
        'Jyrgenson',
        'System Creator',
        'Johannes.Jyrgenson@Note-EMS.com',
        '$2a$10$rhscX/JgSaMa7iyn8rKjRerOx4oOINWpoDxL6K2mKCT47LbxNJB7u',
        1);


--changeset JohannesJyrgenson20240911:3
ALTER TABLE userauth.AppUser ALTER COLUMN AccountId INT NULL;
ALTER TABLE userauth.AppUser DROP CONSTRAINT FK_AppUser_Account;
ALTER TABLE userauth.AppUser ADD CONSTRAINT FK_AppUser_Account
    FOREIGN KEY (AccountId)
    REFERENCES userauth.Account(AccountId)
    ON DELETE SET NULL;