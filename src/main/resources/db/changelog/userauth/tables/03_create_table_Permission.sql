--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.Permission;
CREATE TABLE userauth.Permission (
    PermissionID INT IDENTITY(1, 1) NOT NULL,
    PermissionName NVARCHAR(50) UNIQUE NOT NULL,
    PermissionDescription NVARCHAR(MAX),
    CONSTRAINT PK_Permission_PermissionID PRIMARY KEY CLUSTERED (PermissionID)
);
