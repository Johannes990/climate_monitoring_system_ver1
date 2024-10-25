--liquibase formatted sql

--changeset JohannesJyrgenson20241025:1
DROP TABLE IF EXISTS notification.NotificationType;
CREATE TABLE notification.NotificationType (
    NotificationTypeId INT IDENTITY(1, 1) NOT NULL,
    NotificationTypeDescription NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_NotificationType_NotificationTypeId PRIMARY KEY CLUSTERED (NotificationTypeId)
);

--changeset JohannesJyrgenson20241025:2
INSERT INTO notification.NotificationType (NotificationTypeDescription)
VALUES
    ('TEMP_ABOVE_LIMIT'),
    ('TEMP_BELOW_LIMIT'),
    ('TEMP_BACK_WITHIN_LIMIT'),
    ('RH_ABOVE_LIMIT'),
    ('RH_BELOW_LIMIT'),
    ('RH_BACK_WITHIN_LIMIT'),
    ('NO_SIGNAL');