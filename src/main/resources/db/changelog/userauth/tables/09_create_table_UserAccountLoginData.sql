--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.UserAccountLoginData
CREATE TABLE userauth.UserAccountLoginData (
    UserID INT NOT NULL,
    Email NVARCHAR(254) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(MAX) NOT NULL,
    PasswordSalt NVARCHAR(MAX) NOT NULL,
    HashAlgorithmID INT NOT NULL,
    CONSTRAINT PK_UserAccountLoginData_UserID PRIMARY KEY CLUSTERED (UserID),
    CONSTRAINT FK_UserAccountLoginData_UserAccountInfo FOREIGN KEY (UserID)
        REFERENCES userauth.UserAccountInfo(UserID)
        ON UPDATE CASCADE,
    CONSTRAINT FK_UserAccountLoginData_HashAlgorithmID FOREIGN KEY (HashAlgorithmID)
        REFERENCES userauth.HashAlgorithm(HashAlgorithmID)
);