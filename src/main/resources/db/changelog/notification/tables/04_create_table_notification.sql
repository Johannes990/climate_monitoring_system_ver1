--liquibase formatted sql

--changeset JohannesJyrgenson20241025:1
DROP TABLE IF EXISTS notification.Notification;
CREATE TABLE notification.Notification (
    NotificationId INT IDENTITY(1, 1) NOT NULL,
    NotificationTypeId INT NOT NULL,
    SensorId INT NOT NULL,
    TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    IsActionTaken BIT DEFAULT 0,
    ActionId INT,
    CONSTRAINT PK_Notification_NotificationId PRIMARY KEY CLUSTERED (NotificationId),
    CONSTRAINT FK_Notification_NotificationType FOREIGN KEY (NotificationTypeId)
                                       REFERENCES notification.NotificationType(NotificationTypeId),
    CONSTRAINT FK_Notification_Action FOREIGN KEY (ActionId)
                                       REFERENCES notification.Action(ActionId),
    CONSTRAINT CHK_Notification_ActionTaken CHECK (IsActionTaken = 0 OR ActionId IS NOT NULL),
);

--changeset JohannesJyrgenson20241025:2
DROP TABLE IF EXISTS notification.Notification;
CREATE TABLE notification.Notification (
    NotificationId INT IDENTITY(1, 1) NOT NULL,
    NotificationTypeId INT NOT NULL,
    SensorId INT NOT NULL,
    TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    ConditionsSelfResolved BIT DEFAULT 0,
    UserActionTaken BIT DEFAULT 0,
    ActionId INT,
    IsActive AS (CASE WHEN UserActionTaken = 0 AND ConditionsSelfResolved = 0 THEN 1 ELSE 0 END),
    CONSTRAINT PK_Notification_NotificationId PRIMARY KEY CLUSTERED (NotificationId),
    CONSTRAINT FK_Notification_NotificationType FOREIGN KEY (NotificationTypeId)
                                       REFERENCES notification.NotificationType(NotificationTypeId),
    CONSTRAINT FK_Notification_Action FOREIGN KEY (ActionId)
                                       REFERENCES notification.Action(ActionId),
    CONSTRAINT CHK_Notification_ActionNotTaken CHECK (UserActionTaken = 0 OR ActionId IS NOT NULL),
);

--changeset JohannesJyrgenson20241105:3
DROP TABLE IF EXISTS notification.Notification;
CREATE TABLE notification.Notification (
    NotificationId INT IDENTITY(1, 1) NOT NULL,
    NotificationTypeId INT NOT NULL,
    SensorId INT NOT NULL,
    TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    ConditionsSelfResolved BIT DEFAULT 0,
    UserActionTaken BIT DEFAULT 0,
    ActionId INT,
    IsActive AS (CASE WHEN UserActionTaken = 0 AND ConditionsSelfResolved = 0 THEN 1 ELSE 0 END),
    CONSTRAINT PK_Notification_NotificationId PRIMARY KEY CLUSTERED (NotificationId),
    CONSTRAINT FK_Notification_NotificationType FOREIGN KEY (NotificationTypeId)
        REFERENCES notification.NotificationType(NotificationTypeId),
    CONSTRAINT FK_Notification_SensorId FOREIGN KEY (SensorId)
        REFERENCES climatedata.Sensor(SensorId),
    CONSTRAINT FK_Notification_Action FOREIGN KEY (ActionId)
        REFERENCES notification.Action(ActionId),
    CONSTRAINT CHK_Notification_ActionNotTaken CHECK (UserActionTaken = 0 OR ActionId IS NOT NULL),

);
