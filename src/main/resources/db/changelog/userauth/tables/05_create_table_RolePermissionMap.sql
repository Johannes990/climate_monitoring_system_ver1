--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.RolePermissionMap;
CREATE TABLE userauth.RolePermissionMap
(
    RoleID       INT NOT NULL,
    PermissionID INT NOT NULL,
    CONSTRAINT PK_RolePermissionMap_RoleID_PermissionID PRIMARY KEY CLUSTERED (RoleID, PermissionID),
    CONSTRAINT FK_RolePermission_UserRole FOREIGN KEY (RoleID)
        REFERENCES userauth.UserRole(RoleID)
        ON DELETE CASCADE,
    CONSTRAINT FK_RolePermissionMap_Permission FOREIGN KEY (PermissionID)
        REFERENCES userauth.Permission(PermissionID)
        ON DELETE CASCADE,
);
