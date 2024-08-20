USE CLIMATESYSTEM;

CREATE TABLE userauth.HashAlgorithm (
    HashAlgorithmID INT IDENTITY(1, 1) NOT NULL,
    HashAlgorithmName NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_HashAlgorithm_HashAlgorithmID PRIMARY KEY CLUSTERED (HashAlgorithmID)
);

CREATE TABLE userauth.Permission (
    PermissionID INT IDENTITY(1, 1) NOT NULL,
    PermissionName NVARCHAR(50) UNIQUE NOT NULL,
    PermissionDescription NVARCHAR(MAX),
    CONSTRAINT PK_Permission_PermissionID PRIMARY KEY CLUSTERED (PermissionID)
);

CREATE TABLE userauth.UserRole (
    RoleID INT IDENTITY(1, 1) NOT NULL,
    RoleName NVARCHAR(50) UNIQUE NOT NULL,
    RoleDescription NVARCHAR(MAX),
    CONSTRAINT PK_UserRole_RoleID PRIMARY KEY CLUSTERED (RoleID)
);

CREATE TABLE userauth.RolePermissionMap (
    RoleID INT NOT NULL,
    PermissionID INT NOT NULL,
    CONSTRAINT PK_RolePermission_RoleID PRIMARY KEY CLUSTERED (RoleID, PermissionID),
    CONSTRAINT FK_RolePermission_Role FOREIGN KEY (RoleID)
        REFERENCES userauth.UserRole(RoleID)
        ON DELETE CASCADE,
    CONSTRAINT FK_RolePermission_Permission FOREIGN KEY (PermissionID)
        REFERENCES userauth.Permission(PermissionID)
        ON DELETE CASCADE
);

CREATE TABLE userauth.Gender (
    GenderID INT IDENTITY(1, 1) NOT NULL,
    GenderName NVARCHAR(20) UNIQUE NOT NULL,
    CONSTRAINT PK_Gender_GenderID PRIMARY KEY CLUSTERED (GenderID)
);

CREATE TABLE userauth.AccountStatus (
    StatusID INT IDENTITY(1, 1) NOT NULL,
    StatusName NVARCHAR(20) UNIQUE NOT NULL,
    StatusDescription NVARCHAR(MAX),
    CONSTRAINT PK_AccountStatus_StatusID PRIMARY KEY CLUSTERED (StatusID)
);

CREATE TABLE userauth.UserAccountInfo (
    UserID INT IDENTITY(1, 1) NOT NULL,
    AccountStatus INT NOT NULL,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    DateOfBirth DATE,
    GenderID INT,
    RoleID INT NOT NULL,
    CONSTRAINT PK_UserAccountInfo_UserID PRIMARY KEY CLUSTERED (UserID),
    CONSTRAINT FK_UserAccountInfo_AccountStatus FOREIGN KEY (AccountStatus)
        REFERENCES userauth.AccountStatus(StatusID),
    CONSTRAINT FK_UserAccountInfo_UserRole FOREIGN KEY (RoleID)
        REFERENCES userauth.UserRole(RoleID)
        ON UPDATE CASCADE,
    CONSTRAINT FK_UserAccountInfo_Gender FOREIGN KEY (GenderID)
        REFERENCES userauth.Gender(GenderID)
);

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
    CONSTRAINT FK_UserAccountLoginData_HashAlgorithm FOREIGN KEY (HashAlgorithmID)
        REFERENCES userauth.HashAlgorithm(HashAlgorithmID)
);
