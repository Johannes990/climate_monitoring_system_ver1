--liquibase formatted sql

--changeset JohannesJyrgenson20241025:1
DROP TABLE IF EXISTS notification.Action;
CREATE TABLE notification.Action (
    ActionId INT IDENTITY(1, 1) NOT NULL,
    TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    Message NVARCHAR(MAX) NOT NULL,
    UserId INT,
    CONSTRAINT PK_Action_ActionId PRIMARY KEY CLUSTERED (ActionId),
    CONSTRAINT FK_Action_User FOREIGN KEY (UserId)
                                 REFERENCES userauth.AppUser(UserId) ON DELETE SET NULL,
);
