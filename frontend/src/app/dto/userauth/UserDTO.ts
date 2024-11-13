import {AccountDTO} from "@/app/dto/userauth/AccountDTO";

export interface UserDTO {
    userId: number;
    firstName: string;
    lastName: string;
    userName: string;
    email: string;
    password: string;
    accountDTO: AccountDTO
}