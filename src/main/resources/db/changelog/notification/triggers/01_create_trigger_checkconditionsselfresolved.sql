--liquibase formatted sql

--changeset JohannesJyrgenson20241025:1
CREATE TRIGGER TRG_CheckConditionsSelfResolved
    ON notification.Notification
    AFTER UPDATE
    AS
    BEGIN
        SET NOCOUNT ON;

        IF EXISTS (
            SELECT 1
            FROM inserted i
            JOIN notification.Notification n ON i.NotificationId = n.NotificationId
            WHERE i.ConditionsSelfResolved = 1
            AND n.NotificationTypeId NOT IN (
                SELECT NotificationTypeId
                FROM notification.NotificationType
                WHERE NotificationTypeDescription IN (
                    'TEMP_ABOVE_LIMIT',
                    'TEMP_BELOW_LIMIT',
                    'RH_ABOVE_LIMIT',
                    'RH_BELOW_LIMIT'
                    )
                )
        )
        BEGIN
            RAISERROR('Conditions can only self resolve for specific notification types: TEMP_ABOVE_LIMIT, TEMP_BELOW_LIMIT, RH_ABOVE_LIMIT, RH_BELOW_LIMIT', 16, 1);
            RETURN;
        END;
    END;