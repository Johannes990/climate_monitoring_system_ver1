--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.UserAccountInfo;
CREATE TABLE userauth.UserAccountInfo (
    UserID INT IDENTITY(1, 1) NOT NULL,
    AccountStatusID INT NOT NULL,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    DateOfBirth DATE,
    GenderID INT,
    RoleID INT NOT NULL,
    CONSTRAINT PK_UserAccountInfo_UserID PRIMARY KEY CLUSTERED (UserID),
    CONSTRAINT FK_UserAccountInfo_AccountStatus FOREIGN KEY (AccountStatusID)
        REFERENCES userauth.AccountStatus(StatusID),
    CONSTRAINT FK_UserAccountInfo_Gender FOREIGN KEY (GenderID)
        REFERENCES userauth.Gender(GenderID),
    CONSTRAINT FK_UserAccountInfo_UserRole FOREIGN KEY (RoleID)
        REFERENCES userauth.UserRole(RoleID)
);
