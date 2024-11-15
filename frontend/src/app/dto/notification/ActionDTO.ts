import {UserDTO} from "@/app/dto/userauth/UserDTO";

export interface ActionDTO {
    actionId?: number;
    timestamp?: Date;
    message: string;
    user: UserDTO;
}