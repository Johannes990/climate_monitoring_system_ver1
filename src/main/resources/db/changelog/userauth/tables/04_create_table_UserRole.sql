--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.UserRole;
CREATE TABLE userauth.UserRole (
    RoleID INT IDENTITY(1, 1) NOT NULL,
    RoleName NVARCHAR(50) UNIQUE NOT NULL,
    RoleDescription NVARCHAR(MAX),
    CONSTRAINT PK_UserRole_RoleID PRIMARY KEY CLUSTERED (RoleID)
);
