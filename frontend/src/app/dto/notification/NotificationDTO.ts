import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {ActionDTO} from "@/app/dto/notification/ActionDTO";
import {NotificationTypeDTO} from "@/app/dto/notification/NotificationTypeDTO";

export interface NotificationDTO {
    notificationId?: number;
    notificationType: NotificationTypeDTO;
    sensor: SensorDTO;
    timestamp: Date;
    conditionsSelfResolved: boolean;
    userActionTaken: boolean;
    action: ActionDTO | null;
    isActive: boolean;
}